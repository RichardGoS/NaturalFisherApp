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
import com.example.naturalfisherapp.data.models.ItemVenta;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;


/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class ItemVentaAdapter extends RecyclerView.Adapter<ItemVentaAdapter.ItemVentaHolder> {

    List<ItemVenta> items;
    private Context context;
    private android.app.FragmentManager fragmentManager;
    private Activity activity;
    private IVentaRegistroHolderView ventaRegistroHolderView;
    private String modo;

    public ItemVentaAdapter(List<ItemVenta> items, Context context, android.app.FragmentManager fragmentManager, Activity activity, IVentaRegistroHolderView ventaRegistroHolderView, String modo) {
        this.items = items;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.ventaRegistroHolderView = ventaRegistroHolderView;
        this.modo = modo;
    }

    @NonNull
    @NotNull
    @Override
    public ItemVentaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_venta, parent, false);
        ItemVentaAdapter.ItemVentaHolder itemVentaHolder = new ItemVentaHolder(view, modo, ventaRegistroHolderView);
        context = parent.getContext();
        return itemVentaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemVentaHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemVentaHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {

        ItemVenta item;
        String modo;
        IVentaRegistroHolderView ventaRegistroHolderView;

        @BindView(R.id.nombreProducto)
        TextView nombreProducto;

        @BindView(R.id.cantPeso)
        EditText cantPeso;

        @BindView(R.id.precioKg)
        TextView precioKg;

        @BindView(R.id.precioLb)
        TextView precioLb;

        @BindView(R.id.total)
        TextView total;

        @BindView(R.id.llBtnReducirCantidad)
        LinearLayout llBtnReducirCantidad;

        @BindView(R.id.llBtnAumentarCantidad)
        LinearLayout llBtnAumentarCantidad;

        @BindView(R.id.llBtnItem)
        LinearLayout llBtnItem;

        public ItemVentaHolder(@NonNull @NotNull View itemView, String modo, IVentaRegistroHolderView ventaRegistroHolderView) {
            super(itemView);
            this.modo = modo;
            this.ventaRegistroHolderView = ventaRegistroHolderView;
            ButterKnife.bind(this, itemView);
        }

        void bind(final ItemVenta item){
            this.item = item;

            if(this.item != null){

                nombreProducto.setText(item.getProducto().getNombre());
                cantPeso.setText(item.getCant_peso().toString());

                if( modo != null && !modo.equals("creacion") ){
                    llBtnAumentarCantidad.setVisibility(View.GONE);
                    llBtnReducirCantidad.setVisibility(View.GONE);
                    llBtnItem.setVisibility(View.GONE);

                } else {
                    llBtnAumentarCantidad.setVisibility(View.VISIBLE);
                    llBtnReducirCantidad.setVisibility(View.VISIBLE);
                    llBtnItem.setVisibility(View.VISIBLE);
                    cantPeso.setFocusable(true);
                    cantPeso.setOnFocusChangeListener(this);
                    cantPeso.requestFocusFromTouch();
                    //cantPeso.requestFocus();
                }

                Double precioUni = 0.0;

                if(this.item.getUsa_precio_distinto() != null && this.item.getUsa_precio_distinto().equals("S")){
                    if(this.item.getPrecio_distinto() != null && this.item.getPrecio_distinto() > 0){
                        precioUni = this.item.getPrecio_distinto();
                    }
                } else {
                    precioUni = item.getProducto().getPrecio();
                }

                if(item.getProducto().getUnidad().equals("Kg")){
                    precioKg.setText("$" + Utilidades.puntoMil(precioUni) + " Kg");
                    precioLb.setText("$" + Utilidades.puntoMil(precioUni / 2) + " Lb");

                } else if(item.getProducto().getUnidad().equals("Lb")){
                    precioKg.setText("$" + Utilidades.puntoMil(precioUni * 2) + " Kg");
                    precioLb.setText("$" + Utilidades.puntoMil(precioUni) + " Lb");
                } else if(item.getProducto().getUnidad().contains("Uni")){
                    precioKg.setText("$" + Utilidades.puntoMil(precioUni) + " - " + item.getProducto().getUnidad());
                    precioLb.setVisibility(View.GONE);
                }

                total.setText("$" + Utilidades.puntoMil(item.getTotal()));
            }


        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llBtnReducirCantidad)
        void onClickLlBtnReducirCantidad(){
            if(!cantPeso.getText().toString().trim().equals("")){
                if(Double.parseDouble(cantPeso.getText().toString().trim()) <= 0){
                    cantPeso.setText("0.0");
                    item.setCant_peso(0.0);
                } else if(Double.parseDouble(cantPeso.getText().toString().trim()) > 0){
                    Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                    cant-=0.001;
                    item.setCant_peso(cant);
                    cantPeso.setText(""+ Utilidades.restringirDecimales(item.getCant_peso()));
                }
            }

            
            //calcularPrecio();
        }

        @OnClick(R.id.llBtnAumentarCantidad)
        void onClickLlBtnAumentarCantidad(){
            if(!cantPeso.getText().toString().trim().equals("")){
                Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                cant+=0.001;
                item.setCant_peso(cant);
                cantPeso.setText(""+ Utilidades.restringirDecimales(item.getCant_peso()));
            }
            //calcularPrecio();
        }

        @OnTextChanged(R.id.cantPeso)
        void onTextChangedCantPeso(){
            if(!cantPeso.getText().toString().equals("") && !cantPeso.getText().toString().equals("0.0") && !cantPeso.getText().toString().equals("0")){
                calcularPrecio();
                ventaRegistroHolderView.calcularPrecioTotal();
            }
        }

/*
        @OnFocusChange(R.id.cantPeso)
        void onFocusChangeCantPeso(){
            System.out.println("-- Focus CantPeso --  " + cantPeso.requestFocus());
            if(cantPeso.requestFocus()){
                System.out.println("-- Focus CantPeso --");
                if(cantPeso.getText().toString().equals("")){
                    cantPeso.setText("0.0");
                } else if(cantPeso.getText().toString().equals("0") || cantPeso.getText().toString().equals("0.0")){
                    cantPeso.setText("");
                }
            }
        }*/

        @OnClick(R.id.btnEliminarItem)
        void onClickLlBtnEliminarItem(){
            System.out.println("Eliminar Item");
            ventaRegistroHolderView.eliminarItemVenta(this.item.getProducto());
        }

        @OnClick(R.id.btnCambiarPrecio)
        void onClickLlBtnCambiarPrecio(){
            System.out.println("Cambiar Precio");
        }


        /**
         * -------------- METODOS PROPIOS --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite calcular el presio conforme a la cantidad que se va a llevar
         * @Fecha 18/07/21
         */
        private void calcularPrecio() {

            Double precio = 0.0;

            try{
                Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                if(this.item.getUsa_precio_distinto().equals("S")){
                    if(this.item.getPrecio_distinto() != null && this.item.getPrecio_distinto() > 0.0){
                        precio = cant * this.item.getPrecio_distinto();
                    }
                } else {
                    precio = cant * this.item.getProducto().getPrecio();
                }
                item.setTotal(precio);
                item.setCant_peso(cant);
            } catch (Exception e){
                e.printStackTrace();
            }


            total.setText("$ " + Utilidades.puntoMil(precio));

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                System.out.println("-- Focus CantPeso --");
            }

        }
    }
}

