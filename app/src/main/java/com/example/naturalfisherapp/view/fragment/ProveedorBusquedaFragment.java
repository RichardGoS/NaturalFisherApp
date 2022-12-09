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
import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.presenter.activities.ProveedorPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProveedorPresenter;
import com.example.naturalfisherapp.view.adapter.ItemProveedorAdapter;
import com.example.naturalfisherapp.view.dialog.AgregarCrearProveedorDialogFragment;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IProveedorBusquedaFragmentView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 27/09/2022
 */
public class ProveedorBusquedaFragment extends Fragment implements IProveedorBusquedaFragmentView {

    private Activity activity;
    private FragmentManager fragmentManager;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progress;
    private IProveedorPresenter proveedorPresenter;

    @BindView(R.id.efRvProveedorBusqueda)
    RecyclerView recyclerViewProveedorBusqueda;

    @BindView(R.id.btnCrearCliente)
    FloatingActionButton btnCrearCliente;

    public ProveedorBusquedaFragment() {
    }


    public static ProveedorBusquedaFragment newInstance(Activity activity, FragmentManager fragmentManager) {
        ProveedorBusquedaFragment fragment = new ProveedorBusquedaFragment();
        fragment.activity = activity;
        fragment.fragmentManager = fragmentManager;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proveedor_busqueda, container, false);

        ButterKnife.bind(this, view);

        progress = new ProgressDialog(getContext());

        proveedorPresenter = new ProveedorPresenter(getContext(), this);

        proveedorPresenter.consultarProveedores();

        return view;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnCrearCliente)
    void goToCrearProveedor(){
        AgregarCrearProveedorDialogFragment agregarFragment = AgregarCrearProveedorDialogFragment.newInstance(activity,fragmentManager, "Agregar Proveedor a la Inversion", this);
        agregarFragment.show(fragmentManager, "AgregarProveedor");
        Fragment fragment = fragmentManager.findFragmentByTag("AgregarCliente");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    /**
     * -------------- METODOS INTERFACE IProveedorBusquedaFragment --------------------------------
     */

    @Override
    public void showProgress(String mensaje) {
        if(!progress.isShowing()){
            progress= ProgressDialog.show(getContext(), mensaje,null);
        }
    }

    @Override
    public void hideProgress() {
        if(progress != null && progress.isShowing()){
            progress.dismiss();
        }
    }

    @Override
    public void mostrarDialogoInformativo(String tipo, String informacion) {
        InformacionDialogFragment informacionDialogFragment = InformacionDialogFragment.newInstance(tipo, informacion, this);
        informacionDialogFragment.show(fragmentManager, "InformacionDialog");
        Fragment fragment = fragmentManager.findFragmentByTag("InformacionDialog");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public void cargarAdapter(List<Proveedor> proveedores) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ItemProveedorAdapter itemProveedorAdapter = new ItemProveedorAdapter(getContext(), fragmentManager, activity, proveedores, this);
        recyclerViewProveedorBusqueda.setAdapter(itemProveedorAdapter);
        recyclerViewProveedorBusqueda.setLayoutManager(linearLayoutManager);
    }
}