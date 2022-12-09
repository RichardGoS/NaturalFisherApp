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
import com.example.naturalfisherapp.data.models.EstadisticasMesProducto;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.interfaces.fragment.IEstadisticasProductoBusquedaFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fase 4 Tarea 1
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/08/2022
 */
public class ItemEstadisticaProductoAdapter extends RecyclerView.Adapter<ItemEstadisticaProductoAdapter.ItemEstadisticaProductoAdapterHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<EstadisticasMesProducto> listEstadisticasMesProductos;
    private IEstadisticasProductoBusquedaFragmentView estadisticasProductoBusquedaFragmentView;
    private Double total;

    public ItemEstadisticaProductoAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<EstadisticasMesProducto> listEstadisticasMesProductos, Double total, IEstadisticasProductoBusquedaFragmentView estadisticasProductoBusquedaFragmentView) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.listEstadisticasMesProductos = listEstadisticasMesProductos;
        this.estadisticasProductoBusquedaFragmentView = estadisticasProductoBusquedaFragmentView;
        this.total = total;
    }

    @NonNull
    @Override
    public ItemEstadisticaProductoAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_estadistica_producto, parent, false);
        ItemEstadisticaProductoAdapter.ItemEstadisticaProductoAdapterHolder adapterHolder = new ItemEstadisticaProductoAdapterHolder(view);
        context = parent.getContext();
        return adapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemEstadisticaProductoAdapterHolder holder, int position) {
        holder.bind(listEstadisticasMesProductos.get(position), total);
    }

    @Override
    public int getItemCount() {
        return listEstadisticasMesProductos.size();
    }

    class ItemEstadisticaProductoAdapterHolder extends RecyclerView.ViewHolder {

        private EstadisticasMesProducto estadisticasMesProducto;
        private Double total;

        @BindView(R.id.txtNomProducto)
        TextView txtNomProducto;

        @BindView(R.id.txtCantVendido)
        TextView txtCantVendida;

        @BindView(R.id.txtTotal)
        TextView txtTotal;

        @BindView(R.id.txtPorcentage)
        TextView txtPorcentage;

        @BindView(R.id.txtDescripccionProducto)
        TextView txtDescripccionProducto;

        public ItemEstadisticaProductoAdapterHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(EstadisticasMesProducto estadisticasMesProducto, Double total){
            this.estadisticasMesProducto =  estadisticasMesProducto;
            this.total = total;

            if (estadisticasMesProducto != null && estadisticasMesProducto.getProducto() != null){
                txtNomProducto.setText(estadisticasMesProducto.getProducto().getNombre());
                txtDescripccionProducto.setText(this.estadisticasMesProducto.getProducto().getDescripcion_unidad());
                txtCantVendida.setText(" " + Utilidades.restringirDecimales(this.estadisticasMesProducto.getCant_peso()) + " " + this.estadisticasMesProducto.getProducto().getUnidad());
                txtTotal.setText("$" + Utilidades.puntoMil(this.estadisticasMesProducto.getTotal()));

                Double porcentage = (this.estadisticasMesProducto.getTotal() / total) * 100;

                txtPorcentage.setText("" + Utilidades.restringirDecimalesPorcentage(porcentage) + " %");
            }
        }
    }
}
