package com.example.naturalfisherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 17/07/2021
 */

public class ItemAutocompleteTextViewAdapter extends RecyclerView.Adapter<ItemAutocompleteTextViewAdapter.ItemAutocompleteTextViewHolder>{

    private List<Cliente> clientes;
    private Context context;
    private Activity activity;

    public ItemAutocompleteTextViewAdapter(List<Cliente> clientes, Context context, Activity activity) {
        this.clientes = clientes;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public ItemAutocompleteTextViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_autocomplete_text_azul_bajo, parent, false);
        ItemAutocompleteTextViewAdapter.ItemAutocompleteTextViewHolder itemAutocompleteTextViewHolder = new ItemAutocompleteTextViewHolder(view);
        context = parent.getContext();
        return itemAutocompleteTextViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemAutocompleteTextViewHolder holder, int position) {
        holder.bind(clientes.get(position));
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }


    class ItemAutocompleteTextViewHolder extends RecyclerView.ViewHolder{

        private Cliente cliente;

        @BindView(R.id.nombreCliente)
        TextView nombreCliente;

        public ItemAutocompleteTextViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        void bind(Cliente cliente){

            this.cliente = cliente;

            if(this.cliente != null){
                nombreCliente.setText(this.cliente.getNombre());
            }

        }
    }

}