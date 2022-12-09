package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;
import com.example.naturalfisherapp.presenter.activities.ProductoPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.adapter.ItemProductoAdapter;
import com.example.naturalfisherapp.view.dialog.CrearProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IProductoBusquedaFragmentView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public class ProductoBusquedaFragment extends Fragment implements IProductoBusquedaFragmentView {

    private Activity activity;
    private LinearLayoutManager linearLayoutManager;

    private IProductoPresenter productoPresenter;

    private ProgressDialog progress;

    private boolean isFABOpen = false;

    private int numProducto = 0;
    private int numPromocio = 0;

    @BindView(R.id.numProductos)
    TextView numProductos;

    @BindView(R.id.numPromociones)
    TextView numPromociones;

    @BindView(R.id.efRvProductoBusqueda)
    RecyclerView efRvProductoBusqueda;

    @BindView(R.id.btnMenuCreacion)
    FloatingActionButton btnMenuCreacion;

    @BindView(R.id.btnCrearProducto)
    FloatingActionButton btnCrearProducto;

    @BindView(R.id.btnCrearPromocion)
    FloatingActionButton btnCrearPromocion;

    @BindView(R.id.llDatosProductos)
    LinearLayout llDatosProductos;

    @BindView(R.id.llNumProductos)
    LinearLayout llNumProductos;

    @BindView(R.id.llNumPromociones)
    LinearLayout llNumPromociones;

    public ProductoBusquedaFragment() {
        // Required empty public constructor
    }

    public static ProductoBusquedaFragment newInstance(Activity activity) {
        ProductoBusquedaFragment fragment = new ProductoBusquedaFragment();
        fragment.activity = activity;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_producto_busqueda, container, false);

        ButterKnife.bind(this, view);

        InformacionSession.getInstance().setFragmentActual(EnumVariables.FRAGMENT_PRODUCTO_BUSQUEDA.getValor());

        progress = new ProgressDialog(getContext());

        productoPresenter = new ProductoPresenter(getContext(),this);

        if(InformacionSession.getInstance().getProductosConsultados() != null && !InformacionSession.getInstance().getProductosConsultados().isEmpty()){
            boolean recargar = activity.getIntent().getBooleanExtra("Recargar", false);
            if(recargar){
                productoPresenter.consultarProductos();
            } else {
                numProducto = InformacionSession.getInstance().getNumProductos();
                numPromocio = InformacionSession.getInstance().getNumPromocion();
                cargarAdapter(InformacionSession.getInstance().getProductosConsultados(), numProducto, numPromocio);
            }
        } else {
            productoPresenter.consultarProductos();
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
     * -------------- METODOS INTERFACE IProductoBusquedaFragmentView --------------------------------
     */

    @Override
    public void cargarAdapter(List<GeneralProductos> productos, int numProduc, int NumPromoc) {
        //gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        if(productos != null && !productos.isEmpty()){

            llDatosProductos.setVisibility(View.VISIBLE);

            if(numProduc > 0){
                numProductos.setText("" + numProduc);
                llNumProductos.setVisibility(View.VISIBLE);
            } else {
                llNumProductos.setVisibility(View.GONE);
            }

            if(NumPromoc > 0){
                numPromociones.setText("" + NumPromoc);
                llNumPromociones.setVisibility(View.VISIBLE);
            } else {
                llNumPromociones.setVisibility(View.GONE);
            }


            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            ItemProductoAdapter adapter = new ItemProductoAdapter(getContext(), fragmentManager, activity, productos, this);
            efRvProductoBusqueda.setAdapter(adapter);
            efRvProductoBusqueda.setLayoutManager(linearLayoutManager);


        } else {
            llDatosProductos.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress() {
        progress= ProgressDialog.show(getContext(), "Consultando..",null);
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    @Override
    public void actualizarDatos() {
        productoPresenter.consultarProductos();
    }

    @Override
    public void goToPromocionDetalle(Promocion promocion, String modo) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.producto_container, PromocionDetalleFragment.newInstance(activity, promocion, modo));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnMenuCreacion)
    void onClickMenuCrear(){
        if(isFABOpen){
            closeFABMenu();
        } else {
            showFABMenu();
        }
    }

    @OnClick(R.id.btnCrearProducto)
    void onClickCrearProducto(){
        goToDialogCrearProducto();
    }

    @OnClick(R.id.btnCrearPromocion)
    void onClickCrearPromocion(){
        System.out.println("================= PROMOCION ==============================");
        Promocion promocion = new Promocion();
        CrearProductoDialogFragment crearProductoDialogFragment = CrearProductoDialogFragment.newInstance(true, promocion, activity, "Crear Promocion");
        crearProductoDialogFragment.show(getFragmentManager(), "CrearPromocion");
        Fragment fragment = getFragmentManager().findFragmentByTag("CrearPromocion");
        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Descripccion Metodo permite ir a la dialog crearProducto
     * @Autor RagooS
     * @Date 12/01/2022
     */
    private void goToDialogCrearProducto() {
        CrearProductoDialogFragment crearProductoDialogFragment = CrearProductoDialogFragment.newInstance(activity,  "Crear Producto", this);
        crearProductoDialogFragment.show(getFragmentManager(), "CrearProducto");
        Fragment fragment = getFragmentManager().findFragmentByTag("CrearProducto");
        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * @Descripccion Metodo permite mostrar los botones flotantes
     * @Autor RagooS
     * @Date 01/04/22
     */
    private void showFABMenu(){
        isFABOpen=true;
        btnCrearProducto.setVisibility(View.VISIBLE);
        btnCrearPromocion.setVisibility(View.VISIBLE);
        btnCrearProducto.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        btnCrearPromocion.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
    }

    /**
     * @Descripccion Metodo permite ocultar los botones flotantes
     * @Autor RagooS
     * @Date 01/04/22
     */
    private void closeFABMenu(){
        isFABOpen=false;
        btnCrearProducto.animate().translationY(0);
        btnCrearPromocion.animate().translationY(0);
        btnCrearProducto.setVisibility(View.GONE);
        btnCrearPromocion.setVisibility(View.GONE);
    }
}