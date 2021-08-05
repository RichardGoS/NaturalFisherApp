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

    public ItemVentaAdapter(List<ItemVenta> items, Context context, android.app.FragmentManager fragmentManager, Activity activity, IVentaRegistroHolderView ventaRegistroHolderView) {
        this.items = items;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.ventaRegistroHolderView = ventaRegistroHolderView;
    }

    @NonNull
    @NotNull
    @Override
    public ItemVentaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_venta, parent, false);
        ItemVentaAdapter.ItemVentaHolder itemVentaHolder = new ItemVentaHolder(view);
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

    class ItemVentaHolder extends RecyclerView.ViewHolder{

        ItemVenta item;

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

        public ItemVentaHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final ItemVenta item){
            this.item = item;

            if(this.item != null){
                nombreProducto.setText(item.getProducto().getNombre());

                cantPeso.setText(item.getCant_peso().toString());

                Double precioUni = item.getProducto().getPrecio();

                if(item.getProducto().getUnidad().equals("Lb")){
                    precioKg.setText("$" + Utilidades.puntoMil(precioUni * 2) + " Kg");
                    precioLb.setText("$" + Utilidades.puntoMil(precioUni) + " Lb");
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
            if(Double.parseDouble(cantPeso.getText().toString().trim()) <= 0){
                cantPeso.setText("0.0");
                item.setCant_peso(0.0);
            } else if(Double.parseDouble(cantPeso.getText().toString().trim()) > 0){
                Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                cant-=0.001;
                item.setCant_peso(cant);
                cantPeso.setText(""+ Utilidades.restringirDecimales(item.getCant_peso()));
            }
            
            //calcularPrecio();
        }

        @OnClick(R.id.llBtnAumentarCantidad)
        void onClickLlBtnAumentarCantidad(){
            Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
            cant+=0.001;
            item.setCant_peso(cant);
            cantPeso.setText(""+ Utilidades.restringirDecimales(item.getCant_peso()));

            //calcularPrecio();
        }

        @OnTextChanged(R.id.cantPeso)
        void onTextChangedCantPeso(){
            calcularPrecio();
            ventaRegistroHolderView.calcularPrecioTotal();
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
                precio = cant * this.item.getProducto().getPrecio();
                item.setTotal(precio);
                item.setCant_peso(cant);
            } catch (Exception e){
                e.printStackTrace();
            }


            total.setText("$ " + Utilidades.puntoMil(precio));

        }

    }
}

