package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.presenter.activities.ventaPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IVentaPresenter;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.activities.VentaPrinsipalActivity;
import com.example.naturalfisherapp.view.adapter.VentaRegistroAdapter;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IDetalleRegistroVentaFragmentView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public class DetalleRegistroVentaFragment extends Fragment implements IDetalleRegistroVentaFragmentView {

    private LinearLayoutManager linearLayoutManager;
    private Activity activity;
    private Date fecha;
    private IVentaPresenter ventaPresenter;
    private ProgressDialog progress;
    private IDetalleRegistroVentaFragmentView iDetalleRegistroVentaFragmentView;
    private Cliente clienteNew;
    private boolean nuevaVenta = false;

    @BindView(R.id.efRvVentas)
    RecyclerView recyclerViewVentas;


    public DetalleRegistroVentaFragment() {
        // Required empty public constructor
    }


    public static DetalleRegistroVentaFragment newInstance(Activity activity, Date fecha) {
        DetalleRegistroVentaFragment fragment = new DetalleRegistroVentaFragment();
        fragment.activity = activity;
        fragment.fecha = fecha;
        return fragment;
    }

    public static DetalleRegistroVentaFragment newInstance(Activity activity, boolean nuevaVenta, Cliente clienteNew) {
        DetalleRegistroVentaFragment fragment = new DetalleRegistroVentaFragment();
        fragment.activity = activity;
        fragment.nuevaVenta = nuevaVenta;
        fragment.clienteNew = clienteNew;
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
        View view = inflater.inflate(R.layout.fragment_detalle_registro_venta, container, false);

        ButterKnife.bind(this, view);

        InformacionSession.getInstance().setFragmentActual(EnumVariables.FRAGMENT_DETALLE_REGISTRO_VENTA.getValor());

        progress = new ProgressDialog(getContext());
        //consultarVentas();
        ventaPresenter = new ventaPresenter(getContext(), this);

        if(nuevaVenta && clienteNew != null){
            List<Venta> ventas = new  ArrayList<>();
            Venta ventaNew = new Venta();
            ventaNew.setCliente(clienteNew);
            ventaNew.setFecha(Utilidades.obtenerFechaFormatoEstandar(new Date()));
            ventaNew.setTotal(0d);

            ventaNew.setTelefono(clienteNew.getTelefono()!= null ? clienteNew.getTelefono() : "");
            ventaNew.setDireccion(clienteNew.getDireccion()!= null ? clienteNew.getDireccion() : "");

            ventas.add(ventaNew);
            cargarAdapter(ventas, EnumVariables.MODO_CREACION.getValor());
        }else if(fecha != null){
            ventaPresenter.consultarVentaEnFecha(Utilidades.formatearFecha(fecha));
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
     * -------------- METODOS PROPIOS --------------------------------
     */


    /**
     * -------------- METODOS INTERFACE IDetalleRegistroVentaFragmentView --------------------------------
     */

    /**
     * @Autor RagooS
     * @Date 06/07/21
     * @Descripcion Metodo permite cargar el recyclerView
     * @param listVenta Lista de objetos a cargar
     */

    @Override
    public void cargarAdapter(List<Venta> listVenta, String modo) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        VentaRegistroAdapter adapter = new VentaRegistroAdapter(listVenta, getActivity().getApplicationContext(), fragmentManager, getActivity(), ventaPresenter, modo);
        recyclerViewVentas.setAdapter(adapter);
        recyclerViewVentas.setLayoutManager(linearLayoutManager);
    }

    /**
     * @Autor RagooS
     * @Date 06/07/21
     * @Descripcion Metodo permite mostrar la barra de progreso
     * @param mensaje Mesaje a mostrar al usuario
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Date 06/07/21
     * @Descripcion Metodo permite ocultar la barra de progreso
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Date 06/07/21
     * @Descripcion Metodo permite mostrar dialogo informativo
     */
    @Override
    public void mostrarDialogoInformativo(String tipo, String informacion) {
        InformacionDialogFragment informacionDialogFragment = InformacionDialogFragment.newInstance(tipo, informacion, this);
        informacionDialogFragment.show(getFragmentManager(), "InformacionDialog");
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("InformacionDialog");
        if (fragment != null) {
            activity.getFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }

    /**
     * @Autor RagooS
     * @Date 06/07/21
     * @Descripcion Metodo permite ocultar la barra de progreso
     */
    @Override
    public void goToVentaPrinsipalActivity(){
        Intent intent = new Intent(activity, VentaPrinsipalActivity.class);
        startActivity(intent);
        activity.finish();
    }


}