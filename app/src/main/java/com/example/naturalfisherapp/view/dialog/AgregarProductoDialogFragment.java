package com.example.naturalfisherapp.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.presenter.activities.ProductoPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.adapter.ItemAgregarProductoVentaAdapter;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarProductoDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 17/07/2021
 */

public class AgregarProductoDialogFragment extends DialogFragment implements IAgregarProductoDialogFragment {

    private Dialog dialog;
    private FragmentManager fragmentManager;
    private IProductoPresenter productoPresenter;
    private ProgressDialog progress;
    private LinearLayoutManager linearLayoutManager;
    private IVentaRegistroHolderView ventaRegistroHolderView;

    @BindView(R.id.edtBuscador)
    EditText edtBuscador;

    @BindView(R.id.efRvProductos)
    RecyclerView efRvProductos;

    public static AgregarProductoDialogFragment newInstance(FragmentManager fragmentManager, IVentaRegistroHolderView ventaRegistroHolderView){
        AgregarProductoDialogFragment agregarProductoDialogFragment = new AgregarProductoDialogFragment();
        agregarProductoDialogFragment.fragmentManager = fragmentManager;
        agregarProductoDialogFragment.ventaRegistroHolderView = ventaRegistroHolderView;

        return agregarProductoDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        //setHasOptionsMenu(true);
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View agregarProductoDialog = inflater.inflate(R.layout.dialog_fragment_agregar_producto_venta, null);

        ButterKnife.bind(this, agregarProductoDialog);

        builder.setView(agregarProductoDialog);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        productoPresenter = new ProductoPresenter(getContext(), this);

        progress = new ProgressDialog(getContext());

        if(InformacionSession.getInstance().getProductosConsultados() != null && !InformacionSession.getInstance().getProductosConsultados().isEmpty()){
            cargarAdapter(InformacionSession.getInstance().getProductosConsultados());
        } else {
            productoPresenter.consultarProductos();
        }

        return dialog;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IClientePresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar barra de carga
     * @Fecha 18/07/21
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar barra de carga
     * @Fecha 18/07/21
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar dialogo
     * @Fecha 18/07/21
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite cargar el adapter de los productos
     * @Fecha 18/07/21
     */
    @Override
    public void cargarAdapter(List<Producto> productos) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ItemAgregarProductoVentaAdapter itemAgregarProductoVentaAdapter = new ItemAgregarProductoVentaAdapter(getContext(), fragmentManager, getActivity(), productos, ventaRegistroHolderView, this);
        efRvProductos.setAdapter(itemAgregarProductoVentaAdapter);
        efRvProductos.setLayoutManager(linearLayoutManager);
    }
}
