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
import com.example.naturalfisherapp.data.models.ItemPromocion;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.dialog.CambiarPrecioItemProductoVentaDialogFragment;
import com.example.naturalfisherapp.view.interfaces.adapter.IItemPromocionHolderView;
import com.example.naturalfisherapp.view.interfaces.fragment.IPromocionDetalleFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 02/05/2022
 */

public class ItemPromocionAdapter extends RecyclerView.Adapter<ItemPromocionAdapter.ItemPromocionHolder>{

    private List<ItemPromocion> itemsPromocion;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private IPromocionDetalleFragmentView promocionDetalleFragmentView;
    private String modo;

    public ItemPromocionAdapter(List<ItemPromocion> itemsPromocion, FragmentManager fragmentManager, Activity activity, IPromocionDetalleFragmentView promocionDetalleFragmentView, String modo) {
        this.itemsPromocion = itemsPromocion;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.promocionDetalleFragmentView = promocionDetalleFragmentView;
        this.modo = modo;
    }

    @NonNull
    @Override
    public ItemPromocionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adater_item_promocion, parent, false);
        ItemPromocionAdapter.ItemPromocionHolder itemPromocionHolder = new ItemPromocionHolder(view, promocionDetalleFragmentView, fragmentManager, modo);
        context = parent.getContext();
        return itemPromocionHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPromocionHolder holder, int position) {
    holder.bind(itemsPromocion.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsPromocion.size();
    }

    class ItemPromocionHolder extends RecyclerView.ViewHolder implements IItemPromocionHolderView {

        private ItemPromocion itemPromocion;
        private IPromocionDetalleFragmentView promocionDetalleFragmentView;
        private FragmentManager fragmentManager;
        String modo;

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

        public ItemPromocionHolder(@NonNull View itemView, IPromocionDetalleFragmentView promocionDetalleFragmentView, FragmentManager fragmentManager, String modo) {
            super(itemView);
            this.modo = modo;
            this.promocionDetalleFragmentView = promocionDetalleFragmentView;
            this.fragmentManager = fragmentManager;
            ButterKnife.bind(this, itemView);
        }

        void bind(ItemPromocion itemPromocion){
            this.itemPromocion = itemPromocion;

            if(this.itemPromocion != null){

                nombreProducto.setText(itemPromocion.getProducto().getNombre());
                cantPeso.setText(itemPromocion.getCant_peso().toString());

                if(modo != null){

                    if( modo.equals(EnumVariables.MODO_CONSULTA.getValor())){
                        llBtnAumentarCantidad.setVisibility(View.GONE);
                        llBtnReducirCantidad.setVisibility(View.GONE);
                        llBtnItem.setVisibility(View.GONE);
                        //itemPromocion.setPrecio_venta(itemPromocion.getPrecio_venta());
                        cantPeso.setFocusable(false);
                        cantPeso.setEnabled(false);
                    } else if( modo.equals(EnumVariables.MODO_CREACION.getValor())){
                        llBtnAumentarCantidad.setVisibility(View.VISIBLE);
                        llBtnReducirCantidad.setVisibility(View.VISIBLE);
                        llBtnItem.setVisibility(View.VISIBLE);
                        cantPeso.setFocusable(true);
                        cantPeso.setEnabled(true);
                        //cantPeso.setOnFocusChangeListener(this);
                        cantPeso.requestFocusFromTouch();
                        //cantPeso.requestFocus();
                    } else if( modo.equals(EnumVariables.MODO_EDICION.getValor())){
                        llBtnAumentarCantidad.setVisibility(View.VISIBLE);
                        llBtnReducirCantidad.setVisibility(View.VISIBLE);
                        llBtnItem.setVisibility(View.VISIBLE);
                        cantPeso.setFocusable(true);
                        cantPeso.setEnabled(true);
                        cantPeso.requestFocusFromTouch();
                    }

                }

                Double precioUni = 0.0;

                precioUni = itemPromocion.getPrecio_venta();

                setEdtPrecioProducto(precioUni);

                total.setText("$" + Utilidades.puntoMil(itemPromocion.getTotal()));
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
                    itemPromocion.setCant_peso(0.0);
                } else if(Double.parseDouble(cantPeso.getText().toString().trim()) > 0){
                    Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                    cant-=0.001;
                    itemPromocion.setCant_peso(cant);
                    cantPeso.setText(""+ Utilidades.restringirDecimales(itemPromocion.getCant_peso()));
                }
            }
            calcularPrecio();
        }

        @OnClick(R.id.llBtnAumentarCantidad)
        void onClickLlBtnAumentarCantidad(){
            if(!cantPeso.getText().toString().trim().equals("")){
                Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                cant+=0.001;
                itemPromocion.setCant_peso(cant);
                cantPeso.setText(""+ Utilidades.restringirDecimales(itemPromocion.getCant_peso()));
            }
            calcularPrecio();
        }

        @OnTextChanged(R.id.cantPeso)
        void onTextChangedCantPeso(){
            if(!cantPeso.getText().toString().equals("") && !cantPeso.getText().toString().equals("0.0") && !cantPeso.getText().toString().equals("0")){
                calcularPrecio();
            } else if(cantPeso.getText().toString().equals("0.0") || cantPeso.getText().toString().equals("0")){
                calcularPrecio();
            }
        }

        @OnClick(R.id.btnEliminarItem)
        void onClickLlBtnEliminarItem(){
            System.out.println("Eliminar Item");
            if(promocionDetalleFragmentView != null){
                promocionDetalleFragmentView.eliminarProductoItem(this.itemPromocion.getProducto());
            }

        }

        @OnClick(R.id.btnCambiarPrecio)
        void onClickLlBtnCambiarPrecio(){
            System.out.println("Cambiar Precio");

            CambiarPrecioItemProductoVentaDialogFragment cambiarPrecioItemProductoVentaDialogFragment = CambiarPrecioItemProductoVentaDialogFragment.newInstance("Cambiar Precio Producto", activity, this);
            cambiarPrecioItemProductoVentaDialogFragment.show(fragmentManager, "CambiarPrecio");
            android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("CambiarPrecio");
            if (fragment != null) {
                activity.getFragmentManager().beginTransaction().remove(fragment).commit();
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
                /*if(this.item.getUsa_precio_distinto().equals("S")){
                    if(this.item.getPrecio_distinto() != null && this.item.getPrecio_distinto() > 0.0){
                        precio = cant * this.item.getPrecio_distinto();
                    }
                } else {
                    precio = cant * this.item.getProducto().getPrecio();
                }*/
                precio = cant * this.itemPromocion.getPrecio_venta();
                itemPromocion.setTotal(precio);
                itemPromocion.setCant_peso(cant);
            } catch (Exception e){
                e.printStackTrace();
            }

            total.setText("$ " + Utilidades.puntoMil(precio));

            if(promocionDetalleFragmentView != null){
                promocionDetalleFragmentView.calcularPrecioTotal();
            }

        }

        private void setEdtPrecioProducto(Double precioUni) {
            if(itemPromocion.getProducto().getUnidad().equals("Kg")){
                precioKg.setText("$" + Utilidades.puntoMil(precioUni) + " Kg");
                precioLb.setText("$" + Utilidades.puntoMil(precioUni / 2) + " Lb");
            } else if(itemPromocion.getProducto().getUnidad().equals("Lb")){
                precioKg.setText("$" + Utilidades.puntoMil(precioUni * 2) + " Kg");
                precioLb.setText("$" + Utilidades.puntoMil(precioUni) + " Lb");
            } else if(itemPromocion.getProducto().getUnidad().contains("Uni")){
                precioKg.setText("$" + Utilidades.puntoMil(precioUni) + " - " + itemPromocion.getProducto().getUnidad());
                precioLb.setVisibility(View.GONE);
            }
        }


        /**
         * -------------- METODOS INTERFACE IItemPromocionHolderView --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite mostrar barra de carga
         * @Fecha 03/05/22
         */
        @Override
        public void cambiarPrecio(String precioNew) {
            //this.item.setUsa_precio_distinto("S");
            this.itemPromocion.setPrecio_venta(Double.parseDouble(precioNew.contains(",") ? precioNew.replace(",",".") : precioNew));
            Double precio = Double.parseDouble(precioNew);
            setEdtPrecioProducto(precio);
            calcularPrecio();

        }
    }
}
