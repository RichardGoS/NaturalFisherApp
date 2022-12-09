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
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.interfaces.adapter.IInversionRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IPromocionDetalleFragmentView;

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
    private List<GeneralProductos> productos;
    private IVentaRegistroHolderView ventaRegistroHolderView;
    private IAgregarProductoDialogFragment agregarProductoDialogFragment;
    private IPromocionDetalleFragmentView promocionDetalleFragmentView;

    /**
     * Fase 4 Tarea 3
     * @author Ragoos
     */
    private IInversionRegistroHolderView iInversionRegistroHolderView;

    public ItemAgregarProductoVentaAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<GeneralProductos> productos, IVentaRegistroHolderView ventaRegistroHolderView, IAgregarProductoDialogFragment agregarProductoDialogFragment) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.productos = productos;
        this.ventaRegistroHolderView = ventaRegistroHolderView;
        this.agregarProductoDialogFragment = agregarProductoDialogFragment;
    }

    public ItemAgregarProductoVentaAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<GeneralProductos> productos, IPromocionDetalleFragmentView promocionDetalleFragmentView, IAgregarProductoDialogFragment agregarProductoDialogFragment) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.productos = productos;
        this.promocionDetalleFragmentView = promocionDetalleFragmentView;
        this.agregarProductoDialogFragment = agregarProductoDialogFragment;
    }

    public ItemAgregarProductoVentaAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<GeneralProductos> productos, IInversionRegistroHolderView iInversionRegistroHolderView, IAgregarProductoDialogFragment agregarProductoDialogFragment) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.productos = productos;
        this.iInversionRegistroHolderView = iInversionRegistroHolderView;
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
        holder.bind(this.productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ItemAgregarProductoVentaHolder extends RecyclerView.ViewHolder{

        private GeneralProductos producto;
        private Producto product;
        private Promocion promocion;

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

        void bind(GeneralProductos producto){
            this.producto = producto;

            if(producto != null){
                if(producto.getProducto() != null){
                    product = producto.getProducto();

                    txtNombreProducto.setText(product.getNombre());

                    if(product.getUnidad().equals("Kg")){
                        txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(product.getPrecio()) + " Kg - $" + Utilidades.puntoMil(product.getPrecio()/2) + " Lb", 23));
                    } else if (product.getUnidad().equals("Lb")){
                        txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(product.getPrecio()*2)  + " Kg - " + "$" + Utilidades.puntoMil(product.getPrecio())  + " Lb", 23));
                    } else if (product.getUnidad().equals("Unidad")){
                        txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(product.getPrecio()) + " - Unidad", 23));
                    }

                } else if(producto.getPromocion() != null){
                    promocion = producto.getPromocion();

                    txtNombreProducto.setText(promocion.getNombre());
                    txtPrecio.setText(Utilidades.cortarCadena("$" + Utilidades.puntoMil(promocion.getTotal()) + " - Unidad", 23));
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

            if( ventaRegistroHolderView != null ){
                ventaRegistroHolderView.agregarProducto(producto);
            } else if( promocionDetalleFragmentView != null ){
                if(product != null){
                    promocionDetalleFragmentView.agregarProducto(product);
                }
            } else if( iInversionRegistroHolderView != null ){
                iInversionRegistroHolderView.agregarProducto(product);
            }

            agregarProductoDialogFragment.dismissDialog();

        }
    }
}
