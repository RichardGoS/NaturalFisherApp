package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Bodega;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.interfaces.fragment.IBodegaBusquedaFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 13/02/2022
 */

public class ItemBodegaAdapter extends RecyclerView.Adapter<ItemBodegaAdapter.ItemBodegaAdapterHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<Bodega> bodegaList;
    private IBodegaBusquedaFragmentView iBodegaBusquedaFragmentView;

    public ItemBodegaAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<Bodega> bodegaList, IBodegaBusquedaFragmentView iBodegaBusquedaFragmentView) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.bodegaList = bodegaList;
        this.iBodegaBusquedaFragmentView = iBodegaBusquedaFragmentView;
    }

    @NonNull
    @NotNull
    @Override
    public ItemBodegaAdapterHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_bodega, parent, false);
        ItemBodegaAdapter.ItemBodegaAdapterHolder itemBodegaAdapterHolder = new ItemBodegaAdapterHolder(view);
        context = parent.getContext();
        return itemBodegaAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemBodegaAdapterHolder holder, int position) {
        holder.bind(bodegaList.get(position));
    }

    @Override
    public int getItemCount() {
        return bodegaList.size();
    }

    class ItemBodegaAdapterHolder extends RecyclerView.ViewHolder {

        private Bodega bodega;

        @BindView(R.id.txtNomProducto)
        TextView txtNomProducto;

        @BindView(R.id.txtCantDisponible)
        TextView txtCantDisponible;

        @BindView(R.id.txtDescripccionProducto)
        TextView txtDescripccionProducto;

        public ItemBodegaAdapterHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Bodega bodega){
            this.bodega = bodega;

            if(this.bodega != null){
                txtNomProducto.setText(this.bodega.getProducto().getNombre());
                txtCantDisponible.setText(" " + Utilidades.restringirDecimales(this.bodega.getCant_disponible()) + " " + this.bodega.getProducto().getUnidad());
                txtDescripccionProducto.setText(this.bodega.getProducto().getDescripcion_unidad());
            }

        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */
    }
}
