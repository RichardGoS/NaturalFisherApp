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
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.presenter.activities.ClientePresenter;
import com.example.naturalfisherapp.presenter.interfaces.IClientePresenter;
import com.example.naturalfisherapp.view.adapter.ItemClienteAdapter;
import com.example.naturalfisherapp.view.dialog.AgregarClienteDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IClienteBusquedaFragmentView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 29/12/2021
 */
public class ClienteBusquedaFragment extends Fragment implements IClienteBusquedaFragmentView {

    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private IClientePresenter clientePresenter;
    private ProgressDialog progress;

    @BindView(R.id.efRvClienteBusqueda)
    RecyclerView recyclerViewClienteBusqueda;

    @BindView(R.id.btnCrearCliente)
    FloatingActionButton btnCrearCliente;

    public ClienteBusquedaFragment() {
        // Required empty public constructor
    }


    public static ClienteBusquedaFragment newInstance(Activity activity) {
        ClienteBusquedaFragment fragment = new ClienteBusquedaFragment();
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

        View view = inflater.inflate(R.layout.fragment_cliente_busqueda, container, false);

        ButterKnife.bind(this, view);

        progress = new ProgressDialog(getContext());

        clientePresenter = new ClientePresenter(getContext(), this);

        clientePresenter.consultarClientes();

        return view;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnCrearCliente)
    void goToCrearCliente(){
        //System.out.println("CREARRR");
        AgregarClienteDialogFragment agregarClienteDialogFragment = AgregarClienteDialogFragment.newInstance(getActivity(), getActivity().getSupportFragmentManager(), "Crear Cliente", this);
        agregarClienteDialogFragment.show(getActivity().getSupportFragmentManager(), "CrearCliente");
        android.app.Fragment fragment = getActivity().getFragmentManager().findFragmentByTag("CrearCliente");
        if (fragment != null) {
            getActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * -------------- METODOS INTERFACE IClienteBusquedaFragmentView --------------------------------
     */

    @Override
    public void cargarAdapter(List<Cliente> clientes) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ItemClienteAdapter itemClienteAdapter = new ItemClienteAdapter(getContext(), fragmentManager, getActivity(), clientes, this);
        recyclerViewClienteBusqueda.setAdapter(itemClienteAdapter);
        recyclerViewClienteBusqueda.setLayoutManager(linearLayoutManager);
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
        clientePresenter.consultarClientes();
    }
}