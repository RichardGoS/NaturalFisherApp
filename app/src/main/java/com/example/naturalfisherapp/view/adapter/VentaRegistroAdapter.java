package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
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
import com.example.naturalfisherapp.view.dialog.AgregarClienteDialogFragment;
import com.example.naturalfisherapp.view.dialog.AgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.dialog.ConfirmarDialogFragment;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.dialog.SelectOptionDialogFragment;
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
    private String modo;

    public VentaRegistroAdapter(List<Venta> listVenta, Context context, FragmentManager fragmentManager, Activity activity, IVentaPresenter ventaPresenter, String modo) {
        this.listVenta = listVenta;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.ventaPresenter = ventaPresenter;
        this.modo = modo;
    }

    @NonNull
    @Override
    public VentaRegistroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_venta_registro, parent, false);
        VentaRegistroAdapter.VentaRegistroHolder ventaRegistroHolder = new VentaRegistroHolder(view, ventaPresenter, fragmentManager, modo);
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

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Se agregan las variables para el control logico
         */

        private List<String> listDireccionesCliente;
        private List<String> listTelefonosCliente;
        private int posicionDireccionSeleccionada = 0;
        private int posicionTelefonoSeleccionado = 0;
        private String modo;

        private ProgressDialog progress;

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

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Se agregan los campos de la vista para el control de la misma
         */

        @BindView(R.id.llBtnCambiarClienteVenta)
        LinearLayout llBtnCambiarClienteVenta;

        @BindView(R.id.llBtnCambiarTelefonoVenta)
        LinearLayout llBtnCambiarTelefonoVenta;

        @BindView(R.id.llBtnCambiarDireccionVenta)
        LinearLayout llBtnCambiarDireccionVenta;

        public VentaRegistroHolder(View itemView, IVentaPresenter ventaPresenter, FragmentManager fragmentManager, String modo) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.ventaPresenter = ventaPresenter;
            this.fragmentManager = fragmentManager;
            this.modo = modo;

            listDireccionesCliente = new ArrayList<>();
            listTelefonosCliente = new ArrayList<>();
        }

        void bind(final Venta venta, Activity activity){
            this.venta = venta;
            this.activity = activity;

            progress = new ProgressDialog(activity.getApplicationContext());

            if(venta != null && venta.getCliente() != null){
                fechaVentaDetalle.setText(Utilidades.formatearFecha(venta.getFecha()));
                nombreCliente.setText(venta.getCliente().getNombre());

                if(venta.getDireccion() != null && !venta.getDireccion().equals("")){
                    direccionCliente.setText(venta.getDireccion());

                    if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
                        listDireccionesCliente.add(venta.getDireccion());
                        posicionDireccionSeleccionada = 0;
                    }

                } else {
                    direccionCliente.setText(venta.getCliente().getDireccion());
                    venta.setDireccion(venta.getCliente().getDireccion());

                    if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
                        listDireccionesCliente.add(venta.getDireccion());
                        posicionDireccionSeleccionada = 0;
                    }
                }

                if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
                    llBtnCambiarClienteVenta.setVisibility(View.VISIBLE);
                    llBtnCambiarTelefonoVenta.setVisibility(View.VISIBLE);
                    llBtnCambiarDireccionVenta.setVisibility(View.VISIBLE);

                    if(venta.getCliente().getDireccion_respaldo() != null && !venta.getCliente().getDireccion_respaldo().equals("")){
                        listDireccionesCliente.add(venta.getCliente().getDireccion_respaldo());
                    }

                    if(venta.getCliente().getTelefono_respaldo() != null && !venta.getCliente().getTelefono_respaldo().equals("")){
                        listTelefonosCliente.add(venta.getCliente().getTelefono_respaldo());
                    }
                } else if(modo.equals(EnumVariables.MODO_CONSULTA.getValor())) {
                    llBtnCambiarClienteVenta.setVisibility(View.GONE);
                    llBtnCambiarTelefonoVenta.setVisibility(View.GONE);
                    llBtnCambiarDireccionVenta.setVisibility(View.GONE);
                }

                if(venta.getTelefono() != null && !venta.getTelefono().equals("")){
                    telefonoCliente.setText("" + venta.getTelefono());

                    if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
                        listTelefonosCliente.add(venta.getTelefono());
                        posicionTelefonoSeleccionado = 0;
                    }

                } else {
                    telefonoCliente.setText("" + venta.getCliente().getTelefono());
                    venta.setTelefono(venta.getCliente().getTelefono());

                    if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
                        listTelefonosCliente.add(venta.getCliente().getTelefono());
                    }
                }

                if(this.venta.getItems() != null && !this.venta.getItems().isEmpty()){
                    recyclerViewItems.setVisibility(View.VISIBLE);
                    llAgregarProducto.setVisibility(View.GONE);
                    llBotonesProcesos.setVisibility(View.GONE);
                    btnAgregarProducto.setVisibility(View.GONE);
                    cargarAdapterItems(this.venta.getItems(), modo);
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
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Se agregan los metodos onClick para el manejo de los botodes.
         */
        @OnClick(R.id.llBtnCambiarClienteVenta)
        void onClickLlBtnCambiarClienteVenta(){
            System.out.println("Cambiar Cliente");

            AgregarClienteDialogFragment agregarClienteDialogFragment = AgregarClienteDialogFragment.newInstance(activity,"Cambiar Cliente", venta.getCliente(), this);
            agregarClienteDialogFragment.show(fragmentManager, "CambiarCliente");
            Fragment fragment = fragmentManager.findFragmentByTag("CambiarCliente");
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }

        }

        @OnClick(R.id.llBtnCambiarTelefonoVenta)
        void onClickLlBtnCambiarTelefonoVenta(){
            System.out.println("Cambiar Telefono");

            SelectOptionDialogFragment selectOptionDialogFragment = SelectOptionDialogFragment.newInstance(fragmentManager, "Cambiar Telefono", EnumVariables.NUMBER.getValor(), listTelefonosCliente, posicionTelefonoSeleccionado, this);
            selectOptionDialogFragment.show(fragmentManager, "CambiarTelefono");
            Fragment fragment = fragmentManager.findFragmentByTag("CambiarTelefono");
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }

        }

        @OnClick(R.id.llBtnCambiarDireccionVenta)
        void onClickLlBtnCambiarDireccionVenta(){
            System.out.println("Cambiar Direccion");

            SelectOptionDialogFragment selectOptionDialogFragment = SelectOptionDialogFragment.newInstance(fragmentManager, "Cambiar Direccion",EnumVariables.LETRAS.getValor(), listDireccionesCliente, posicionDireccionSeleccionada, this);
            selectOptionDialogFragment.show(fragmentManager, "CambiarDireccion");
            Fragment fragment = fragmentManager.findFragmentByTag("CambiarDireccion");
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
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

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Metodo encargado de validar y setear los datos del Cliente Cambiados.
         * @param cliente
         */
        @Override
        public void setCliente(Cliente cliente) {
            if(cliente != null){
                venta.setCliente(cliente);
                nombreCliente.setText(cliente.getNombre());
                telefonoCliente.setText(cliente.getTelefono());
                direccionCliente.setText(cliente.getDireccion());
                venta.setDireccion(cliente.getDireccion());
                venta.setTelefono(cliente.getTelefono());

                setListDatosCliente(cliente);
            }
        }

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Metodo encargado de validar y setear la direccion.
         * @param direccion
         */
        @Override
        public void setDireccion(String direccion, int posicionSeleccionada) {
            if(direccion != null && !direccion.equals("")){
                direccionCliente.setText(direccion);
                venta.setDireccion(direccion);

                if(posicionSeleccionada >= 0 && posicionSeleccionada < listDireccionesCliente.size()){
                    this.posicionDireccionSeleccionada = posicionSeleccionada;
                } else {
                    listDireccionesCliente.add(direccion);
                    this.posicionDireccionSeleccionada = listDireccionesCliente.size() - 1;
                }

                /*
                if(listDireccionesCliente != null && !listDireccionesCliente.isEmpty()){
                    for(String direcc:listDireccionesCliente){
                        if(direcc != null && !direcc.equals(direccion)){
                            listDireccionesCliente.add(direccion);
                            posicionDireccionSeleccionada = listDireccionesCliente.size() - 1;
                        } else {
                            break;
                        }
                    }
                }*/
            }
        }

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Metodo encargado de validar y setear el telefono.
         * @param telefono
         */
        @Override
        public void setTelefono(String telefono, int posicionSeleccionada) {
            if(telefono != null && !telefono.equals("")){
                telefonoCliente.setText(telefono);
                venta.setTelefono(telefono);

                if(posicionSeleccionada >= 0 && posicionSeleccionada < listTelefonosCliente.size()){
                    this.posicionTelefonoSeleccionado = posicionSeleccionada;
                } else {
                    listTelefonosCliente.add(telefono);
                    this.posicionTelefonoSeleccionado = listTelefonosCliente.size() - 1;
                }


                /*if(listTelefonosCliente != null && !listTelefonosCliente.isEmpty()){
                    for(String tele:listTelefonosCliente){
                        if(!tele.equals(telefono)){
                            listTelefonosCliente.add(telefono);
                            posicionTelefonoSeleccionado = listTelefonosCliente.size() - 1;
                        } else {
                            break;
                        }
                    }
                }*/
            }
        }

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Metodo encargado de validar y setear el telefono.
         * @param mensaje
         */
        @Override
        public void showProgress(String mensaje) {
            progress= ProgressDialog.show(activity.getApplicationContext(), mensaje,null);
        }

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Metodo encargado de validar y setear el telefono.
         */
        @Override
        public void hideProgress() {
            progress.dismiss();
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
                venta.setTelefono(venta.getTelefono() != null && !venta.getTelefono().equals("") ? venta.getTelefono() : venta.getCliente().getTelefono());
                venta.setDireccion(venta.getDireccion() != null && !venta.getDireccion().equals("") ? venta.getDireccion() : venta.getCliente().getDireccion());
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

        /**
         * Fase 4 Tarea 2
         * @author RagooS
         * @fecha 30/07/2022
         * @descripcion Metodo permite insertar los datos del cliente en todos los campos de este.
         */
        private void setListDatosCliente(Cliente cliente) {

            listTelefonosCliente = new ArrayList<>();
            listDireccionesCliente = new ArrayList<>();

            listDireccionesCliente.add(cliente.getDireccion());
            posicionDireccionSeleccionada = 0;

            listTelefonosCliente.add(cliente.getTelefono());
            posicionTelefonoSeleccionado = 0;

            if(cliente.getDireccion_respaldo() != null && !cliente.getDireccion_respaldo().equals("")){
                listDireccionesCliente.add(cliente.getDireccion_respaldo());
            }

            if(cliente.getTelefono_respaldo() != null && !cliente.getTelefono_respaldo().equals("")){
                listTelefonosCliente.add(cliente.getTelefono_respaldo());
            }
        }
    }
}
