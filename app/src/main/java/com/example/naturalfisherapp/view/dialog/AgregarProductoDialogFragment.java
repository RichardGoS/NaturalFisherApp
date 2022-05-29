package com.example.naturalfisherapp.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.ItemPromocion;
import com.example.naturalfisherapp.data.models.ItemVenta;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;
import com.example.naturalfisherapp.presenter.activities.ProductoPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.adapter.ItemAgregarProductoVentaAdapter;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IPromocionDetalleFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 17/07/2021
 */

public class AgregarProductoDialogFragment extends DialogFragment implements IAgregarProductoDialogFragment {

    private Dialog dialog;
    private FragmentManager fragmentManager;
    private IProductoPresenter productoPresenter;
    private ProgressDialog progress;
    private LinearLayoutManager linearLayoutManager;
    private IVentaRegistroHolderView ventaRegistroHolderView;
    private List<ItemVenta> items;
    private List<Producto> productos;
    private List<ItemPromocion> itemsPromocion;
    private IPromocionDetalleFragmentView promocionDetalleFragmentView;
    private Activity activity;

    @BindView(R.id.edtBuscador)
    EditText edtBuscador;

    @BindView(R.id.efRvProductos)
    RecyclerView efRvProductos;

    public static AgregarProductoDialogFragment newInstance(FragmentManager fragmentManager, IVentaRegistroHolderView ventaRegistroHolderView, List<ItemVenta> items, Activity activity){
        AgregarProductoDialogFragment agregarProductoDialogFragment = new AgregarProductoDialogFragment();
        agregarProductoDialogFragment.fragmentManager = fragmentManager;
        agregarProductoDialogFragment.ventaRegistroHolderView = ventaRegistroHolderView;
        agregarProductoDialogFragment.items = items;
        agregarProductoDialogFragment.activity = activity;
        return agregarProductoDialogFragment;
    }

    public static AgregarProductoDialogFragment newInstance(FragmentManager fragmentManager, IPromocionDetalleFragmentView promocionDetalleFragmentView, List<ItemPromocion> itemsPromocion){
        AgregarProductoDialogFragment agregarProductoDialogFragment = new AgregarProductoDialogFragment();
        agregarProductoDialogFragment.fragmentManager = fragmentManager;
        agregarProductoDialogFragment.promocionDetalleFragmentView = promocionDetalleFragmentView;
        agregarProductoDialogFragment.itemsPromocion = itemsPromocion;

        return agregarProductoDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        //setHasOptionsMenu(true);
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View agregarProductoDialog = inflater.inflate(R.layout.dialog_fragment_agregar_producto_venta, null);

        ButterKnife.bind(this, agregarProductoDialog);

        builder.setView(agregarProductoDialog);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        productoPresenter = new ProductoPresenter(getContext(), this);

        progress = new ProgressDialog(getContext());

        productos = new ArrayList<>();

        if(items != null && !items.isEmpty()){
            productos = extraerProductos(items);
        } else if(itemsPromocion != null && !itemsPromocion.isEmpty()){
            productos = extraerProductosPromocion(itemsPromocion);
        }

        if(ventaRegistroHolderView != null){
            if(InformacionSession.getInstance().getProductosActivosVenta() != null && !InformacionSession.getInstance().getProductosActivosVenta().isEmpty()){
                List<GeneralProductos> productosDisponibles = validarProductosDisponibles(InformacionSession.getInstance().getProductosActivosVenta(), productos);
                cargarAdapter(productosDisponibles);
            } else {
                productoPresenter.consultarProductosActivosVenta();
            }
        } else if(promocionDetalleFragmentView != null){
            if(InformacionSession.getInstance().getProductosActivosPromo() != null && !InformacionSession.getInstance().getProductosActivosPromo().isEmpty()){
                List<GeneralProductos> productosDisponibles = validarProductosDisponibles(InformacionSession.getInstance().getProductosActivosPromo(), productos);
                cargarAdapter(productosDisponibles);
            } else {
                productoPresenter.consultarProductosPromocion();
            }

        }
        /*if(InformacionSession.getInstance().getProductosActivosVenta() != null && !InformacionSession.getInstance().getProductosActivosVenta().isEmpty()){
            List<GeneralProductos> productosDisponibles = validarProductosDisponibles(InformacionSession.getInstance().getProductosActivosVenta(), productos);
            cargarAdapter(productosDisponibles);
        } else {
            if(ventaRegistroHolderView != null){
                productoPresenter.consultarProductosActivosVenta();
            } else if(promocionDetalleFragmentView != null){

            }

        }*/


        return dialog;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IClientePresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar barra de carga
     * @Fecha 18/07/21
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar barra de carga
     * @Fecha 18/07/21
     */
    @Override
    public void hideProgress() {
        if(progress != null){
            progress.dismiss();
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar dialogo
     * @Fecha 18/07/21
     */
    @Override
    public void dismissDialog() {
        if( dialog != null ){
            dialog.dismiss();
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite cargar el adapter de los productos
     * @Fecha 18/07/21
     */
    @Override
    public void cargarAdapter(List<GeneralProductos> productos) {

        if(productos != null && !productos.isEmpty()){
            if( ventaRegistroHolderView != null ){
                linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                ItemAgregarProductoVentaAdapter itemAgregarProductoVentaAdapter = new ItemAgregarProductoVentaAdapter(activity.getApplicationContext(), fragmentManager, activity, productos, ventaRegistroHolderView, this);
                efRvProductos.setAdapter(itemAgregarProductoVentaAdapter);
                efRvProductos.setLayoutManager(linearLayoutManager);
            } else if( promocionDetalleFragmentView != null ){
                linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                ItemAgregarProductoVentaAdapter itemAgregarProductoVentaAdapter = new ItemAgregarProductoVentaAdapter(getContext(), fragmentManager, getActivity(), productos, promocionDetalleFragmentView, this);
                efRvProductos.setAdapter(itemAgregarProductoVentaAdapter);
                efRvProductos.setLayoutManager(linearLayoutManager);
            }

        } else {
            dismissDialog();
        }
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar los productos disponibles para agregar a la venta
     * @Fecha 10/08/21
     */
    private List<GeneralProductos> validarProductosDisponibles(List<GeneralProductos> productosConsultados, List<Producto> productosOcupados) {

        if(productosConsultados != null && !productosConsultados.isEmpty()){
            List<GeneralProductos> productosDis = new ArrayList<>(productosConsultados);

            if(productosOcupados != null && !productosOcupados.isEmpty()){

                for(Producto productoOcupado: productosOcupados){
                    for(int i = 0; i < productosDis.size(); i++){
                        if(productosDis.get(i).getProducto() != null && productoOcupado.getCodigo() == productosDis.get(i).getProducto().getCodigo()){
                            productosDis.remove(i);
                        }

                    }
                }
            }

            return productosDis;
        } else {
            return null;
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite extraer los productos de los item venta
     * @Fecha 10/08/21
     */
    private List<Producto> extraerProductos(List<ItemVenta> items) {

        List<Producto> productos = new ArrayList<>();

        for( ItemVenta item:items){
            if(item.getProducto() != null){
                productos.add(item.getProducto());
            }
        }
        return productos;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite extraer los productos de los item venta
     * @Fecha 01/05/22
     */
    private List<Producto> extraerProductosPromocion(List<ItemPromocion> items) {

        List<Producto> productos = new ArrayList<>();

        for( ItemPromocion item:items){
            if(item.getProducto() != null){
                productos.add(item.getProducto());
            }
        }

        return productos;

    }
}
