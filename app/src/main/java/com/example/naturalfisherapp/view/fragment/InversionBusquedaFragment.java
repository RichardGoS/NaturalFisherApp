package com.example.naturalfisherapp.view.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaInversiones;
import com.example.naturalfisherapp.data.models.interpretes.DetalleInversiones;
import com.example.naturalfisherapp.presenter.activities.InversionPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IInversionPresenter;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.activities.ProveedorPrincipalActivity;
import com.example.naturalfisherapp.view.adapter.ItemInversionBusquedaAdapter;
import com.example.naturalfisherapp.view.dialog.AgregarCrearProveedorDialogFragment;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.interfaces.activities.IInversionPrincipalActivityView;
import com.example.naturalfisherapp.view.interfaces.fragment.IInversionBusquedaFragmentView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 26/09/2022
 */
public class InversionBusquedaFragment extends Fragment implements IInversionBusquedaFragmentView {

    String fecha = "";
    String horaDia = "";
    int anio, mes, dia, hora, minuto;

    boolean isFechIni;
    boolean isHoraIni;

    private Activity activity;
    private FragmentManager fragmentManager;
    private LinearLayoutManager linearLayoutManager;
    private IInversionPresenter inversionPresenter;
    private IInversionPrincipalActivityView inversionPrincipalActivityView;

    private String mesActual = "";
    private String mesConsulta = "";

    private ProgressDialog progress;

    private DatePickerDialog.OnDateSetListener calendarioFlotante = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            anio = year;
            mes = month;
            dia = day;

            String mesAnio = anio + "/" + (mes + 1);
            mesConsulta = mesAnio;

            System.out.println("MesAño: " + mesAnio);

            if(inversionPresenter != null){
                inversionPresenter.consultarInversionesEnMes(mesActual);
            }
        }
    };

    @BindView(R.id.efRvInversionBusqueda)
    RecyclerView recyclerViewInversionBusqueda;

    @BindView(R.id.totalInversionRango)
    TextView totalInversionRango;

    @BindView(R.id.fechaRango)
    TextView fechaRango;

    @BindView(R.id.cantRango)
    TextView cantRango;

    @BindView(R.id.llDatosDetalleInversion)
    LinearLayout llDatosDetalleInversion;

    @BindView(R.id.llBtnFecha)
    LinearLayout llBtnFecha;

    @BindView(R.id.llBtnProveedores)
    LinearLayout llBtnProveedores;

    @BindView(R.id.llBtnEstadisticaProducto)
    LinearLayout llBtnEstadisticaProducto;

    public InversionBusquedaFragment() {}

    public static InversionBusquedaFragment newInstance(Activity activity, FragmentManager fragmentManager, IInversionPrincipalActivityView inversionPrincipalActivityView) {
        InversionBusquedaFragment fragment = new InversionBusquedaFragment();
        fragment.activity = activity;
        fragment.fragmentManager = fragmentManager;
        fragment.inversionPrincipalActivityView = inversionPrincipalActivityView;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inversion_busqueda, container, false);

        ButterKnife.bind(this, view);

        InformacionSession.getInstance().setFragmentActual(EnumVariables.FRAGMENT_INVERSION_BUSQUEDA.getValor());

        llDatosDetalleInversion.setVisibility(View.GONE);

        progress = new ProgressDialog(getContext());

        Date fechaActual = new Date();
        mesActual = Utilidades.obtenerMesAñoMesFecha(fechaActual);
        mesConsulta = mesActual;

        inversionPresenter = new InversionPresenter(getContext(), this);

        if(InformacionSession.getInstance().getBusquedaInversiones() == null || InformacionSession.getInstance().getBusquedaInversiones().isEmpty() || InformacionSession.getInstance().getDetalleInversiones() == null){
            inversionPresenter.consultarInversionesEnMes(mesActual);
        } else {
            cargarDatos(InformacionSession.getInstance().getBusquedaInversiones(), InformacionSession.getInstance().getDetalleInversiones());
        }
        calendarioCargarVariables();

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
    }

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.llBtnFecha)
    void onClickFecha(){
        DatePickerDialog dialogPiker = new DatePickerDialog(activity, calendarioFlotante, anio, mes, dia);
        dialogPiker.getWindow().setBackgroundDrawableResource(R.color.azul_bajo);
        dialogPiker.show();
    }

    @OnClick(R.id.llBtnProveedores)
    void onClickProveedores(){
        goToProveedoresActivity();
    }

    @OnClick(R.id.btnCrearInversion)
    void onClickCrearInversion(){
        goToAgregarCrearProveedorInversion();
    }

    /**
     * -------------- METODOS INTERFACE IInversionBusquedaFragmentView --------------------------------
     */

    @Override
    public void showProgress() {
        if(!progress.isShowing()){
            progress= ProgressDialog.show(getContext(), "Consultando..",null);
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
    public void cargarDatos(List<BusquedaInversiones> busquedaInversiones, DetalleInversiones detalleInversiones) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ItemInversionBusquedaAdapter itemInversionBusquedaAdapter = new ItemInversionBusquedaAdapter(busquedaInversiones, activity.getApplicationContext(), fragmentManager, activity);
        recyclerViewInversionBusqueda.setAdapter(itemInversionBusquedaAdapter);
        recyclerViewInversionBusqueda.setLayoutManager(linearLayoutManager);

        llDatosDetalleInversion.setVisibility(View.VISIBLE);
        totalInversionRango.setText(Utilidades.puntoMil(detalleInversiones.getTotal()));
        fechaRango.setText(detalleInversiones.getFecha());
        cantRango.setText("" + detalleInversiones.getCantInversiones());
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite cargar las variables para el calendario
     * @Fecha 10/03/2022
     */
    private void calendarioCargarVariables() {
        Calendar calendar = Calendar.getInstance();
        anio = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        fecha = Utilidades.getFecha();
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        minuto = calendar.get(Calendar.MINUTE);
        horaDia = Utilidades.getHora();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ir a la Actividad
     * @Fecha 27/09/2022
     */
    private void goToProveedoresActivity() {
        Intent intent = new Intent(activity, ProveedorPrincipalActivity.class);
        startActivity(intent);
        activity.finish();
    }

    /**
     * @author RagooS
     * @fecha 01/10/2022
     * @descripcion Metodo permite desplegar Dialogo para agregar crear el proveedor a la inversion
     */
    private void goToAgregarCrearProveedorInversion() {
        AgregarCrearProveedorDialogFragment agregarFragment = AgregarCrearProveedorDialogFragment.newInstance(activity,fragmentManager, "Agregar Proveedor a la Inversion", inversionPrincipalActivityView);
        agregarFragment.show(fragmentManager, "AgregarProveedor");
        Fragment fragment = fragmentManager.findFragmentByTag("AgregarCliente");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }
}