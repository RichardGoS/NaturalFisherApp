package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaClientesVenta;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.fragment.DetalleRegistroVentaFragment;
import com.example.naturalfisherapp.view.fragment.VentaBusquedaFragment;

import org.jetbrains.annotations.NotNull;

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

public class ItemVentaBusquedaAdapter extends RecyclerView.Adapter<ItemVentaBusquedaAdapter.ItemVentaBusquedaHolder> {

    List<BusquedaVentas> busquedaVentas;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;

    public ItemVentaBusquedaAdapter(List<BusquedaVentas> busquedaVentas, Context context, FragmentManager fragmentManager, Activity activity) {
        this.busquedaVentas = busquedaVentas;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public ItemVentaBusquedaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_venta_busqueda, parent, false);
        ItemVentaBusquedaAdapter.ItemVentaBusquedaHolder itemVentaBusquedaHolder = new ItemVentaBusquedaHolder(view);
        context = parent.getContext();
        return itemVentaBusquedaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemVentaBusquedaHolder holder, int position) {
        holder.bind(busquedaVentas.get(position), activity);
    }

    @Override
    public int getItemCount() {
        return busquedaVentas.size();
    }

    class ItemVentaBusquedaHolder extends RecyclerView.ViewHolder{

        private BusquedaVentas busquedaVenta;
        private LinearLayoutManager linearLayoutManager;
        Activity activity;

        @BindView(R.id.llItemVentaFecha)
        LinearLayout llItemVentaFecha;

        @BindView(R.id.efRvItemsClientes)
        RecyclerView efRvItemsClientes;

        @BindView(R.id.fechaVenta)
        TextView fechaVenta;

        @BindView(R.id.precioVenta)
        TextView precioVenta;

        @BindView(R.id.txtCantVentasDia)
        TextView txtCantVentasDia;

        public ItemVentaBusquedaHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final BusquedaVentas busquedaVenta, Activity activity){
            this.busquedaVenta = busquedaVenta;
            this.activity = activity;

            if(this.busquedaVenta != null){
                fechaVenta.setText(Utilidades.formatearFecha(this.busquedaVenta.getFecha()));
                precioVenta.setText(Utilidades.puntoMil(this.busquedaVenta.getTotal()));

                if(busquedaVenta.getClientesVenta() != null && !busquedaVenta.getClientesVenta().isEmpty()){
                    txtCantVentasDia.setText("" + busquedaVenta.getClientesVenta().size());
                    cargarItemsAdapter(busquedaVenta.getClientesVenta());
                }
                //precioVenta.setText(Utilidades.puntoMil(venta.getTotal()));
            }

        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS PROPIOS --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite cargar el adapter
         * @Fecha 10/03/2022
         */
        private void cargarItemsAdapter(List<BusquedaClientesVenta> clientesVenta) {
            linearLayoutManager = new LinearLayoutManager(activity,RecyclerView.VERTICAL,false);
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            ClienteItemVentaBusqueda clienteItemVentaBusqueda = new ClienteItemVentaBusqueda(clientesVenta, activity.getApplicationContext(), fragmentManager, activity);
            efRvItemsClientes.setAdapter(clienteItemVentaBusqueda);
            efRvItemsClientes.setLayoutManager(linearLayoutManager);
        }

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llItemVentaFecha)
        public void goToVentasFecha(){
            System.out.println("Presiona....");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.venta_container, DetalleRegistroVentaFragment.newInstance(activity, busquedaVenta.getFecha()));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
