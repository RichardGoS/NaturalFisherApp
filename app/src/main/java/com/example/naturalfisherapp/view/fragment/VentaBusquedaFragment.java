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
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;
import com.example.naturalfisherapp.data.models.interpretes.DetalleVentas;
import com.example.naturalfisherapp.presenter.activities.ventaPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IVentaPresenter;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.activities.EstadisticaPrincipalActivity;
import com.example.naturalfisherapp.view.adapter.ItemVentaBusquedaAdapter;
import com.example.naturalfisherapp.view.interfaces.fragment.VentaBusquedaFragmentView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public class VentaBusquedaFragment extends Fragment implements VentaBusquedaFragmentView {

    String fecha = "";
    String horaDia = "";
    int anio, mes, dia, hora, minuto;

    boolean isFechIni;
    boolean isHoraIni;

    private Activity activity;
    private FragmentManager fragmentManager;
    private LinearLayoutManager linearLayoutManager;
    private InterfaceApiService service;
    private IVentaPresenter ventaPresenter;

    private String mesActual = "";
    private String mesConsulta = "";


    private DatePickerDialog.OnDateSetListener calendarioFlotante = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            anio = year;
            mes = month;
            dia = day;

            String mesAnio = anio + "/" + (mes + 1);
            mesConsulta = mesAnio;

            System.out.println("MesAño: " + mesAnio);

            if(ventaPresenter != null){
                ventaPresenter.consultarVentaEnMes(mesAnio);
            }
        }
    };

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

    @BindView(R.id.llBtnFecha)
    LinearLayout llBtnFecha;

    @BindView(R.id.llBtnEstadisticaProducto)
    LinearLayout llBtnEstadisticaProducto;

    private ProgressDialog progress;


    public VentaBusquedaFragment() {}

    public static VentaBusquedaFragment newInstance(Activity activity, FragmentManager fragmentManager) {
        VentaBusquedaFragment fragment = new VentaBusquedaFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_venta_busqueda, container, false);

        ButterKnife.bind(this, view);

        InformacionSession.getInstance().setFragmentActual(EnumVariables.FRAGMENT_VENTA_BUSQUEDA.getValor());

        llDatosDetalleVenta.setVisibility(View.GONE);

        progress = new ProgressDialog(getContext());

        ventaPresenter = new ventaPresenter(activity.getApplicationContext(), this);

        Date fechaActual = new Date();
        mesActual = Utilidades.obtenerMesAñoMesFecha(fechaActual);
        mesConsulta = mesActual;

        if(InformacionSession.getInstance().getVentasConsultadas() != null && !InformacionSession.getInstance().getVentasConsultadas().isEmpty()){
            if(InformacionSession.getInstance().getDetalleVentas() != null){
                cargarDatos(InformacionSession.getInstance().getVentasConsultadas(), InformacionSession.getInstance().getDetalleVentas());
            } else {
                //ventaPresenter.consultarVentasDefault();
                ventaPresenter.consultarVentaEnMes(mesActual);
            }
        } else {
            ventaPresenter.consultarVentaEnMes(mesActual);
            //ventaPresenter.consultarVentasDefault();
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
        hideProgress();
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

    @OnClick(R.id.llBtnEstadisticaProducto)
    void onClickLlBtnEstadisticaProducto(){
        if(mesActual != null && !mesActual.equals("")){
            System.out.println(mesActual);
            goToEstaditicaActivity();
        }
    }

    /**
     * -------------- METODOS INTERFACE VentaBusquedaFragmentView --------------------------------
     */

    @Override
    public void cargarDatos(List<BusquedaVentas> busquedaVentas, DetalleVentas detalleVentas) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ItemVentaBusquedaAdapter itemVentaBusquedaAdapter = new ItemVentaBusquedaAdapter(busquedaVentas, activity.getApplicationContext(), fragmentManager, activity);
        recyclerViewVentaBusqueda.setAdapter(itemVentaBusquedaAdapter);
        recyclerViewVentaBusqueda.setLayoutManager(linearLayoutManager);

        llDatosDetalleVenta.setVisibility(View.VISIBLE);
        totalVentaRango.setText(Utilidades.puntoMil(detalleVentas.getTotal()));
        fechaRangoVenta.setText(detalleVentas.getFecha());
        cantRangoVenta.setText("" + detalleVentas.getCantVentas());
    }

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
     * Fase 4 Tarea 1
     * @author RagooS
     * @Descripccion Metodo permite ir al activity EstadisticaProductoBusqueda
     * @ddate 07/08/2022
     */
    private void goToEstaditicaActivity() {
        Intent intent = new Intent(activity, EstadisticaPrincipalActivity.class);
        intent.putExtra("fecha", mesConsulta);
        startActivity(intent);
        activity.finish();
    }

}