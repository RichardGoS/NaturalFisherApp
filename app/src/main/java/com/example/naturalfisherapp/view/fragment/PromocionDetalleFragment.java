package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.ItemPromocion;
import com.example.naturalfisherapp.data.models.ItemVenta;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.presenter.activities.ProductoPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.activities.ProductoPrincipalActivity;
import com.example.naturalfisherapp.view.adapter.ItemPromocionAdapter;
import com.example.naturalfisherapp.view.dialog.AgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.dialog.ConfirmarDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IPromocionDetalleFragmentView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 29/04/2022
 */

public class PromocionDetalleFragment extends Fragment implements IPromocionDetalleFragmentView {

    private Activity activity;
    private ProgressDialog progress;
    private LinearLayoutManager linearLayoutManager;
    private FragmentManager fragmentManager;
    private IProductoPresenter productoPresenter;
    //private List<ItemPromocion> itemsPromocion;

    private Promocion promocion;
    private String modo;

    @BindView(R.id.nombrePromocion)
    TextView nombrePromocion;

    @BindView(R.id.descripcionPromocion)
    TextView descripcionPromocion;

    @BindView(R.id.totalPrecioCalculado)
    TextView totalPrecioCalculado;

    @BindView(R.id.porcentage)
    TextView porcentage;

    @BindView(R.id.ganancia)
    TextView ganancia;

    @BindView(R.id.totalPrecio)
    TextView totalPrecio;

    @BindView(R.id.txtBtnRojo)
    TextView txtBtnRojo;

    @BindView(R.id.txtBtnAzul)
    TextView txtBtnAzul;

    @BindView(R.id.btnCrear)
    LinearLayout btnCrear;

    @BindView(R.id.btnCancelar)
    LinearLayout btnCancelar;

    @BindView(R.id.llBtnAgregarProducto)
    LinearLayout llBtnAgregarProducto;

    @BindView(R.id.llAgregarProducto)
    LinearLayout llAgregarProducto;

    @BindView(R.id.llBotonesProcesos)
    LinearLayout llBotonesProcesos;

    @BindView(R.id.btnAgregarProducto)
    FloatingActionButton btnAgregarProducto;

    @BindView(R.id.efRvItemsProductos)
    RecyclerView efRvItemsProductos;

    @BindView(R.id.llEfRvItemsProductos)
    LinearLayout llEfRvItemsProductos;

    public static PromocionDetalleFragment newInstance(Activity activity, Promocion promocion, String modo){
        PromocionDetalleFragment promocionDetalleFragment = new PromocionDetalleFragment();
        promocionDetalleFragment.activity = activity;
        promocionDetalleFragment.promocion = promocion;
        promocionDetalleFragment.modo = modo;
        return promocionDetalleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promocion_detalle, container, false);

        ButterKnife.bind(this, view);

        InformacionSession.getInstance().setFragmentActual(EnumVariables.FRAGMENT_PROMOCION_DETALLE.getValor());

        progress = new ProgressDialog(getContext());

        productoPresenter = new ProductoPresenter(getContext(), this);

        if(promocion != null){
            nombrePromocion.setText(promocion.getNombre());
            descripcionPromocion.setText(promocion.getDescripccion());
            totalPrecio.setText("$ " + Utilidades.puntoMil(promocion.getTotal()));

            if(promocion.getTotalCalculado() != null ){
                totalPrecioCalculado.setText("$" + Utilidades.puntoMil(promocion.getTotalCalculado()));
            } else {
                totalPrecioCalculado.setText("$ 0" );
            }
            if(promocion.getPorcentage() != null){
                porcentage.setText(promocion.getPorcentage() + "%");
            } else {
                porcentage.setText("0%");
            }

            if(promocion.getGanancia() != null){
                ganancia.setText("$ " + Utilidades.puntoMil(promocion.getGanancia()));
            } else {
                ganancia.setText("$ 0" );
            }

            if(promocion.getItems() != null && !promocion.getItems().isEmpty()){
                llEfRvItemsProductos.setVisibility(View.VISIBLE);
                efRvItemsProductos.setVisibility(View.VISIBLE);
                llAgregarProducto.setVisibility(View.GONE);
                llBtnAgregarProducto.setVisibility(View.GONE);

                if(!modo.equals(EnumVariables.MODO_CREACION.getValor())){
                    cargarAdapter(promocion.getItems(), modo);
                    btnAgregarProducto.setVisibility(View.GONE);
                    txtBtnAzul.setText("Editar Promo");
                    txtBtnRojo.setText("Eliminar Promo");
                    mostrarLlProcesos();
                } else {
                    btnAgregarProducto.setVisibility(View.VISIBLE);
                    txtBtnAzul.setText("Confirmar Promo");
                    txtBtnRojo.setText("Cancelar Promo");
                }
            } else {
                llEfRvItemsProductos.setVisibility(View.GONE);
                efRvItemsProductos.setVisibility(View.GONE);
                llAgregarProducto.setVisibility(View.VISIBLE);
                llBtnAgregarProducto.setVisibility(View.VISIBLE);

                btnAgregarProducto.setVisibility(View.GONE);
            }
        }

        return view;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    /**
     * -------------- METODOS INTERFACE IPromocionDetalleFragmentView --------------------------------
     */


    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), "Consultando..",null);
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite cargar el adapter de los itemProductos
     * @Fecha 03/05/2022
     */
    @Override
    public void cargarAdapter(List<ItemPromocion> itemPromocions, String modo) {

        efRvItemsProductos.setVisibility(View.VISIBLE);
        llEfRvItemsProductos.setVisibility(View.VISIBLE);
        llAgregarProducto.setVisibility(View.GONE);

        linearLayoutManager = new LinearLayoutManager(activity,RecyclerView.VERTICAL,false);

        ItemPromocionAdapter itemPromocionAdapter = new ItemPromocionAdapter(promocion.getItems(), getFragmentManager(), activity, this, modo);
        efRvItemsProductos.setAdapter(itemPromocionAdapter);
        efRvItemsProductos.setLayoutManager(linearLayoutManager);

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite agregar producto a los items de venta
     * @Fecha 03/05/2022
     */
    @Override
    public void agregarProducto(Producto producto) {
        ItemPromocion itemPromocion = new ItemPromocion();
        itemPromocion.setProducto(producto);
        itemPromocion.setCant_peso(0D);
        itemPromocion.setPrecio_venta(producto.getPrecio());
        itemPromocion.setTotal(0D);

        if( promocion.getItems() != null && !promocion.getItems().isEmpty() ){
            promocion.getItems().add(itemPromocion);
        } else {
            List<ItemPromocion> itemsPromocion = new ArrayList<>();
            promocion.setItems(itemsPromocion);
            promocion.getItems().add(itemPromocion);
        }

        mostrarLlProcesos();
        calcularPrecioTotal();

        btnAgregarProducto.setVisibility(View.VISIBLE);

        this.cargarAdapter(this.promocion.getItems(), EnumVariables.MODO_CREACION.getValor());
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite eliminar el item perteneciente al producto de la promocion
     * @Fecha 03/05/2022
     */
    @Override
    public void eliminarProductoItem(Producto producto) {
        for( int i=0; i < this.promocion.getItems().size(); i++){
            if(this.promocion.getItems().get(i).getProducto().getId().equals(producto.getId())){
                this.promocion.getItems().remove(i);
                break;
            }
        }

        this.cargarAdapter(this.promocion.getItems(), EnumVariables.MODO_CREACION.getValor());
        calcularPrecioTotal();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar Linearlayout de los procesos
     * @Fecha 03/05/2022
     */
    @Override
    public void mostrarLlProcesos() {
        llBotonesProcesos.setVisibility(View.VISIBLE);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite calcular precio total de los productos
     * @Fecha 03/05/2022
     */
    @Override
    public void calcularPrecioTotal() {
        if(promocion.getItems() != null && !promocion.getItems().isEmpty()){
            Double total = 0.0;
            for (ItemPromocion item: promocion.getItems()){
                total += item.getTotal();
            }

            promocion.setTotalCalculado(total);
            totalPrecioCalculado.setText("$" + Utilidades.puntoMil(promocion.getTotalCalculado()));
            //promocion.setTotal(total);

            if(total > 0){
                Double ganan = promocion.getTotal() - total;
                promocion.setGanancia(ganan);

                Double porcen = (ganan / promocion.getTotal()) * 100;
                promocion.setPorcentage(Utilidades.restringirDecimalesPorcentage(porcen));
            } else {
                promocion.setPorcentage(0D);
                promocion.setGanancia(0D);
            }

            porcentage.setText(promocion.getPorcentage() + " %");
            ganancia.setText("$" + Utilidades.puntoMil(promocion.getGanancia()));

            habilitarDeahabilitarBtnConfirmar(true);
        } else{
            totalPrecioCalculado.setText("$");
            promocion.setTotalCalculado(0.0);
            habilitarDeahabilitarBtnConfirmar(false);
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite habilitar o deshabilitar boton de confirmar venta
     * @Fecha 03/05/2022
     */
    @Override
    public void habilitarDeahabilitarBtnConfirmar(boolean habilita) {
        btnCrear.setClickable(habilita);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite llamar a los metodos encargados de enviar la informacion para crear la promocion
     * @Fecha 05/05/2022
     */
    @Override
    public void crearPromocion() {
        productoPresenter.guardarPromocion(promocion);
    }

    @Override
    public void goToProductoActivity() {
        Intent intent = new Intent(activity, ProductoPrincipalActivity.class);
        intent.putExtra("Recargar", true);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnAgregarProducto)
    void onClickBtnAgregarProducto(){
        goToAgregarProducto();
    }

    @OnClick(R.id.llBtnAgregarProducto)
    void OnClickLlBtnAgregarProducto(){
        goToAgregarProducto();
    }

    @OnClick(R.id.btnCrear)
    void onClickCrear(){
        if(modo.equals(EnumVariables.MODO_CREACION.getValor()) || modo.equals(EnumVariables.MODO_EDICION.getValor())){
            if(validarPromocion()){
                goToConfirmarDialog();
            }
        } else if(modo.equals(EnumVariables.MODO_CONSULTA.getValor())) {
            activarModoEdicion();
        }

    }

    @OnClick(R.id.btnCancelar)
    void onClickCancelar(){
        if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
            goToProductoActivity();
        } else if(modo.equals(EnumVariables.MODO_CONSULTA.getValor()) || modo.equals(EnumVariables.MODO_EDICION.getValor())){
            if(promocion != null){
                promocion.setItems(null);
                productoPresenter.eliminarPromocion(promocion);
            } else {
                Log.e("BtnEliminarPromo", "Error no se puede enviar a eliminar obj promocion es NULL");
            }

        }

    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ir agregar productos
     * @Fecha 01/05/22
     */
    private void goToAgregarProducto() {
        System.out.println("Agregar Producto....");
        AgregarProductoDialogFragment agregarProductoDialogFragment = AgregarProductoDialogFragment.newInstance(getFragmentManager(), this, promocion.getItems());
        agregarProductoDialogFragment.show(getFragmentManager(), "AgregarProducto");
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("AgregarProducto");
        if (fragment != null) {
            activity.getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar si el objeto promocion contiene la informacion necesaria y valida para crear la promocion
     * @Fecha 05/05/2022
     */
    private boolean validarPromocion() {

        boolean valido = false;

        if(promocion != null){
            if(promocion.getItems() != null && !promocion.getItems().isEmpty()){
                for (ItemPromocion item: promocion.getItems()){
                    if(item.getCant_peso() > 0 && item.getTotal() > 0 ){
                        valido = true;
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
            System.out.println("Objeto Promocion Null....");
            valido = false;
        }

        return valido;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite abrir dialogo de confirmacion
     * @Fecha 05/05/2022
     */
    private void goToConfirmarDialog() {
        ConfirmarDialogFragment confirmarDialogFragment = ConfirmarDialogFragment.newInstance("Confirmar Promocion", "Desea terminar la configuracion de la promocion...", "CONFIRMAR_PROMOCION", this);
        confirmarDialogFragment.show(getFragmentManager(), "ConfirmarPromocion");
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("ConfirmarPromocion");
        if (fragment != null) {
            activity.getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite activar el modo edicion
     * @Fecha 14/05/2022
     */
    private void activarModoEdicion() {
        modo = EnumVariables.MODO_EDICION.getValor();
        btnAgregarProducto.setVisibility(View.VISIBLE);
        txtBtnAzul.setText("Actualizar Promo");
        txtBtnRojo.setText("Eliminar Promo");
        if(promocion != null){
            if(promocion.getItems() != null && !promocion.getItems().isEmpty()){
                cargarAdapter(promocion.getItems(), EnumVariables.MODO_EDICION.getValor());
            }
        }
    }
}
