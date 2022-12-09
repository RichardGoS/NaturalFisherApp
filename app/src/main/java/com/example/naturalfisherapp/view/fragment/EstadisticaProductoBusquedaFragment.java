package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.example.naturalfisherapp.data.models.interpretes.DetalleEstadistica;
import com.example.naturalfisherapp.presenter.activities.EstadisticaPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IEstadisticaPresenter;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.adapter.ItemEstadisticaProductoAdapter;
import com.example.naturalfisherapp.view.interfaces.fragment.IEstadisticasProductoBusquedaFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fase 4 Tarea 1
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/08/2022
 */

public class EstadisticaProductoBusquedaFragment extends Fragment implements IEstadisticasProductoBusquedaFragmentView {

    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progress;
    private String fecha = "";
    private IEstadisticaPresenter estadisticaPresenter;

    @BindView(R.id.llDatosEstadisticas)
    LinearLayout llDatosEstadisticas;

    @BindView(R.id.llFecha)
    LinearLayout llFecha;

    @BindView(R.id.llNumProductos)
    LinearLayout llNumProductos;

    @BindView(R.id.numproductosEstadisticas)
    TextView numproductosEstadisticas;

    @BindView(R.id.totalVenta)
    TextView totalVenta;

    @BindView(R.id.efRvEstadisticasBusqueda)
    RecyclerView efRvEstadisticasBusqueda;

    @BindView(R.id.txtFecha)
    TextView txtFecha;

    public static EstadisticaProductoBusquedaFragment newInstance(Activity activity, String fecha){
        EstadisticaProductoBusquedaFragment fragment = new EstadisticaProductoBusquedaFragment();
        fragment.activity = activity;
        fragment.fecha = fecha;
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

        View view = inflater.inflate(R.layout.fragment_estadistica_producto_busqueda, container, false);

        ButterKnife.bind(this, view);

        InformacionSession.getInstance().setFragmentActual(EnumVariables.FRAGMENT_ESTADISTICA_PRODUCTO_BUSQUEDA.getValor());

        progress = new ProgressDialog(getContext());

        estadisticaPresenter = new EstadisticaPresenter(getContext(), this);

        if(fecha != null && !fecha.equals("")){
            txtFecha.setText(fecha);

            estadisticaPresenter.consultarEstadisticasProductosEnMes(fecha);
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
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */



    /**
     * -------------- METODOS INTERFACE IEstadisticasProductoBusquedaFragmentView --------------------------------
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), "Consultando..",null);
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    @Override
    public void cargarAdapter(DetalleEstadistica detalleEstadistica) {
        if (detalleEstadistica != null && !detalleEstadistica.getEstadisticas().isEmpty()){

            numproductosEstadisticas.setText("" + detalleEstadistica.getCantEstadisticas());
            totalVenta.setText("$ " + Utilidades.puntoMil(detalleEstadistica.getTotal()));

            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            ItemEstadisticaProductoAdapter adapter = new ItemEstadisticaProductoAdapter(getContext(), fragmentManager, activity, detalleEstadistica.getEstadisticas(), detalleEstadistica.getTotal(), this);
            efRvEstadisticasBusqueda.setAdapter(adapter);
            efRvEstadisticasBusqueda.setLayoutManager(linearLayoutManager);
        }
    }

}
