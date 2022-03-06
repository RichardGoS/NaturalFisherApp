package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Bodega;
import com.example.naturalfisherapp.presenter.activities.BodegaPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IBodegaPresenter;
import com.example.naturalfisherapp.view.adapter.ItemBodegaAdapter;
import com.example.naturalfisherapp.view.interfaces.fragment.IBodegaBusquedaFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 13/02/2022
 */

public class BodegaBusquedaFragment extends Fragment implements IBodegaBusquedaFragmentView {

    private Activity activity;
    private ProgressDialog progress;
    private LinearLayoutManager linearLayoutManager;
    private IBodegaPresenter bodegaPresenter;

    @BindView(R.id.efRvBodegaBusqueda)
    RecyclerView efRvBodegaBusqueda;

    @BindView(R.id.llNoRegistros)
    LinearLayout llNoRegistros;

    @BindView(R.id.llfRvBodegaBusqueda)
    LinearLayout llfRvBodegaBusqueda;

    public static BodegaBusquedaFragment newInstance(Activity activity){
        BodegaBusquedaFragment bodegaBusquedaFragment = new BodegaBusquedaFragment();
        bodegaBusquedaFragment.activity = activity;
        return bodegaBusquedaFragment;
    }

    public BodegaBusquedaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bodega_busqueda, container, false);

        ButterKnife.bind(this, view);

        progress = new ProgressDialog(getContext());

        bodegaPresenter = new BodegaPresenter(getContext(), this);

        bodegaPresenter.consultarBodega();

        return view;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IBodegaBusquedaFragmentView --------------------------------
     */

    @Override
    public void cargarAdapter(List<Bodega> bodegas) {

        if(bodegas != null && !bodegas.isEmpty()){

            llNoRegistros.setVisibility(View.GONE);
            llfRvBodegaBusqueda.setVisibility(View.VISIBLE);

            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            ItemBodegaAdapter itemBodegaAdapter = new ItemBodegaAdapter(getContext(), fragmentManager, activity, bodegas, this);
            efRvBodegaBusqueda.setAdapter(itemBodegaAdapter);
            efRvBodegaBusqueda.setLayoutManager(linearLayoutManager);
        } else {
            llfRvBodegaBusqueda.setVisibility(View.GONE);
            llNoRegistros.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    @Override
    public void actualizarDatos() {
        bodegaPresenter.consultarBodega();
    }

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnRealizarInventario)
    void goToRealizarInventario(){
        bodegaPresenter.realizarInventarioGeneral();
    }
}
