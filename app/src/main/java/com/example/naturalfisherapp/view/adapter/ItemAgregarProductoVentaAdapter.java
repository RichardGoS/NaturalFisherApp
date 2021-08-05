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
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarProductoDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 18/07/2021
 */

public class ItemAgregarProductoVentaAdapter extends RecyclerView.Adapter<ItemAgregarProductoVentaAdapter.ItemAgregarProductoVentaHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<Producto> productos;
    private IVentaRegistroHolderView ventaRegistroHolderView;
    private IAgregarProductoDialogFragment agregarProductoDialogFragment;

    public ItemAgregarProductoVentaAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<Producto> productos, IVentaRegistroHolderView ventaRegistroHolderView, IAgregarProductoDialogFragment agregarProductoDialogFragment) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.productos = productos;
        this.ventaRegistroHolderView = ventaRegistroHolderView;
        this.agregarProductoDialogFragment = agregarProductoDialogFragment;
    }

    @NonNull
    @NotNull
    @Override
    public ItemAgregarProductoVentaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_agregar_producto_venta, parent, false);
        ItemAgregarProductoVentaAdapter.ItemAgregarProductoVentaHolder itemAgregarProductoVentaHolder = new ItemAgregarProductoVentaHolder(view);
        context = parent.getContext();
        return itemAgregarProductoVentaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemAgregarProductoVentaHolder holder, int position) {
        holder.bind(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    class ItemAgregarProductoVentaHolder extends RecyclerView.ViewHolder{

        private Producto producto;

        @BindView(R.id.txtNombreProducto)
        TextView txtNombreProducto;

        @BindView(R.id.txtPrecio)
        TextView txtPrecio;

        @BindView(R.id.llItemProducto)
        LinearLayout llItemProducto;

        public ItemAgregarProductoVentaHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Producto producto){
            this.producto = producto;

            if(this.producto != null){
                txtNombreProducto.setText(producto.getNombre());
                if(producto.getUnidad().equals("Kg")){
                    txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(producto.getPrecio()) + " Kg - $" + Utilidades.puntoMil(producto.getPrecio()/2) + " Lb", 23));
                } else if (producto.getUnidad().equals("Lb")){
                    txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(producto.getPrecio()*2)  + " Kg - " + "$" + Utilidades.puntoMil(producto.getPrecio())  + " Lb", 23));
                } else if (producto.getUnidad().equals("Uni")){
                    txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(producto.getPrecio()*2) + " - Unidad", 23));
                } else if(producto.getUnidad().equals("200 g/12 Unidades")){
                    String[] unidades = producto.getUnidad().split("/");
                    txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(producto.getPrecio()) + " - " + unidades[0] + " - " + unidades[1], 23));
                } else if(producto.getUnidad().equals("400 g/12 Unidades")){
                    String[] unidades = producto.getUnidad().split("/");
                    txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(producto.getPrecio()) + " - " + unidades[0] + " - " + unidades[1], 23));
                }
            }

        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llItemProducto)
        void OnClickProcuto(){
            System.out.println("Producto Agregar...." + producto.getNombre());

            ventaRegistroHolderView.agregarProducto(producto);
            agregarProductoDialogFragment.dismissDialog();

        }
    }
}
