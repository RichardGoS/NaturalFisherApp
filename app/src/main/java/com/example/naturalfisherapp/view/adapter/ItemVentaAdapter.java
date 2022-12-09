package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.ItemPromocionVenta;
import com.example.naturalfisherapp.data.models.ItemVenta;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.PromocionVenta;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.dialog.CambiarPrecioItemProductoVentaDialogFragment;
import com.example.naturalfisherapp.view.interfaces.adapter.IItemVentaHolderView;
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
    private FragmentManager fragmentManager;
    private Activity activity;
    private IVentaRegistroHolderView ventaRegistroHolderView;
    private String modo;

    public ItemVentaAdapter(List<ItemVenta> items, Context context, FragmentManager fragmentManager, Activity activity, IVentaRegistroHolderView ventaRegistroHolderView, String modo) {
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

    class ItemVentaHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener, IItemVentaHolderView {

        private ItemVenta item;
        private String modo;
        private IVentaRegistroHolderView ventaRegistroHolderView;
        private LinearLayoutManager linearLayoutManager;

        /**
         *  ========= PRODUCTO ===========
         */

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

        @BindView(R.id.llItemVentaProducto)
        LinearLayout llItemVentaProducto;

        /**
         *  ========= PROMOCION ===========
         */

        @BindView(R.id.nombrePromocion)
        TextView nombrePromocion;

        @BindView(R.id.porcentageGananciaPromocion)
        TextView porcentageGananciaPromocion;

        @BindView(R.id.gananciaPromocion)
        TextView gananciaPromocion;

        @BindView(R.id.precioCalculadoPromocion)
        TextView precioCalculadoPromocion;

        @BindView(R.id.precioPromocion)
        TextView precioPromocion;

        @BindView(R.id.llBtnItemPromocion)
        LinearLayout llBtnItemPromocion;

        @BindView(R.id.llItemVentaPromocion)
        LinearLayout llItemVentaPromocion;

        @BindView(R.id.efRvProductoPromocion)
        RecyclerView efRvProductoPromocion;

        @BindView(R.id.btnCambiarPrecioPromocion)
        LinearLayout btnCambiarPrecioPromocion;

        @BindView(R.id.btnEliminarItemPromocion)
        LinearLayout btnEliminarItemPromocion;

        public ItemVentaHolder(@NonNull @NotNull View itemView, String modo, IVentaRegistroHolderView ventaRegistroHolderView) {
            super(itemView);
            this.modo = modo;
            this.ventaRegistroHolderView = ventaRegistroHolderView;
            ButterKnife.bind(this, itemView);
        }

        void bind(final ItemVenta item){
            this.item = item;

            if(this.item != null){

                if(item.getProducto() != null){

                    llItemVentaPromocion.setVisibility(View.GONE);
                    llItemVentaProducto.setVisibility(View.VISIBLE);
                    nombreProducto.setText(item.getProducto().getNombre());
                    cantPeso.setText(item.getCant_peso().toString());

                    if( modo != null && !modo.equals(EnumVariables.MODO_CREACION.getValor()) ){
                        llBtnAumentarCantidad.setVisibility(View.GONE);
                        llBtnReducirCantidad.setVisibility(View.GONE);
                        llBtnItem.setVisibility(View.GONE);
                        cantPeso.setFocusable(false);
                        cantPeso.setEnabled(false);
                        //efRvProductoPromocion.setScrollbarFadingEnabled(true);
                    } else {
                        llBtnAumentarCantidad.setVisibility(View.VISIBLE);
                        llBtnReducirCantidad.setVisibility(View.VISIBLE);
                        llBtnItem.setVisibility(View.VISIBLE);
                        cantPeso.setFocusable(true);
                        cantPeso.setOnFocusChangeListener(this);
                        cantPeso.requestFocusFromTouch();
                        cantPeso.setEnabled(true);
                        //efRvProductoPromocion.setScrollbarFadingEnabled(false);
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

                    setEdtPrecioProducto(precioUni);

                    if( modo != null && modo.equals(EnumVariables.MODO_CREACION.getValor()) ){
                        this.item.setUsa_precio_distinto("S");
                        this.item.setPrecio_distinto(precioUni);
                    }

                    total.setText("$" + Utilidades.puntoMil(item.getTotal()));

                } else if(item.getPromocionVenta() != null){

                    llItemVentaPromocion.setVisibility(View.VISIBLE);
                    llItemVentaProducto.setVisibility(View.GONE);


                    nombrePromocion.setText(item.getPromocionVenta().getPromocion().getNombre());
                    precioPromocion.setText("$"+ Utilidades.puntoMil(item.getPromocionVenta().getTotal()));
                    gananciaPromocion.setText("$"+ Utilidades.puntoMil(item.getPromocionVenta().getGanancia()));
                    porcentageGananciaPromocion.setText(Utilidades.restringirDecimales(item.getPromocionVenta().getPorcentage()) + "%");
                    precioCalculadoPromocion.setText("$"+ Utilidades.puntoMil(item.getPromocionVenta().getTotalCalculado()));

                    if(modo.equals(EnumVariables.MODO_CREACION.getValor())){
                        btnEliminarItemPromocion.setVisibility(View.VISIBLE);
                        btnCambiarPrecioPromocion.setVisibility(View.VISIBLE);

                        this.item.setUsa_precio_distinto("S");
                        this.item.setPrecio_distinto(item.getPromocionVenta().getTotal());
                    } else {
                        btnEliminarItemPromocion.setVisibility(View.GONE);
                        btnCambiarPrecioPromocion.setVisibility(View.GONE);
                    }

                    cargarAdapterProductosPromocion(item.getPromocionVenta().getItemsPromocionVenta(), modo);
                }


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
            ventaRegistroHolderView.eliminarItemVenta(this.item);
        }

        @OnClick(R.id.btnEliminarItemPromocion)
        void onClickLlBtnEliminarItemPromocion(){
            System.out.println("Eliminar Item");
            ventaRegistroHolderView.eliminarItemVenta(this.item);
        }

        @OnClick(R.id.btnCambiarPrecio)
        void onClickLlBtnCambiarPrecio(){
            System.out.println("Cambiar Precio");

            CambiarPrecioItemProductoVentaDialogFragment cambiarPrecioItemProductoVentaDialogFragment = CambiarPrecioItemProductoVentaDialogFragment.newInstance("Cambiar Precio Venta", activity, this);
            cambiarPrecioItemProductoVentaDialogFragment.show(fragmentManager, "CambiarPrecio");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("CambiarPrecio");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

        @OnClick(R.id.btnCambiarPrecioPromocion)
        void onClickLlBtnCambiarPrecioPromocion(){
            System.out.println("Cambiar Precio");

            CambiarPrecioItemProductoVentaDialogFragment cambiarPrecioItemProductoVentaDialogFragment = CambiarPrecioItemProductoVentaDialogFragment.newInstance("Cambiar Precio Venta", activity, this);
            cambiarPrecioItemProductoVentaDialogFragment.show(fragmentManager, "CambiarPrecio");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("CambiarPrecio");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                System.out.println("-- Focus CantPeso --");
            }

        }


        /**
         * -------------- METODOS PROPIOS --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite calcular el precio conforme a la cantidad que se va a llevar
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

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite setear los campos del precio unitario
         * @Fecha 18/07/21
         */
        private void setEdtPrecioProducto(Double precioUni) {

            if(item.getProducto() != null){
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
            } else if(item.getPromocionVenta() != null){
                item.getPromocionVenta().setTotal(precioUni);
                calcularPrecioTotalPromocion();
            }
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite cargar el reciclerView con los items de la promocion
         * @Fecha 10/05/22
         */
        private void cargarAdapterProductosPromocion(List<ItemPromocionVenta> itemPromocionVentas, String modo){

            if(itemPromocionVentas != null && !itemPromocionVentas.isEmpty()){
                linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                ItemProductoPromocionAdapter itemProductoPromocionAdapter = new ItemProductoPromocionAdapter(itemPromocionVentas, context, fragmentManager, activity, modo, this);
                efRvProductoPromocion.setAdapter(itemProductoPromocionAdapter);
                efRvProductoPromocion.setLayoutManager(linearLayoutManager);
            }

        }

        /**
         * -------------- METODOS INTERFACE IItemVentaHolderView --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite mostrar barra de carga
         * @Fecha 06/03/22
         */
        @Override
        public void cambiarPrecio(String precioNew) {
            this.item.setUsa_precio_distinto("S");
            this.item.setPrecio_distinto(Double.parseDouble(precioNew.contains(",") ? precioNew.replace(",",".") : precioNew));
            Double precio = Double.parseDouble(precioNew);
            setEdtPrecioProducto(precio);
            calcularPrecio();
            ventaRegistroHolderView.calcularPrecioTotal();
        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite calcular los valores de la promocion
         * @Fecha 11/05/22
         */
        @Override
        public void calcularPrecioTotalPromocion() {

            if(item.getPromocionVenta().getItemsPromocionVenta() != null && !item.getPromocionVenta().getItemsPromocionVenta().isEmpty()){
                Double totalCalculado = 0D;
                Double ganancia = 0D;
                Double porcentageGanancia = 0D;

                for(ItemPromocionVenta itemPromocionVenta: item.getPromocionVenta().getItemsPromocionVenta()){
                    totalCalculado = totalCalculado + itemPromocionVenta.getTotal();
                }

                ganancia = item.getPromocionVenta().getTotal() - totalCalculado;
                porcentageGanancia = (ganancia / item.getPromocionVenta().getTotal())*100;
                porcentageGanancia = Utilidades.restringirDecimalesPorcentage(porcentageGanancia);

                item.getPromocionVenta().setGanancia(ganancia);
                item.getPromocionVenta().setTotalCalculado(totalCalculado);
                item.getPromocionVenta().setPorcentage(porcentageGanancia);

                if(ganancia < 0 ){
                    gananciaPromocion.setTextColor(ContextCompat.getColor(context,R.color.rojo));
                    porcentageGananciaPromocion.setTextColor(ContextCompat.getColor(context,R.color.rojo));
                    precioCalculadoPromocion.setTextColor(ContextCompat.getColor(context,R.color.rojo));
                } else {
                    gananciaPromocion.setTextColor(ContextCompat.getColor(context,R.color.azul_oscuro));
                    porcentageGananciaPromocion.setTextColor(ContextCompat.getColor(context,R.color.azul_oscuro));
                    precioCalculadoPromocion.setTextColor(ContextCompat.getColor(context,R.color.azul_oscuro));
                }

                gananciaPromocion.setText("$"+ Utilidades.puntoMil(item.getPromocionVenta().getGanancia()));
                porcentageGananciaPromocion.setText(Utilidades.restringirDecimales(item.getPromocionVenta().getPorcentage()) + "%");
                precioCalculadoPromocion.setText("$"+ Utilidades.puntoMil(item.getPromocionVenta().getTotalCalculado()));
                precioPromocion.setText("$" + Utilidades.puntoMil(item.getPromocionVenta().getTotal()));

                ventaRegistroHolderView.calcularPrecioTotal();
            }

        }
    }
}

