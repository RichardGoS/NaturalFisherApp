package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaProveedorInversion;
import com.example.naturalfisherapp.utilidades.Utilidades;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 26/09/2022
 */
public class ItemProveedorInversionBusquedaAdapter extends RecyclerView.Adapter<ItemProveedorInversionBusquedaAdapter.ItemProveedorInversionBusquedaHolder> {

    List<BusquedaProveedorInversion> proveedoresInversion;
    private Context context;
    private android.app.FragmentManager fragmentManager;
    private Activity activity;

    public ItemProveedorInversionBusquedaAdapter(List<BusquedaProveedorInversion> proveedoresInversion, Context context, FragmentManager fragmentManager, Activity activity) {
        this.proveedoresInversion = proveedoresInversion;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemProveedorInversionBusquedaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_proveedor_item_inversion_busqueda, parent, false);
        ItemProveedorInversionBusquedaAdapter.ItemProveedorInversionBusquedaHolder itemProveedorInversionBusquedaHolder = new ItemProveedorInversionBusquedaHolder(view);
        context = parent.getContext();
        return itemProveedorInversionBusquedaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProveedorInversionBusquedaHolder holder, int position) {
        holder.bind(proveedoresInversion.get(position));
    }

    @Override
    public int getItemCount() {
        return proveedoresInversion.size();
    }

    class ItemProveedorInversionBusquedaHolder extends RecyclerView.ViewHolder{

        private BusquedaProveedorInversion proveedorInversion;

        @BindView(R.id.llItemProveedorInversion)
        LinearLayout llItemProveedorInversion;

        @BindView(R.id.proveedorItemInversion)
        TextView proveedorItemInversion;

        @BindView(R.id.valorInversionProveedor)
        TextView valorInversionProveedor;

        public ItemProveedorInversionBusquedaHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final BusquedaProveedorInversion proveedorInversion){
            this.proveedorInversion = proveedorInversion;

            if(this.proveedorInversion != null){
                proveedorItemInversion.setText(this.proveedorInversion.getProveedor().getNombre());
                valorInversionProveedor.setText(Utilidades.puntoMil(this.proveedorInversion.getPrecioInversion()));
            }

        }
    }
}
