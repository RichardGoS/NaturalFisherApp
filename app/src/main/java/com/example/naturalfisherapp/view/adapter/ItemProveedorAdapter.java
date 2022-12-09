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
import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.view.dialog.DetalleProveedorDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IProveedorBusquedaFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 29/09/2022
 */
public class ItemProveedorAdapter extends RecyclerView.Adapter<ItemProveedorAdapter.ItemProveedorAdapterHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;
    private List<Proveedor> proveedores;
    private IProveedorBusquedaFragmentView proveedorBusquedaFragmentView;

    public ItemProveedorAdapter(Context context, FragmentManager fragmentManager, Activity activity, List<Proveedor> proveedores, IProveedorBusquedaFragmentView proveedorBusquedaFragmentView) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.proveedores = proveedores;
        this.proveedorBusquedaFragmentView = proveedorBusquedaFragmentView;
    }

    @NonNull
    @Override
    public ItemProveedorAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_proveedor_busqueda, parent, false);
        ItemProveedorAdapter.ItemProveedorAdapterHolder itemProveedorAdapterHolder = new ItemProveedorAdapterHolder(view, proveedorBusquedaFragmentView);
        context = parent.getContext();
        return itemProveedorAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProveedorAdapterHolder holder, int position) {
        holder.bind(proveedores.get(position));
    }

    @Override
    public int getItemCount() {
        return proveedores.size();
    }

    class ItemProveedorAdapterHolder extends RecyclerView.ViewHolder{

        private Proveedor proveedor;
        private IProveedorBusquedaFragmentView proveedorBusquedaFragmentView;

        @BindView(R.id.nitProveedor)
        TextView nitProveedor;

        @BindView(R.id.nombreProveedor)
        TextView nombreProveedor;

        @BindView(R.id.ciudadProveedor)
        TextView ciudadProveedor;

        @BindView(R.id.direccionProveedor)
        TextView direccionProveedor;

        @BindView(R.id.telefonoProveedor)
        TextView telefonoProveedor;

        @BindView(R.id.descripcionProveedor)
        TextView descripcionProveedor;

        @BindView(R.id.llProveedor)
        LinearLayout llProveedor;

        public ItemProveedorAdapterHolder(@NonNull View itemView, IProveedorBusquedaFragmentView proveedorBusquedaFragmentView) {
            super(itemView);
            this.proveedorBusquedaFragmentView = proveedorBusquedaFragmentView;
            ButterKnife.bind(this, itemView);
        }

        public void bind (Proveedor proveedor){
            this.proveedor = proveedor;

            if(proveedor != null){
                nitProveedor.setText(this.proveedor.getNit());
                nombreProveedor.setText(this.proveedor.getNombre());
                ciudadProveedor.setText(this.proveedor.getCiudad());
                direccionProveedor.setText(this.proveedor.getDireccion());
                String telefono = this.proveedor.getTelefono();

                if(this.proveedor.getTelefono_respaldo() != null && !this.proveedor.getTelefono_respaldo().equals("")){
                    telefono += " - " + this.proveedor.getTelefono_respaldo();
                }
                telefonoProveedor.setText(telefono);
                descripcionProveedor.setText(this.proveedor.getDescripcion());
            }
        }

        /**
         * --------------================ METODOS =================--------------------------------
         */

        /**
         * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
         */

        @OnClick(R.id.llProveedor)
        void onClickProveedor(){
            System.out.println("Detalle Proveedor");
            DetalleProveedorDialogFragment detalleProveedorDialogFragment = DetalleProveedorDialogFragment.newInstance(activity, fragmentManager, proveedor, proveedorBusquedaFragmentView);
            detalleProveedorDialogFragment.show(fragmentManager, "DetalleProveedor");
            Fragment fragment = fragmentManager.findFragmentByTag("DetalleProveedor");
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }
    }
}
