package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.interfaces.dialog.ISelectOptionDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fase 4 Tarea 2
 * Fecha: 30/07/2022
 */


public class ItemSelectOptionAdapter extends RecyclerView.Adapter<ItemSelectOptionAdapter.ItemSelectOptionAdapterHolder>{

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<String> listOpciones;
    private ISelectOptionDialogFragment iSelectOptionDialogFragment;
    private int posiccionSeleccionada;

    public ItemSelectOptionAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<String> listOpciones, int posiccionSeleccionada, ISelectOptionDialogFragment iSelectOptionDialogFragment) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.listOpciones = listOpciones;
        this.iSelectOptionDialogFragment = iSelectOptionDialogFragment;
        this.posiccionSeleccionada = posiccionSeleccionada;
    }

    @NonNull
    @Override
    public ItemSelectOptionAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_select_opcion, parent, false);
        ItemSelectOptionAdapter.ItemSelectOptionAdapterHolder adapter = new ItemSelectOptionAdapterHolder(view, posiccionSeleccionada);
        context = parent.getContext();
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSelectOptionAdapterHolder holder, int position) {
        holder.bind(listOpciones.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listOpciones.size();
    }

    public class ItemSelectOptionAdapterHolder extends RecyclerView.ViewHolder{

        private String nombreOpccion;
        private int posiccionSeleccionada;
        private int posiccionOpcion;

        @BindView(R.id.llItemOpccion)
        LinearLayout llItemOpccion;

        @BindView(R.id.txtNombreOpccion)
        TextView txtNombreOpccion;

        public ItemSelectOptionAdapterHolder(@NonNull View itemView, int posiccionSeleccionada) {
            super(itemView);
            this.posiccionSeleccionada = posiccionSeleccionada;

            ButterKnife.bind(this, itemView);
        }

        void bind(String nombreOpccion, int posiccionOpcion){
            this.nombreOpccion = nombreOpccion;
            this.posiccionOpcion = posiccionOpcion;

            txtNombreOpccion.setText(nombreOpccion);

            if(posiccionOpcion == posiccionSeleccionada){
                txtNombreOpccion.setTextColor(ContextCompat.getColor(context,R.color.white));
                llItemOpccion.setBackgroundResource(R.drawable.shape_opcion_seleccionada);
            } else {
                txtNombreOpccion.setTextColor(ContextCompat.getColor(context,R.color.azul_oscuro));
                llItemOpccion.setBackgroundResource(R.drawable.shape_opcion_no_seleccionada);
            }
        }


        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */
        @OnClick(R.id.llItemOpccion)
        void onClickLlItemOpccion(){
            if(iSelectOptionDialogFragment != null){
                posiccionSeleccionada = posiccionOpcion;
                iSelectOptionDialogFragment.cambiarOpcion(nombreOpccion, posiccionOpcion);
            }
        }
    }
}
