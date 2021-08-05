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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaClientesVenta;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.fragment.VentaBusquedaFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public class ClienteItemVentaBusqueda extends RecyclerView.Adapter<ClienteItemVentaBusqueda.ClienteItemVentaBusquedadHolder>{

    List<BusquedaClientesVenta> clientesVenta;
    private Context context;
    private android.app.FragmentManager fragmentManager;
    private Activity activity;

    public ClienteItemVentaBusqueda(List<BusquedaClientesVenta> clientesVenta, Context context, android.app.FragmentManager fragmentManager, Activity activity) {
        this.clientesVenta = clientesVenta;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public ClienteItemVentaBusquedadHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cliente_item_venta_busqueda, parent, false);
        ClienteItemVentaBusqueda.ClienteItemVentaBusquedadHolder clienteItemVentaBusquedadHolder = new ClienteItemVentaBusquedadHolder(view);
        context = parent.getContext();
        return clienteItemVentaBusquedadHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClienteItemVentaBusquedadHolder holder, int position) {
        holder.bind(clientesVenta.get(position));
    }

    @Override
    public int getItemCount() {
        return clientesVenta.size();
    }

    class ClienteItemVentaBusquedadHolder extends RecyclerView.ViewHolder{

        private BusquedaClientesVenta clientesVenta;

        @BindView(R.id.llItemClienteVenta)
        LinearLayout llItemClienteVenta;

        @BindView(R.id.clienteItemVenta)
        TextView clienteItemVenta;

        @BindView(R.id.valorVentaCliente)
        TextView valorVentaCliente;

        public ClienteItemVentaBusquedadHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final BusquedaClientesVenta clientesVenta){
            this.clientesVenta = clientesVenta;

            if(this.clientesVenta != null){
                clienteItemVenta.setText(this.clientesVenta.getCliente().getNombre());
                valorVentaCliente.setText(Utilidades.puntoMil(this.clientesVenta.getPrecioVenta()));

            }
        }

        @OnClick(R.id.llItemClienteVenta)
        public void goToItemClienteVenta(){
            System.out.println("Presiona Cliente...");

        }
    }
}
