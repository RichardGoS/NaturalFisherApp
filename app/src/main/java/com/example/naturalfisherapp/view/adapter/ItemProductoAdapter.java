package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.dialog.DetalleProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.IProductoBusquedaFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private List<GeneralProductos> productos;
    private IProductoBusquedaFragmentView iProductoBusquedaFragmentView;

    public ItemProductoAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<GeneralProductos> productos, IProductoBusquedaFragmentView iProductoBusquedaFragmentView) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.productos = productos;
        this.iProductoBusquedaFragmentView = iProductoBusquedaFragmentView;
    }

    @NonNull
    @NotNull
    @Override
    public ItemProductoHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_producto_busqueda, parent, false);
        ItemProductoAdapter.ItemProductoHolder itemProductoHolder = new ItemProductoHolder(view, iProductoBusquedaFragmentView);
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

        private GeneralProductos gProducto;
        private IProductoBusquedaFragmentView iProductoBusquedaFragmentView;

        @BindView(R.id.nombreProducto)
        TextView nombreProducto;

        @BindView(R.id.nombreProductoAdicional)
        TextView nombreProductoAdicional;

        @BindView(R.id.precioProductoLb)
        TextView precioProductoLb;

        @BindView(R.id.precioProductoKg)
        TextView precioProductoKg;

        @BindView(R.id.separadorUnidad)
        TextView separadorUnidad;

        @BindView(R.id.txtDescripccionProducto)
        TextView txtDescripccionProducto;

        @BindView(R.id.llProducto)
        LinearLayout llProducto;

        @BindView(R.id.llImgProducto)
        LinearLayout llImgProducto;

        public ItemProductoHolder(@NonNull @NotNull View itemView, IProductoBusquedaFragmentView iProductoBusquedaFragmentView) {
            super(itemView);
            this.iProductoBusquedaFragmentView = iProductoBusquedaFragmentView;
            ButterKnife.bind(this, itemView);
        }

        void bind(GeneralProductos gProducto){
            this.gProducto = gProducto;

            if(this.gProducto != null){
                if(this.gProducto.getProducto() != null){
                    setInfoProducto(this.gProducto.getProducto());
                } else if(this.gProducto.getPromocion() != null){
                    setInfoPromocion(this.gProducto.getPromocion());
                }
            }
        }

    /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llProducto)
        void onClickLlProducto(){
            if(this.gProducto.getProducto() != null){
                DetalleProductoDialogFragment detalleProductoDialogFragment = DetalleProductoDialogFragment.newInstance(activity, this.gProducto.getProducto(), iProductoBusquedaFragmentView);
                detalleProductoDialogFragment.show(fragmentManager, "DetalleProducto");
                Fragment fragment = fragmentManager.findFragmentByTag("DetalleProducto");
                if (fragment != null) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
            } else if(this.gProducto.getPromocion() != null){
                if(iProductoBusquedaFragmentView != null){
                    iProductoBusquedaFragmentView.goToPromocionDetalle(this.gProducto.getPromocion(), EnumVariables.MODO_CONSULTA.getValor());
                }
            }

        }

        /**
         * -------------- METODOS PROPIOS --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite settear la informacion del producto en la vista
         * @Fecha 10/05/22
         */
        private void setInfoProducto(Producto producto) {
            if(producto != null){

                llImgProducto.setBackgroundResource(R.drawable.ic_agregarproducto);

                if(producto.getNombre().contains(" ")){
                    String[] nom = producto.getNombre().split(" ");
                    String nombre = nom[0];
                    if(nom.length > 2){
                        nombreProductoAdicional.setVisibility(View.VISIBLE);
                        nombre += " " + nom[1];
                        nombreProductoAdicional.setText(nom[2]);
                    } else if(nom.length > 1) {
                        nombre += " " + nom[1];
                    }

                    nombreProducto.setText(nombre);
                } else {
                    nombreProductoAdicional.setVisibility(View.GONE);
                    nombreProducto.setText(producto.getNombre());
                }

                separadorUnidad.setVisibility(View.VISIBLE);
                precioProductoLb.setVisibility(View.VISIBLE);

                if(producto.getUnidad().equals("Kg")){
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()) + " Kg");
                    precioProductoLb.setText("$" + Utilidades.puntoMil(producto.getPrecio()/2) + " Lb");
                } else if (producto.getUnidad().equals("Lb")){
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()*2)  + " Kg");
                    precioProductoLb.setText("$" + Utilidades.puntoMil(producto.getPrecio())  + " Lb");
                } else if (producto.getUnidad().equals("Unidad")){
                    precioProductoKg.setText("$" + Utilidades.puntoMil(producto.getPrecio()) + " - Unidad");
                    separadorUnidad.setVisibility(View.GONE);
                    precioProductoLb.setVisibility(View.GONE);
                }

                if(producto.getDescripcion_unidad()!=null && !producto.getDescripcion_unidad().equals("")){
                    txtDescripccionProducto.setText(producto.getDescripcion_unidad());
                } else {
                    txtDescripccionProducto.setVisibility(View.GONE);
                }
            }
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite settear la informacion de la promocion en la vista
         * @Fecha 10/05/22
         */
        private void setInfoPromocion(Promocion promocion) {
            if(promocion != null){

                llImgProducto.setBackgroundResource(R.drawable.ic_mega_promocion);

                if(promocion.getNombre().contains(" ")){
                    String[] nom = promocion.getNombre().split(" ");
                    String nombre = nom[0];
                    if(nom.length > 2){
                        nombreProductoAdicional.setVisibility(View.VISIBLE);
                        nombre += " " + nom[1];
                        nombreProductoAdicional.setText(nom[2]);
                    } else if(nom.length > 1) {
                        nombre += " " + nom[1];
                    }

                    nombreProducto.setText(nombre);
                } else {
                    nombreProductoAdicional.setVisibility(View.GONE);
                    nombreProducto.setText(promocion.getNombre());
                }

                txtDescripccionProducto.setVisibility(View.VISIBLE);
                txtDescripccionProducto.setText(promocion.getDescripccion());

                precioProductoKg.setText("$" + Utilidades.puntoMil(promocion.getTotal()));

                separadorUnidad.setVisibility(View.GONE);
                precioProductoLb.setVisibility(View.GONE);
            }
        }

    }
}
