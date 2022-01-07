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
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.view.dialog.AgregarClienteDialogFragment;
import com.example.naturalfisherapp.view.dialog.DetalleClienteDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IClienteBusquedaFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 29/112/2021
 */

public class ItemClienteAdapter extends RecyclerView.Adapter<ItemClienteAdapter.ItemClienteHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<Cliente> clientes;
    private IClienteBusquedaFragmentView iClienteBusquedaFragmentView;

    public ItemClienteAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<Cliente> clientes, IClienteBusquedaFragmentView iClienteBusquedaFragmentView) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.clientes = clientes;
        this.iClienteBusquedaFragmentView = iClienteBusquedaFragmentView;
    }

    @NonNull
    @NotNull
    @Override
    public ItemClienteHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_cliente_busqueda, parent, false);
        ItemClienteAdapter.ItemClienteHolder itemClienteHolder = new ItemClienteHolder(view);
        context = parent.getContext();
        return itemClienteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemClienteHolder holder, int position) {
        holder.bind(clientes.get(position));
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    class ItemClienteHolder extends RecyclerView.ViewHolder {

        private Cliente cliente;

        @BindView(R.id.nombreCliente)
        TextView nombreCliente;

        @BindView(R.id.direccionCliente)
        TextView direccionCliente;

        @BindView(R.id.telefonoCliente)
        TextView telefonoCliente;

        @BindView(R.id.llCliente)
        LinearLayout llCliente;

        public ItemClienteHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Cliente cliente) {
            this.cliente = cliente;

            if(this.cliente != null){
                nombreCliente.setText(this.cliente.getNombre());
                direccionCliente.setText(this.cliente.getDireccion());
                telefonoCliente.setText(this.cliente.getTelefono());
            }

        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llCliente)
        void onClickCliente(){
            System.out.println("Detalle Cliente");

            DetalleClienteDialogFragment detalleClienteDialogFragment = DetalleClienteDialogFragment.newInstance(activity, cliente, iClienteBusquedaFragmentView);
            detalleClienteDialogFragment.show(fragmentManager, "DetalleCliente");
            Fragment fragment = fragmentManager.findFragmentByTag("DetalleCliente");
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }
    }
}
