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
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.utilidades.Utilidades;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public class ItemProductoAdapter extends RecyclerView.Adapter<ItemProductoAdapter.ItemProductoHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<Producto> productos;

    public ItemProductoAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<Producto> productos) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.productos = productos;
    }

    @NonNull
    @NotNull
    @Override
    public ItemProductoHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_producto_busqueda, parent, false);
        ItemProductoAdapter.ItemProductoHolder itemProductoHolder = new ItemProductoHolder(view);
        context = parent.getContext();
        return itemProductoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemProductoHolder holder, int position) {
        holder.bind(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    class ItemProductoHolder extends RecyclerView.ViewHolder{

        Producto producto;

        @BindView(R.id.nombreProducto)
        TextView nombreProducto;

        @BindView(R.id.nombreProductoAdicional)
        TextView nombreProductoAdicional;

        @BindView(R.id.precioProductoLb)
        TextView precioProductoLb;

        @BindView(R.id.precioProductoKg)
        TextView precioProductoKg;

        public ItemProductoHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Producto producto){
            this.producto = producto;

            if(this.producto != null){

                if(producto.getNombre().contains(" ")){
                    String[] nom = producto.getNombre().split(" ");
                    nombreProducto.setText(nom[0]);
                    nombreProductoAdicional.setVisibility(View.VISIBLE);
                    nombreProductoAdicional.setText(nom[1]);
                } else {
                    nombreProducto.setText(producto.getNombre());
                }


                if(producto.getUnidad().equals("Kg")){
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()) + " Kg");
                    precioProductoLb.setText("$" + Utilidades.puntoMil(producto.getPrecio()/2) + " Lb");
                } else if (producto.getUnidad().equals("Lb")){
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()*2)  + " Kg");
                    precioProductoLb.setText("$" + Utilidades.puntoMil(producto.getPrecio())  + " Lb");
                } else if (producto.getUnidad().equals("Uni")){
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()*2) + " - Unidad");
                    precioProductoLb.setVisibility(View.GONE);
                } else if(producto.getUnidad().equals("200 g/12 Unidades")){
                    String[] unidades = producto.getUnidad().split("/");
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()) + " - " + unidades[0]);
                    precioProductoLb.setText(unidades[1]);
                } else if(producto.getUnidad().equals("400 g/12 Unidades")){
                    String[] unidades = producto.getUnidad().split("/");
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()) + " - " + unidades[0]);
                    precioProductoLb.setText(unidades[1]);
                }



            }
        }
    }
}
