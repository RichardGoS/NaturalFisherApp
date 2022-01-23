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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;
import com.example.naturalfisherapp.data.models.interpretes.DetalleVentas;
import com.example.naturalfisherapp.presenter.activities.ventaPresenter;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.Utilidades;
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

    @BindView(R.id.totalVentaRango)
    TextView totalVentaRango;

    @BindView(R.id.fechaRangoVenta)
    TextView fechaRangoVenta;

    @BindView(R.id.cantRangoVenta)
    TextView cantRangoVenta;

    @BindView(R.id.llDatosDetalleVenta)
    LinearLayout llDatosDetalleVenta;

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

        llDatosDetalleVenta.setVisibility(View.GONE);

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
    public void cargarDatos(List<BusquedaVentas> busquedaVentas, DetalleVentas detalleVentas) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ItemVentaBusquedaAdapter itemVentaBusquedaAdapter = new ItemVentaBusquedaAdapter(busquedaVentas, getActivity().getApplicationContext(), fragmentManager, getActivity());
        recyclerViewVentaBusqueda.setAdapter(itemVentaBusquedaAdapter);
        recyclerViewVentaBusqueda.setLayoutManager(linearLayoutManager);

        llDatosDetalleVenta.setVisibility(View.VISIBLE);
        totalVentaRango.setText(Utilidades.puntoMil(detalleVentas.getTotal()));
        fechaRangoVenta.setText(detalleVentas.getFecha());
        cantRangoVenta.setText("" + detalleVentas.getCantVentas());
    }

    @Override
    public void showProgress() {
        progress= ProgressDialog.show(getContext(), "Consultando..",null);
    }

    @Override
    public void hideProgress() {
        if(progress != null){
            progress.dismiss();
        }
    }

}