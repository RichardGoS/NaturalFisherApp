package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;
import com.example.naturalfisherapp.presenter.activities.ventaPresenter;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.view.adapter.ItemVentaBusquedaAdapter;
import com.example.naturalfisherapp.view.interfaces.VentaBusquedaFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public class VentaBusquedaFragment extends Fragment implements VentaBusquedaFragmentView {

    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private InterfaceApiService service;
    private com.example.naturalfisherapp.presenter.activities.ventaPresenter ventaPresenter;

    @BindView(R.id.efRvVentaBusqueda)
    RecyclerView recyclerViewVentaBusqueda;

    private ProgressDialog progress;


    public VentaBusquedaFragment() {
        // Required empty public constructor
    }

    public static VentaBusquedaFragment newInstance(Activity activity) {
        VentaBusquedaFragment fragment = new VentaBusquedaFragment();
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
        View view = inflater.inflate(R.layout.fragment_venta_busqueda, container, false);

        ButterKnife.bind(this, view);

        progress = new ProgressDialog(getContext());

        ventaPresenter = new ventaPresenter(activity.getApplicationContext(), this);

        ventaPresenter.consultarVentasDefault();

        return view;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE VentaBusquedaFragmentView --------------------------------
     */

    @Override
    public void cargarAdapter(List<BusquedaVentas> busquedaVentas) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ItemVentaBusquedaAdapter itemVentaBusquedaAdapter = new ItemVentaBusquedaAdapter(busquedaVentas, getActivity().getApplicationContext(), fragmentManager, getActivity());
        recyclerViewVentaBusqueda.setAdapter(itemVentaBusquedaAdapter);
        recyclerViewVentaBusqueda.setLayoutManager(linearLayoutManager);
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