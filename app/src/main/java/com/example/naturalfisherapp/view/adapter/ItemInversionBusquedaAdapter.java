package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaInversiones;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaProveedorInversion;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.fragment.DetalleInversionFragment;

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
public class ItemInversionBusquedaAdapter extends RecyclerView.Adapter<ItemInversionBusquedaAdapter.ItemInversionBusquedaHolder> {

    List<BusquedaInversiones> busquedaInversiones;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;

    public ItemInversionBusquedaAdapter(List<BusquedaInversiones> busquedaInversiones, Context context, FragmentManager fragmentManager, Activity activity) {
        this.busquedaInversiones = busquedaInversiones;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemInversionBusquedaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_inversion_busqueda, parent, false);
        ItemInversionBusquedaAdapter.ItemInversionBusquedaHolder itemInversionBusquedaHolder = new ItemInversionBusquedaHolder(view);
        context = parent.getContext();
        return itemInversionBusquedaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemInversionBusquedaHolder holder, int position) {
        holder.bind(busquedaInversiones.get(position), activity);
    }

    @Override
    public int getItemCount() {
        return busquedaInversiones.size();
    }

    class ItemInversionBusquedaHolder extends RecyclerView.ViewHolder{

        private BusquedaInversiones busquedaInversion;
        private LinearLayoutManager linearLayoutManager;
        Activity activity;

        @BindView(R.id.llItemInversionFecha)
        LinearLayout llItemInversionFecha;

        @BindView(R.id.fechaInversion)
        TextView fechaInversion;

        @BindView(R.id.precioInversion)
        TextView precioInversion;

        @BindView(R.id.txtCantInversionesDia)
        TextView txtCantInversionesDia;

        @BindView(R.id.efRvItemsProveedores)
        RecyclerView efRvItemsProveedores;

        public ItemInversionBusquedaHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final BusquedaInversiones busquedaInversion, Activity activity){
            this.busquedaInversion = busquedaInversion;
            this.activity = activity;

            if(this.busquedaInversion != null){
                fechaInversion.setText(Utilidades.formatearFecha(this.busquedaInversion.getFecha()));
                precioInversion.setText(Utilidades.puntoMil(this.busquedaInversion.getTotal()));

                if(busquedaInversion.getProveedorInversions() != null && !busquedaInversion.getProveedorInversions().isEmpty()){
                    txtCantInversionesDia.setText("" + busquedaInversion.getProveedorInversions().size());
                    cargarItemsAdapter(busquedaInversion.getProveedorInversions());
                }
            }

        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite cargar el adapter
         * @Fecha 26/09/2022
         */
        private void cargarItemsAdapter(List<BusquedaProveedorInversion> proveedorInversions) {
            linearLayoutManager = new LinearLayoutManager(activity,RecyclerView.VERTICAL,false);
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            ItemProveedorInversionBusquedaAdapter itemProveedorInversionBusquedaAdapter = new ItemProveedorInversionBusquedaAdapter(proveedorInversions, context, fragmentManager, activity);
            efRvItemsProveedores.setAdapter(itemProveedorInversionBusquedaAdapter);
            efRvItemsProveedores.setLayoutManager(linearLayoutManager);
        }

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llItemInversionFecha)
        public void goToInversionesFecha(){
            System.out.println("Presiona goToInversionesFecha....");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.inversion_container, DetalleInversionFragment.newInstance(activity, busquedaInversion.getFecha()));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}
