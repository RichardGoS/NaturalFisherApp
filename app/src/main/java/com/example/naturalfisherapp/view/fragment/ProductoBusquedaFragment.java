package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.presenter.activities.ProductoPresenter;
import com.example.naturalfisherapp.view.adapter.ItemProductoAdapter;
import com.example.naturalfisherapp.view.interfaces.IProductoBusquedaFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public class ProductoBusquedaFragment extends Fragment implements IProductoBusquedaFragmentView {

    private Activity activity;
    private GridLayoutManager gridLayoutManager;

    private ProductoPresenter productoPresenter;

    private ProgressDialog progress;

    @BindView(R.id.efRvProductoBusqueda)
    RecyclerView efRvProductoBusqueda;

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

        progress = new ProgressDialog(getContext());

        productoPresenter = new ProductoPresenter(getContext(),this);

        productoPresenter.consultarProductos();

        return view;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IProductoBusquedaFragmentView --------------------------------
     */

    @Override
    public void cargarAdapter(List<Producto> productos) {
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ItemProductoAdapter adapter = new ItemProductoAdapter(getContext(), fragmentManager, activity, productos);
        efRvProductoBusqueda.setAdapter(adapter);
        efRvProductoBusqueda.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void showProgress() {
        progress= ProgressDialog.show(getContext(), "Consultando..",null);
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }
}