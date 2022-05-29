package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.ItemPromocionVenta;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.interfaces.adapter.IItemVentaHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/05/2022
 */

public class ItemProductoPromocionAdapter extends RecyclerView.Adapter<ItemProductoPromocionAdapter.ItemProductoPromocionHolder> {
    private List<ItemPromocionVenta> itemPromocionVentas;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private String modo;
    private IItemVentaHolderView iItemVentaHolderView;

    public ItemProductoPromocionAdapter(List<ItemPromocionVenta> itemPromocionVentas, Context context, FragmentManager fragmentManager, Activity activity, String modo, IItemVentaHolderView iItemVentaHolderView) {
        this.itemPromocionVentas = itemPromocionVentas;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.modo = modo;
        this.iItemVentaHolderView = iItemVentaHolderView;
    }

    @NonNull
    @Override
    public ItemProductoPromocionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_producto_promocion, parent, false);
        ItemProductoPromocionAdapter.ItemProductoPromocionHolder itemProductoPromocionHolder = new ItemProductoPromocionHolder(view, modo, iItemVentaHolderView);
        context = parent.getContext();
        return itemProductoPromocionHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductoPromocionHolder holder, int position) {
        holder.bind(itemPromocionVentas.get(position));
    }

    @Override
    public int getItemCount() {
        return itemPromocionVentas.size();
    }

    class ItemProductoPromocionHolder extends RecyclerView.ViewHolder{

        private String modo;
        private ItemPromocionVenta itemPromocionVenta;
        private IItemVentaHolderView iItemVentaHolderView;

        @BindView(R.id.nombreProductoPromocion)
        TextView nombreProductoPromocion;

        @BindView(R.id.precioKgProductoPromocion)
        TextView precioKgProductoPromocion;

        @BindView(R.id.precioLbProductoPromocion)
        TextView precioLbProductoPromocion;

        @BindView(R.id.totalProductoPromocion)
        TextView totalProductoPromocion;

        @BindView(R.id.cantPesoProductoPromocion)
        EditText cantPesoProductoPromocion;

        @BindView(R.id.llBtnReducirCantidadProductoPromocion)
        LinearLayout llBtnReducirCantidadProductoPromocion;

        @BindView(R.id.llBtnAumentarCantidadProductoPromocion)
        LinearLayout llBtnAumentarCantidadProductoPromocion;

        public ItemProductoPromocionHolder(@NonNull View itemView, String modo, IItemVentaHolderView iItemVentaHolderView) {
            super(itemView);
            this.modo = modo;
            this.iItemVentaHolderView = iItemVentaHolderView;
            ButterKnife.bind(this, itemView);
        }

        void bind(ItemPromocionVenta itemPromocionVenta){
            this.itemPromocionVenta = itemPromocionVenta;

            if(this.itemPromocionVenta != null){

                if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
                    llBtnAumentarCantidadProductoPromocion.setVisibility(View.VISIBLE);
                    llBtnReducirCantidadProductoPromocion.setVisibility(View.VISIBLE);
                    cantPesoProductoPromocion.setFocusable(true);
                    cantPesoProductoPromocion.setEnabled(true);
                } else {
                    llBtnAumentarCantidadProductoPromocion.setVisibility(View.GONE);
                    llBtnReducirCantidadProductoPromocion.setVisibility(View.GONE);
                    cantPesoProductoPromocion.setFocusable(false);
                    cantPesoProductoPromocion.setEnabled(false);
                }

                nombreProductoPromocion.setText(itemPromocionVenta.getProducto().getNombre());
                //precioKgProductoPromocion.setText(itemPromocionVenta.getPrecio_venta());
                //totalProductoPromocion.setText("$" + itemPromocionVenta.getTotal());

                setEdtPrecioProducto(this.itemPromocionVenta.getPrecio_venta());

                cantPesoProductoPromocion.setText("" + Utilidades.restringirDecimales(this.itemPromocionVenta.getCant_peso()));

                totalProductoPromocion.setText("$ " + Utilidades.puntoMil(this.itemPromocionVenta.getTotal()));
            }

        }

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llBtnReducirCantidadProductoPromocion)
        void onClickLlBtnReducirCantidad(){
            if(!cantPesoProductoPromocion.getText().toString().trim().equals("")){
                if(Double.parseDouble(cantPesoProductoPromocion.getText().toString().trim()) <= 0){
                    cantPesoProductoPromocion.setText("0.0");
                    itemPromocionVenta.setCant_peso(0.0);
                } else if(Double.parseDouble(cantPesoProductoPromocion.getText().toString().trim()) > 0){
                    Double cant = Double.parseDouble(cantPesoProductoPromocion.getText().toString().trim());
                    cant-=0.001;
                    itemPromocionVenta.setCant_peso(cant);
                    cantPesoProductoPromocion.setText(""+ Utilidades.restringirDecimales(itemPromocionVenta.getCant_peso()));
                }
            }


            //calcularPrecio();
        }

        @OnClick(R.id.llBtnAumentarCantidadProductoPromocion)
        void onClickLlBtnAumentarCantidad(){
            if(!cantPesoProductoPromocion.getText().toString().trim().equals("")){
                Double cant = Double.parseDouble(cantPesoProductoPromocion.getText().toString().trim());
                cant+=0.001;
                itemPromocionVenta.setCant_peso(cant);
                cantPesoProductoPromocion.setText(""+ Utilidades.restringirDecimales(itemPromocionVenta.getCant_peso()));
            }
            //calcularPrecio();
        }

        @OnTextChanged(R.id.cantPesoProductoPromocion)
        void onTextChangedCantPeso(){
            if(!cantPesoProductoPromocion.getText().toString().equals("") && !cantPesoProductoPromocion.getText().toString().equals("0.0") && !cantPesoProductoPromocion.getText().toString().equals("0")){
                calcularPrecio();
                iItemVentaHolderView.calcularPrecioTotalPromocion();
            }
        }


        /**
         * -------------- METODOS PROPIOS --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite calcular el precio conforme a la cantidad que se va a llevar
         * @Fecha 10/05/22
         */
        private void calcularPrecio() {

            Double precio = 0.0;

            try{
                Double cant = Double.parseDouble(cantPesoProductoPromocion.getText().toString().trim());

                precio = cant * this.itemPromocionVenta.getPrecio_venta();

                itemPromocionVenta.setTotal(precio);
                itemPromocionVenta.setCant_peso(cant);
            } catch (Exception e){
                e.printStackTrace();
            }

            totalProductoPromocion.setText("$ " + Utilidades.puntoMil(precio));

        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite setear la informacion en los campos deprecio producto
         * @Fecha 10/05/22
         */
        private void setEdtPrecioProducto(Double precioUni) {

            if(this.itemPromocionVenta.getProducto().getUnidad().equals("Kg")){
                precioKgProductoPromocion.setText("$" + Utilidades.puntoMil(precioUni) + " Kg");
                precioLbProductoPromocion.setText("$" + Utilidades.puntoMil(precioUni / 2) + " Lb");
            } else if(this.itemPromocionVenta.getProducto().getUnidad().equals("Lb")){
                precioKgProductoPromocion.setText("$" + Utilidades.puntoMil(precioUni * 2) + " Kg");
                precioLbProductoPromocion.setText("$" + Utilidades.puntoMil(precioUni) + " Lb");
            } else if(this.itemPromocionVenta.getProducto().getUnidad().contains("Uni")){
                precioKgProductoPromocion.setText("$" + Utilidades.puntoMil(precioUni) + " - " + this.itemPromocionVenta.getProducto().getUnidad());
                precioLbProductoPromocion.setVisibility(View.GONE);
            }

        }
    }
}
