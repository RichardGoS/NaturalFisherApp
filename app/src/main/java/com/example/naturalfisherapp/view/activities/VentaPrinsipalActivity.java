package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.view.adapter.VentaRegistroAdapter;
import com.example.naturalfisherapp.view.dialog.AgregarClienteDialogFragment;
import com.example.naturalfisherapp.view.fragment.VentaBusquedaFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class VentaPrinsipalActivity extends AppCompatActivity {

    private Activity activity;
    //private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManagerVentas;
    private InterfaceApiService service;

    //@BindView(R.id.efRvVentas)
    //RecyclerView recyclerViewVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_prinsipal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        boolean isVenta = getIntent().getBooleanExtra("vender", false);

        if(isVenta){
            AgregarClienteDialogFragment agregarClienteDialogFragment = AgregarClienteDialogFragment.newInstance(getSupportFragmentManager());
            agregarClienteDialogFragment.show(getSupportFragmentManager(), "AgregarCliente");
            android.app.Fragment fragment = getFragmentManager().findFragmentByTag("AgregarCliente");
            if (fragment != null) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        } else {
            goToVentaBusqueda();
        }

        //List<Venta> listVenta = cargarVentasPrueba();

        //List<Venta> listVenta = consultarVentas();

        //cargarAdapter(listVenta);
    }

    /*private void cargarAdapter(List<Venta> listVenta) {

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        VentaRegistroAdapter adapter = new VentaRegistroAdapter(listVenta, getApplicationContext(), fragmentManager, this);
        recyclerViewVentas.setAdapter(adapter);
        recyclerViewVentas.setLayoutManager(linearLayoutManager);

    }*/

    private List<Venta> cargarVentasPrueba() {

        List<Venta> listVenta = new ArrayList<>();
        Venta venta = new Venta();
        Cliente cliente = new Cliente();

        cliente.setId((long) 1);
        cliente.setNombre("Natalia");
        cliente.setTelefono("3125146232");
        cliente.setDireccion("Call. 44 # 32a - 22 Oe");

        venta.setCliente(cliente);
        listVenta.add(venta);

        Venta venta1 = new Venta();
        Cliente cliente1 = new Cliente();
        cliente1.setId((long) 2);
        cliente1.setNombre("Carolina");
        cliente1.setTelefono("3154589724");
        cliente1.setDireccion("Cra. 36 # 2aw - 08 Oe");

        venta1.setCliente(cliente1);
        listVenta.add(venta1);

        Venta venta2 = new Venta();
        Cliente cliente2 = new Cliente();
        cliente2.setId((long) 3);
        cliente2.setNombre("Nacho");
        cliente2.setTelefono("3258429716");
        cliente2.setDireccion("Cra. 22 # 2aw - 08 Oe");

        venta2.setCliente(cliente2);
        listVenta.add(venta2);

        return listVenta;
    }

    /*public List<Venta> consultarVentas(){

        try {
            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Venta>> call = service.getVentas();

            call.enqueue(new Callback<List<Venta>>() {
                @Override
                public void onResponse(Call<List<Venta>> call, Response<List<Venta>> response) {
                    if(response != null){
                        cargarAdapter(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Venta>> call, Throwable t) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }*/

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToMenuPrincipal();
    }



    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Descripccion Metodo permite ir a la actividad Login
     * @Autor RagooS
     * @Date 04/07/21
     */
    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Descripccion Metodo permite ir a la actividad Login
     * @Autor RagooS
     * @Date 04/07/21
     */
    private void goToMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Autor RagooS
     * @Date 06/07/21
     * @Descripccion Metodo permite ir a el fragment VentaBusqueda
     */
    private void goToVentaBusqueda() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.venta_container, VentaBusquedaFragment.newInstance(this));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}