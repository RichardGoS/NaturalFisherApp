package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.naturalfisherapp.data.models.Inversion;
import com.example.naturalfisherapp.data.models.ItemInversion;
import com.example.naturalfisherapp.data.models.ItemVenta;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.presenter.interfaces.IInversionPresenter;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.activities.InversionPrincipalActivity;
import com.example.naturalfisherapp.view.activities.VentaPrinsipalActivity;
import com.example.naturalfisherapp.view.dialog.AgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.dialog.ConfirmarDialogFragment;
import com.example.naturalfisherapp.view.interfaces.adapter.IInversionRegistroHolderView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 02/10/2022
 */
public class InversionRegistroAdapter extends RecyclerView.Adapter<InversionRegistroAdapter.InversionRegistroHolder>{

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<Inversion> inversiones;
    private String modo;
    private IInversionPresenter inversionPresenter;

    public InversionRegistroAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<Inversion> inversiones, String modo, IInversionPresenter inversionPresenter) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.inversiones = inversiones;
        this.modo = modo;
        this.inversionPresenter = inversionPresenter;
    }

    @NonNull
    @Override
    public InversionRegistroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inversion_registro, parent, false);
        InversionRegistroAdapter.InversionRegistroHolder inversionRegistroHolder = new InversionRegistroHolder(view, inversionPresenter, fragmentManager, modo);
        context = parent.getContext();
        return inversionRegistroHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InversionRegistroHolder holder, int position) {
        holder.bind(inversiones.get(position), activity);
    }

    @Override
    public int getItemCount() {
        return inversiones.size();
    }

    class InversionRegistroHolder extends RecyclerView.ViewHolder implements IInversionRegistroHolderView {

        private Inversion inversion;
        private LinearLayoutManager linearLayoutManager;
        private IInversionPresenter inversionPresenter;
        private Activity activity;
        private FragmentManager fragmentManager;
        private String modo;
        private ProgressDialog progress;

        @BindView(R.id.fechaInversionDetalle)
        TextView fechaInversionDetalle;

        @BindView(R.id.nombreProveedor)
        TextView nombreProveedor;

        @BindView(R.id.nitProveedor)
        TextView nitProveedor;

        @BindView(R.id.direccionProveedor)
        TextView direccionProveedor;

        @BindView(R.id.totalInversion)
        TextView totalInversion;

        @BindView(R.id.efRvItems)
        RecyclerView recyclerViewItems;

        @BindView(R.id.llBtnFecha)
        LinearLayout llBtnFecha;

        @BindView(R.id.llAgregarProducto)
        LinearLayout llAgregarProducto;

        @BindView(R.id.llBtnAgregarProducto)
        LinearLayout llBtnAgregarProducto;

        @BindView(R.id.llBotonesProcesos)
        LinearLayout llBotonesProcesos;

        @BindView(R.id.btnCancelarInversion)
        LinearLayout btnCancelarInversion;

        @BindView(R.id.btnConfirmarInversion)
        LinearLayout btnConfirmarInversion;

        @BindView(R.id.llEfRvItemsProductos)
        LinearLayout llEfRvItemsProductos;

        @BindView(R.id.btnAgregarProducto)
        FloatingActionButton btnAgregarProducto;

        public InversionRegistroHolder(@NonNull View itemView, IInversionPresenter inversionPresenter,FragmentManager fragmentManager, String modo) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.inversionPresenter = inversionPresenter;
            this.fragmentManager = fragmentManager;
            this.modo = modo;
        }

        void bind(final Inversion inversion, Activity activity){
            this.inversion = inversion;
            this.activity = activity;

            progress = new ProgressDialog(activity.getApplicationContext());

            if(inversion != null && inversion.getProveedor() != null){
                fechaInversionDetalle.setText(Utilidades.formatearFecha(inversion.getFecha()));
                nombreProveedor.setText(inversion.getProveedor().getNombre());
                nitProveedor.setText(inversion.getProveedor().getNit());
                String direccion = inversion.getProveedor().getDireccion() + " - " + inversion.getProveedor().getCiudad();
                direccionProveedor.setText(direccion);

                if(this.inversion.getItems() != null && !this.inversion.getItems().isEmpty()){
                    recyclerViewItems.setVisibility(View.VISIBLE);
                    llAgregarProducto.setVisibility(View.GONE);
                    llBotonesProcesos.setVisibility(View.GONE);
                    btnAgregarProducto.setVisibility(View.GONE);
                    cargarAdapterItems(this.inversion.getItems(), modo);
                } else {
                    llEfRvItemsProductos.setVisibility(View.GONE);
                    recyclerViewItems.setVisibility(View.GONE);
                    llAgregarProducto.setVisibility(View.VISIBLE);
                    llBotonesProcesos.setVisibility(View.GONE);
                    btnAgregarProducto.setVisibility(View.GONE);
                }

                totalInversion.setText("$ " + Utilidades.puntoMil(inversion.getTotal()));
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

        @OnClick(R.id.btnAgregarProducto)
        void onClickBtnAgregarProducto(){
            goToAgregarProducto();
        }

        @OnClick(R.id.btnCancelarInversion)
        void onClickCancelarVenta(){
            goToInversionActivity();
        }

        @OnClick(R.id.btnConfirmarInversion)
        void onClickBtnConfirmarInversion(){
            if(validarInversion()){
                goToConfirmarDialog();
            }
        }

        /**
         * -------------- METODOS PROPIOS --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite ir agregar productos
         * @Fecha 12/10/2022
         */
        private void goToAgregarProducto() {
            System.out.println("Agregar Producto....");
            AgregarProductoDialogFragment agregarProductoDialogFragment = AgregarProductoDialogFragment.newInstance(fragmentManager, this, inversion.getItems(), activity);
            agregarProductoDialogFragment.show(fragmentManager, "AgregarProducto");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("AgregarProducto");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }


        /**
         * -------------- METODOS INTERFACE IInversionRegistroHolderView --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite agregar producto a los items de inversion
         * @Fecha 18/10/2022
         */
        @Override
        public void agregarProducto(Producto producto) {
            ItemInversion itemInversion = new ItemInversion();
            itemInversion.setProducto(producto);
            itemInversion.setCant_comprado(0D);
            itemInversion.setPrecio_unitario(producto.getPrecio());
            itemInversion.setPrecio_total(0D);

            if(inversion.getItems() != null){
                inversion.getItems().add(itemInversion);
            } else {
                List<ItemInversion> itemInversions = new ArrayList<>();
                inversion.setItems(itemInversions);
                inversion.getItems().add(itemInversion);
            }

            mostrarLlProcesos();
            calcularPrecioTotal();

            btnAgregarProducto.setVisibility(View.VISIBLE);

            cargarAdapterItems(inversion.getItems(), EnumVariables.MODO_CREACION.getValor());
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite mostrar Linearlayout de los procesos
         * @Fecha 18/10/2022
         */
        @Override
        public void mostrarLlProcesos() {
            llBotonesProcesos.setVisibility(View.VISIBLE);
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite calcular precio total de los productos
         * @Fecha 18/10/2022
         */
        @Override
        public void calcularPrecioTotal() {
            if(inversion.getItems() != null && !inversion.getItems().isEmpty()){
                Double total = 0.0;
                for(ItemInversion item : inversion.getItems()){
                    total += item.getPrecio_total();
                }

                inversion.setTotal(total);
                totalInversion.setText("$" + Utilidades.puntoMil(inversion.getTotal()));
                habilitarDeahabilitarBtnConfirmar(true);

            } else {
                totalInversion.setText("$");
                inversion.setTotal(0D);
                habilitarDeahabilitarBtnConfirmar(false);
            }
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite habilitar o deshabilitar boton de confirmar
         * @Fecha 18/10/2022
         */
        @Override
        public void habilitarDeahabilitarBtnConfirmar(boolean habilita) {
            btnConfirmarInversion.setClickable(habilita);
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite cargar el adapter de los itemProductos
         * @Fecha 18/10/2022
         */
        @Override
        public void cargarAdapterItems(List<ItemInversion> items, String modo) {
            recyclerViewItems.setVisibility(View.VISIBLE);
            llEfRvItemsProductos.setVisibility(View.VISIBLE);
            llAgregarProducto.setVisibility(View.GONE);

            linearLayoutManager = new LinearLayoutManager(activity,RecyclerView.VERTICAL,false);
            ItemInversionAdater itemInversionAdater = new ItemInversionAdater(items, fragmentManager, activity, this, modo);
            recyclerViewItems.setAdapter(itemInversionAdater);
            recyclerViewItems.setLayoutManager(linearLayoutManager);

        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite eliminar el item perteneciente al producto de la inversion
         * @Fecha 18/10/2022
         */
        @Override
        public void eliminarProductoItem(Producto producto) {
            for (int i = 0; i < this.inversion.getItems().size(); i++){
                if(this.inversion.getItems().get(i).getProducto().getId().equals(producto.getId())){
                    this.inversion.getItems().remove(i);
                    break;
                }
            }
            cargarAdapterItems(this.inversion.getItems(), EnumVariables.MODO_CREACION.getValor());
            calcularPrecioTotal();
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite llamar el metodo para enviar infromacion inversion
         * @Fecha 05/11/2022
         */
        @Override
        public void realizarInversion() {
            inversionPresenter.realizarInversion(inversion);
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite ir a la actividad InversionPrincipalActivity
         * @Fecha 05/11/2022
         */
        @Override
        public void goToInversionActivity() {
            Intent intent = new Intent(activity, InversionPrincipalActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite validar si el objeto inversion contiene la informacion necesaria y valida para realizar la inversion
         * @Fecha 05/11/2022
         */
        private boolean validarInversion() {

            boolean valido = false;

            if(inversion != null){
                inversion.setFecha(null);

                if(inversion.getItems() != null && !inversion.getItems().isEmpty()){
                    for (ItemInversion item: inversion.getItems()){
                        if(item.getCant_comprado() > 0 && item.getPrecio_total() > 0 ){
                            if(item.getProducto() != null){
                                valido = true;
                            } else {
                                System.out.println("Item no tiene Producto");
                                valido = false;
                            }

                        } else {
                            System.out.println("Item en 0....");
                            valido = false;
                        }

                    }
                }else {
                    System.out.println("No existen Items....");
                    valido = false;
                }
            } else {
                System.out.println("Objeto Inversion Null....");
                valido = false;
            }

            return valido;
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite abrir dialogo de confirmacion venta
         * @Fecha 05/11/2022
         */
        private void goToConfirmarDialog() {
            ConfirmarDialogFragment confirmarDialogFragment = ConfirmarDialogFragment.newInstance("Confirmar Inversion", "Esta seguro que desea confirmar la inversion...", "CONFIRMAR_INVERSION", this);
            confirmarDialogFragment.show(fragmentManager, "ConfirmarVenta");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("ConfirmarVenta");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

    }
}
