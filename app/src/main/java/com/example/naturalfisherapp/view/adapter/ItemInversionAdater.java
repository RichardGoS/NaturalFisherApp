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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.ItemInversion;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.dialog.CambiarPrecioItemProductoVentaDialogFragment;
import com.example.naturalfisherapp.view.interfaces.adapter.IInversionRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.adapter.IItemInversionHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 18/10/2022
 */
public class ItemInversionAdater extends RecyclerView.Adapter<ItemInversionAdater.ItemInversionHolder>{

    List<ItemInversion> itemInversions;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private IInversionRegistroHolderView inversionRegistroHolderView;
    private String modo;

    public ItemInversionAdater(List<ItemInversion> itemInversions, FragmentManager fragmentManager, Activity activity, IInversionRegistroHolderView inversionRegistroHolderView, String modo) {
        this.itemInversions = itemInversions;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.inversionRegistroHolderView = inversionRegistroHolderView;
        this.modo = modo;
    }

    @NonNull
    @Override
    public ItemInversionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_inversion, parent, false);
        ItemInversionAdater.ItemInversionHolder itemInversionHolder = new ItemInversionAdater.ItemInversionHolder(view, modo, inversionRegistroHolderView);
        context = parent.getContext();
        return itemInversionHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemInversionHolder holder, int position) {
        holder.bind(itemInversions.get(position));
    }

    @Override
    public int getItemCount() {
        return itemInversions.size();
    }

    class ItemInversionHolder extends RecyclerView.ViewHolder implements IItemInversionHolderView {

        private ItemInversion item;
        private String modo;
        private IInversionRegistroHolderView inversionRegistroHolderView;
        private LinearLayoutManager linearLayoutManager;

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

        public ItemInversionHolder(@NonNull View itemView, String modo, IInversionRegistroHolderView inversionRegistroHolderView) {
            super(itemView);
            this.modo = modo;
            this.inversionRegistroHolderView = inversionRegistroHolderView;
            ButterKnife.bind(this, itemView);
        }

        void bind(final ItemInversion item){
            this.item = item;

            if(this.item != null){
                nombreProducto.setText(item.getProducto().getNombre());
                cantPeso.setText(item.getCant_comprado().toString());

                if(modo != null){
                    if( modo.equals(EnumVariables.MODO_CONSULTA.getValor())){
                        llBtnAumentarCantidad.setVisibility(View.GONE);
                        llBtnReducirCantidad.setVisibility(View.GONE);
                        llBtnItem.setVisibility(View.GONE);
                        cantPeso.setFocusable(false);
                        cantPeso.setEnabled(false);
                    } else if( modo.equals(EnumVariables.MODO_CREACION.getValor())){
                        llBtnAumentarCantidad.setVisibility(View.VISIBLE);
                        llBtnReducirCantidad.setVisibility(View.VISIBLE);
                        llBtnItem.setVisibility(View.VISIBLE);
                        cantPeso.setFocusable(true);
                        cantPeso.setEnabled(true);
                        cantPeso.requestFocusFromTouch();
                    }
                }

                Double precioUni = 0.0;

                precioUni = item.getPrecio_unitario();

                setEdtPrecioProducto(precioUni);

                total.setText("$" + Utilidades.puntoMil(item.getPrecio_total()));

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
                    item.setCant_comprado(0.0);
                } else if(Double.parseDouble(cantPeso.getText().toString().trim()) > 0){
                    Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                    cant-=0.001;
                    item.setCant_comprado(cant);
                    cantPeso.setText(""+ Utilidades.restringirDecimales(item.getCant_comprado()));
                }
            }
            calcularPrecio();
        }

        @OnClick(R.id.llBtnAumentarCantidad)
        void onClickLlBtnAumentarCantidad(){
            if(!cantPeso.getText().toString().trim().equals("")){
                Double cant = Double.parseDouble(cantPeso.getText().toString().trim());
                cant+=0.001;
                item.setCant_comprado(cant);
                cantPeso.setText(""+ Utilidades.restringirDecimales(item.getCant_comprado()));
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
            if(inversionRegistroHolderView != null){
                inversionRegistroHolderView.eliminarProductoItem(this.item.getProducto());
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
         * @Fecha 18/10/2022
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
                precio = cant * this.item.getPrecio_unitario();
                item.setPrecio_total(precio);
                item.setCant_comprado(cant);
            } catch (Exception e){
                e.printStackTrace();
            }

            total.setText("$ " + Utilidades.puntoMil(precio));

            if(inversionRegistroHolderView != null){
                inversionRegistroHolderView.calcularPrecioTotal();
            }

        }

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite settear los campos
         * @Fecha 18/10/2022
         */
        private void setEdtPrecioProducto(Double precioUni) {
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
        }

        /**
         * -------------- METODOS INTERFACE IItemInversionHolderView --------------------------------
         */

        /**
         * @Autor RagooS
         * @Descripccion Metodo permite cambiar el precio del producto
         * @Fecha 18/10/2022
         */
        @Override
        public void cambiarPrecio(String precioNew) {
            Double precio = Double.parseDouble(precioNew.contains(",") ? precioNew.replace(",",".") : precioNew);
            this.item.setPrecio_unitario(precio);
            setEdtPrecioProducto(precio);
            calcularPrecio();
            inversionRegistroHolderView.calcularPrecioTotal();
        }
    }
}
