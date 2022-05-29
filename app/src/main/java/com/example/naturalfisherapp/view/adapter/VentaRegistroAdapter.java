package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.ItemPromocion;
import com.example.naturalfisherapp.data.models.ItemPromocionVenta;
import com.example.naturalfisherapp.data.models.ItemVenta;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.PromocionVenta;
import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;
import com.example.naturalfisherapp.presenter.interfaces.IVentaPresenter;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.activities.MenuPrincipalActivity;
import com.example.naturalfisherapp.view.activities.VentaPrinsipalActivity;
import com.example.naturalfisherapp.view.dialog.AgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.dialog.ConfirmarDialogFragment;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class VentaRegistroAdapter extends RecyclerView.Adapter<VentaRegistroAdapter.VentaRegistroHolder> {

    List<Venta> listVenta;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private IVentaPresenter ventaPresenter;

    public VentaRegistroAdapter(List<Venta> listVenta, Context context, FragmentManager fragmentManager, Activity activity, IVentaPresenter ventaPresenter) {
        this.listVenta = listVenta;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.ventaPresenter = ventaPresenter;
    }

    @NonNull
    @Override
    public VentaRegistroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_venta_registro, parent, false);
        VentaRegistroAdapter.VentaRegistroHolder ventaRegistroHolder = new VentaRegistroHolder(view, ventaPresenter, fragmentManager);
        context = parent.getContext();
        return ventaRegistroHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VentaRegistroAdapter.VentaRegistroHolder holder, int position) {
        holder.bind(listVenta.get(position), activity);
    }

    @Override
    public int getItemCount() {
        return listVenta.size();
    }

    class VentaRegistroHolder extends RecyclerView.ViewHolder implements IVentaRegistroHolderView {

        private Venta venta;
        private LinearLayoutManager linearLayoutManager;
        private IVentaPresenter ventaPresenter;
        private Activity activity;
        private FragmentManager fragmentManager;

        @BindView(R.id.fechaVentaDetalle)
        TextView fechaVentaDetalle;

        @BindView(R.id.nombreCliente)
        TextView nombreCliente;

        @BindView(R.id.telefonoCliente)
        TextView telefonoCliente;

        @BindView(R.id.direccionCliente)
        TextView direccionCliente;

        @BindView(R.id.efRvItems)
        RecyclerView recyclerViewItems;

        @BindView(R.id.totalVenta)
        TextView totalVenta;

        @BindView(R.id.llAgregarProducto)
        LinearLayout llAgregarProducto;

        @BindView(R.id.llBtnAgregarProducto)
        LinearLayout llBtnAgregarProducto;

        @BindView(R.id.llBotonesProcesos)
        LinearLayout llBotonesProcesos;

        @BindView(R.id.btnCancelarVenta)
        LinearLayout btnCancelarVenta;

        @BindView(R.id.btnConfirmarVenta)
        LinearLayout btnConfirmarVenta;

        @BindView(R.id.btnAgregarProducto)
        FloatingActionButton btnAgregarProducto;

        public VentaRegistroHolder(View itemView, IVentaPresenter ventaPresenter, FragmentManager fragmentManager) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.ventaPresenter = ventaPresenter;
            this.fragmentManager = fragmentManager;
        }

        void bind(final Venta venta, Activity activity){
            this.venta = venta;
            this.activity = activity;

            if(venta != null && venta.getCliente() != null){
                fechaVentaDetalle.setText(Utilidades.formatearFecha(venta.getFecha()));
                nombreCliente.setText(venta.getCliente().getNombre());
                telefonoCliente.setText("" + venta.getCliente().getTelefono());
                direccionCliente.setText(venta.getCliente().getDireccion());

                if(this.venta.getItems() != null && !this.venta.getItems().isEmpty()){
                    recyclerViewItems.setVisibility(View.VISIBLE);
                    llAgregarProducto.setVisibility(View.GONE);
                    llBotonesProcesos.setVisibility(View.GONE);
                    btnAgregarProducto.setVisibility(View.GONE);
                    cargarAdapterItems(this.venta.getItems(), EnumVariables.MODO_CONSULTA.getValor());
                } else {
                    recyclerViewItems.setVisibility(View.GONE);
                    llAgregarProducto.setVisibility(View.VISIBLE);
                    llBotonesProcesos.setVisibility(View.GONE);
                    btnAgregarProducto.setVisibility(View.GONE);
                }

                totalVenta.setText("$ " + Utilidades.puntoMil(venta.getTotal()));
            }

        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llBtnAgregarProducto)
        void OnClickLlBtnAgregarProducto(){
            goToAgregarProducto();
        }

        @OnClick(R.id.btnCancelarVenta)
        void onClickCancelarVenta(){
            goToVentaPrincipalActivity();
        }

        @OnClick(R.id.btnConfirmarVenta)
        void onClickConfirmarVenta(){
            if(validarVenta()){
                goToConfirmarDialog();
                //ventaPresenter.realizarVenta(venta);
            }
        }

        @OnClick(R.id.btnAgregarProducto)
        void onClickBtnAgregarProducto(){
            goToAgregarProducto();
        }

        /**
         * -------------- METODOS INTERFACE IVentaRegistroHolderView --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite cargar el adapter de los itemProductos
         * @Fecha 18/07/21
         */
        @Override
        public void cargarAdapterItems(List<ItemVenta> items, String modo) {

            recyclerViewItems.setVisibility(View.VISIBLE);
            llAgregarProducto.setVisibility(View.GONE);
            //llBotonesProcesos.setVisibility(View.VISIBLE);

            linearLayoutManager = new LinearLayoutManager(activity,RecyclerView.VERTICAL,false);
            //FragmentManager fragmentManager = activity.getFragmentManager();
            ItemVentaAdapter itemVentaAdapter = new ItemVentaAdapter(items, activity.getApplicationContext(), fragmentManager, activity, this, modo);
            recyclerViewItems.setAdapter(itemVentaAdapter);
            recyclerViewItems.setLayoutManager(linearLayoutManager);
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite agregar producto a los items de venta
         * @Fecha 18/07/21
         */
        @Override
        public void agregarProducto(GeneralProductos gProducto) {
            ItemVenta item = new ItemVenta();
            if( gProducto != null){
                if(gProducto.getProducto() != null){
                    item.setProducto(gProducto.getProducto());

                    item.setCant_peso(0d);
                    item.setTotal(0d);
                } else if(gProducto.getPromocion() != null){
                    PromocionVenta promocionVenta = new PromocionVenta();
                    promocionVenta.setPromocion(gProducto.getPromocion());
                    List<ItemPromocionVenta> itemPromocionVentas = new ArrayList<>();

                    if(gProducto.getPromocion().getItems() != null && !gProducto.getPromocion().getItems().isEmpty()){
                        for(ItemPromocion itemPromocion:gProducto.getPromocion().getItems()){
                            ItemPromocionVenta itemPromocionVenta = new ItemPromocionVenta();
                            itemPromocionVenta.setProducto(itemPromocion.getProducto());
                            itemPromocionVenta.setPrecio_venta(itemPromocion.getPrecio_venta());
                            itemPromocionVenta.setCant_peso(itemPromocion.getCant_peso());
                            itemPromocionVenta.setTotal(itemPromocion.getTotal());

                            itemPromocionVentas.add(itemPromocionVenta);
                        }
                    }

                    promocionVenta.setItemsPromocionVenta(itemPromocionVentas);

                    promocionVenta.setTotal(gProducto.getPromocion().getTotal());
                    promocionVenta.setPorcentage(gProducto.getPromocion().getPorcentage());
                    promocionVenta.setGanancia(gProducto.getPromocion().getGanancia());
                    promocionVenta.setTotalCalculado(gProducto.getPromocion().getTotalCalculado());
                    item.setPromocionVenta(promocionVenta);

                    item.setCant_peso(1d);
                    item.setTotal(gProducto.getPromocion().getTotal());

                }
            }


            item.setUsa_precio_distinto("N");
            if(this.venta.getItems() != null && !this.venta.getItems().isEmpty()){
                item.setCodigoItemApp(this.venta.getItems().size());
                this.venta.getItems().add(item);
            } else {
                List<ItemVenta> items = new ArrayList<>();
                item.setCodigoItemApp(0);
                items.add(item);
                this.venta.setItems(items);

            }
            mostrarLlProcesos();
            calcularPrecioTotal();

            btnAgregarProducto.setVisibility(View.VISIBLE);

            cargarAdapterItems(this.venta.getItems(), EnumVariables.MODO_CREACION.getValor());

        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite mostrar Linearlayout de los procesos
         * @Fecha 18/07/21
         */
        @Override
        public void mostrarLlProcesos() {
            llBotonesProcesos.setVisibility(View.VISIBLE);
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite habilitar o deshabilitar boton de confirmar venta
         * @Fecha 18/07/21
         */
        @Override
        public void habilitarDeahabilitarBtnConfirmarVenta(boolean habilita) {
            btnConfirmarVenta.setClickable(habilita);
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite calcular precio total de los productos
         * @Fecha 18/07/21
         */
        @Override
        public void calcularPrecioTotal() {

            if(venta.getItems() != null && !venta.getItems().isEmpty()){
                Double total = 0.0;
                for (ItemVenta item: venta.getItems()){
                    total += item.getTotal();
                }

                totalVenta.setText("$" + Utilidades.puntoMil(total));
                venta.setTotal(total);
                habilitarDeahabilitarBtnConfirmarVenta(true);
            } else{
                totalVenta.setText("$");
                venta.setTotal(0.0);
                habilitarDeahabilitarBtnConfirmarVenta(false);
            }
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite llamar el metodo para enviar infromacion venta
         * @Fecha 27/07/21
         */
        @Override
        public void realizarVenta() {
            ventaPresenter.realizarVenta(venta);
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite eliminar el item perteneciente al producto de la venta
         * @Fecha 24/02/2022
         */
        @Override
        public void eliminarItemVenta(ItemVenta itemVenta) {

            if(itemVenta != null){
                if(venta.getItems() != null && !venta.getItems().isEmpty()){
                    for( int i=0; i < venta.getItems().size(); i++){
                        if(venta.getItems().get(i).getCodigoItemApp() == itemVenta.getCodigoItemApp()){
                            venta.getItems().remove(i);
                            calcularPrecioTotal();
                            break;
                        } else {
                            Log.e("EliminarItemVenta", "Error al eliminar el item de la venta no se encontro el item a eliminar");
                        }
                    }
                    cargarAdapterItems(venta.getItems(), EnumVariables.MODO_CREACION.getValor());
                } else {
                    Log.e("EliminarItemVenta", "Error al eliminar el item de la venta no existen items");
                }
            } else {
                Log.e("EliminarItemVenta", "Error al eliminar el item de la venta es NULL");
            }

        }

        /*@Override
        public void mostrarDialogoInformativo(String tipo, String informacion) {
            InformacionDialogFragment informacionDialogFragment = InformacionDialogFragment.newInstance(tipo, informacion);
            informacionDialogFragment.show(fragmentManager, "InformacionDialog");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("InformacionDialog");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }*/

        /**
         * -------------- METODOS PROPIOS --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite ir agregar productos
         * @Fecha 18/07/21
         */
        private void goToAgregarProducto() {
            System.out.println("Agregar Producto....");
            AgregarProductoDialogFragment agregarProductoDialogFragment = AgregarProductoDialogFragment.newInstance(fragmentManager, this, venta.getItems(), activity);
            agregarProductoDialogFragment.show(fragmentManager, "AgregarProducto");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("AgregarProducto");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }


        /**
         * @Autor RagooS
         * @Descripccion Metodo permite validar si el objeto venta contiene la informacion necesaria y valida para realizar la venta
         * @Fecha 18/07/21
         */
        private boolean validarVenta() {

            boolean valido = false;

            if(venta != null){
                venta.setFecha(null);
                if(venta.getItems() != null && !venta.getItems().isEmpty()){
                    for (ItemVenta item: venta.getItems()){
                        if(item.getCant_peso() > 0 && item.getTotal() > 0 ){
                            if(item.getProducto() != null){
                                valido = true;
                            } else if(item.getPromocionVenta() != null){
                                if(item.getPromocionVenta().getPromocion() != null){
                                    if(item.getPromocionVenta().getItemsPromocionVenta() != null && !item.getPromocionVenta().getItemsPromocionVenta().isEmpty()){
                                        valido = true;
                                    } else {
                                        System.out.println("Item Promocion Venta no tiene Items");
                                        valido = false;
                                    }
                                } else {
                                    System.out.println("Item Promocion Venta no tiene Promocion");
                                    valido = false;
                                }
                            } else {
                                System.out.println("Item no tiene ni Producto ni Promocion");
                                valido = false;
                            }
                        } else {
                            System.out.println("Item en 0....");
                            valido = false;
                        }
                    }
                } else {
                    System.out.println("No existen Items....");
                    valido = false;
                }
            } else {
                System.out.println("Objeto Venta Null....");
                valido = false;
            }

            return valido;
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite abrir dialogo de confirmacion venta
         * @Fecha 27/07/21
         */
        private void goToConfirmarDialog() {
            ConfirmarDialogFragment confirmarDialogFragment = ConfirmarDialogFragment.newInstance("Confirmar Venta", "Esta seguro que desea confirmar la venta...", "CONFIRMAR_VENTA", this);
            confirmarDialogFragment.show(fragmentManager, "ConfirmarVenta");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("ConfirmarVenta");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite ir a la actividad VentaPrincipal
         * @Fecha 04/01/2022
         */
        private void goToVentaPrincipalActivity() {
            Intent intent = new Intent(activity, VentaPrinsipalActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }

    }




}
