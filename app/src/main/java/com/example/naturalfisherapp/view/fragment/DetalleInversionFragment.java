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
import com.example.naturalfisherapp.data.models.Inversion;
import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.presenter.activities.InversionPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IInversionPresenter;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.activities.InversionPrincipalActivity;
import com.example.naturalfisherapp.view.adapter.InversionRegistroAdapter;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IDetalleInversionFragmentView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 01/10/2022
 */
public class DetalleInversionFragment extends Fragment implements IDetalleInversionFragmentView {

    private Activity activity;
    private FragmentManager fragmentManager;
    private ProgressDialog progress;
    private Proveedor proveedor;
    private boolean nuevaInversion = false;
    private IInversionPresenter inversionPresenter;
    private LinearLayoutManager linearLayoutManager;
    private Date fecha;

    @BindView(R.id.efRvInversiones)
    RecyclerView efRvInversiones;

    public DetalleInversionFragment() {
        // Required empty public constructor
    }

    public static DetalleInversionFragment newInstance(Activity activity, Date fecha){
        DetalleInversionFragment fragment = new DetalleInversionFragment();
        fragment.activity = activity;
        fragment.fecha = fecha;
        return fragment;
    }

    public static DetalleInversionFragment newInstance(Activity activity, FragmentManager fragmentManager, Proveedor proveedor, boolean nuevaInversion) {
        DetalleInversionFragment fragment = new DetalleInversionFragment();
        fragment.activity = activity;
        fragment.fragmentManager = fragmentManager;
        fragment.proveedor = proveedor;
        fragment.nuevaInversion = nuevaInversion;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_inversion, container, false);

        ButterKnife.bind(this, view);

        InformacionSession.getInstance().setFragmentActual(EnumVariables.FRAGMENT_DETALLE_INVERSION.getValor());

        progress = new ProgressDialog(getContext());

        inversionPresenter = new InversionPresenter(getContext(), this);

        if(nuevaInversion && proveedor != null){
            List<Inversion> inversiones = new ArrayList<>();
            Inversion inversion = new Inversion();
            inversion.setProveedor(proveedor);
            inversion.setFecha(Utilidades.obtenerFechaFormatoEstandar(new Date()));
            inversion.setTotal(0D);

            inversiones.add(inversion);
            cargarAdapter(inversiones, EnumVariables.MODO_CREACION.getValor());
        } else if( fecha != null){
            inversionPresenter.consultarInversionesEnFecha(Utilidades.formatearFecha(fecha));
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
     * -------------- METODOS INTERFACE IDetalleInversionFragmentView --------------------------------
     */

    /**
     * @author RagooS
     * @fecha 02/10/2022
     * @descripcion Metodo permite mostrar el progress bar
     */
    @Override
    public void showProgress(String mensaje) {
        progress = ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @author RagooS
     * @fecha 02/10/2022
     * @descripcion Metodo permite ocultar el progress bar
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @author RagooS
     * @fecha 02/10/2022
     * @descripcion Metodo permite mostrar el dialogo informativo
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
     * @author RagooS
     * @fecha 02/10/2022
     * @descripcion Metodo permite cargar el adapter con la informacion de las inversiones
     */
    @Override
    public void cargarAdapter(List<Inversion> inversiones, String modo) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        InversionRegistroAdapter adapter = new InversionRegistroAdapter(getContext(),fragmentManager, activity, inversiones,modo,inversionPresenter);
        efRvInversiones.setAdapter(adapter);
        efRvInversiones.setLayoutManager(linearLayoutManager);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ir a la actividad InversionPrincipalActivity
     * @Fecha 05/11/2022
     */
    @Override
    public void goToInversionActivity() {
        Intent intent = new Intent(activity, InversionPrincipalActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}