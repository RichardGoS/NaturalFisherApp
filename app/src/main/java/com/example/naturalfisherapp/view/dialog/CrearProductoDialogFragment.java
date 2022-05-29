package com.example.naturalfisherapp.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.presenter.activities.ProductoPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.fragment.PromocionDetalleFragment;
import com.example.naturalfisherapp.view.interfaces.IProductoBusquedaFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.ICrearProductoDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleProductoDialogFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 12/01/2022
 */

public class CrearProductoDialogFragment extends DialogFragment implements ICrearProductoDialogFragmentView {

    private Dialog dialog;
    private Activity activity;
    private String titulo;
    private boolean unidadSeleccionada = false;
    private String unidad;
    private ProgressDialog progress;
    private IProductoPresenter iProductoPresenter;
    private IProductoBusquedaFragmentView iProductoBusquedaFragmentView;
    private IDetalleProductoDialogFragment iDetalleProductoDialogFragment;
    private Producto producto;

    /**
     * Fase III T2  PROMOCIONES
     */
    private boolean isPromocion = false;
    private Promocion promocion;

    String[] unidades = new String[] { "Seleccionar", "Kg", "Lb", "Unidad", "Otro" };

    @BindView(R.id.spUnidadProducto)
    Spinner spUnidadProducto;

    @BindView(R.id.btnAceptar)
    LinearLayout btnAceptar;

    @BindView(R.id.btnCancelar)
    LinearLayout btnCancelar;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.edtNombreProducto)
    EditText edtNombreProducto;

    @BindView(R.id.edtPrecioProducto)
    EditText edtPrecioProducto;

    @BindView(R.id.edtVariacionProducto)
    EditText edtVariacionProducto;

    @BindView(R.id.edtDescripcionProducto)
    EditText edtDescripcionProducto;

    @BindView(R.id.chbxRealizaInventario)
    CheckBox chbxRealizaInventario;

    @BindView(R.id.llUnidad)
    LinearLayout llUnidad;

    @BindView(R.id.llVariacion)
    LinearLayout llVariacion;

    @BindView(R.id.llInventario)
    LinearLayout llInventario;


    public static CrearProductoDialogFragment newInstance(Activity activity, String titulo, IProductoBusquedaFragmentView iProductoBusquedaFragmentView){
        CrearProductoDialogFragment crearProductoDialogFragment = new CrearProductoDialogFragment();
        crearProductoDialogFragment.activity = activity;
        crearProductoDialogFragment.titulo = titulo;
        crearProductoDialogFragment.iProductoBusquedaFragmentView = iProductoBusquedaFragmentView;
        return crearProductoDialogFragment;
    }

    public static CrearProductoDialogFragment newInstance(Activity activity, Producto producto, String titulo, IDetalleProductoDialogFragment iDetalleProductoDialogFragment){
        CrearProductoDialogFragment crearProductoDialogFragment = new CrearProductoDialogFragment();
        crearProductoDialogFragment.activity = activity;
        crearProductoDialogFragment.producto = producto;
        crearProductoDialogFragment.titulo = titulo;
        crearProductoDialogFragment.iDetalleProductoDialogFragment = iDetalleProductoDialogFragment;
        return crearProductoDialogFragment;
    }

    public static CrearProductoDialogFragment newInstance(boolean isPromocion, Promocion promocion, Activity activity, String titulo){
        CrearProductoDialogFragment crearProductoDialogFragment = new CrearProductoDialogFragment();
        crearProductoDialogFragment.activity = activity;
        crearProductoDialogFragment.titulo = titulo;
        crearProductoDialogFragment.isPromocion = isPromocion;
        crearProductoDialogFragment.promocion = promocion;
        return crearProductoDialogFragment;
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
        View agregarClienteDialog = inflater.inflate(R.layout.dialog_fragment_crear_producto, null);

        ButterKnife.bind(this, agregarClienteDialog);

        if(isPromocion){
            llUnidad.setVisibility(View.GONE);
            llVariacion.setVisibility(View.GONE);
            llInventario.setVisibility(View.GONE);
        } else {
            if(iProductoBusquedaFragmentView != null){
                iProductoPresenter = new ProductoPresenter(getContext(), iProductoBusquedaFragmentView, this);
            } else if(iDetalleProductoDialogFragment != null){
                iProductoPresenter = new ProductoPresenter(getContext(),  this, iDetalleProductoDialogFragment);
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, unidades);

            spUnidadProducto.setAdapter(adapter);

            if(producto != null){
                llenarCampos(producto);
            } else {
                chbxRealizaInventario.setChecked(true);
            }
        }

        txtTitulo.setText(titulo);

        builder.setView(agregarClienteDialog);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }


    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnAceptar)
    void onClikBtnAceptar(){
        if(validarCampos()){
            System.out.println("Valido");
            setDatos();

            if(isPromocion){
                if(promocion != null){
                    //iProductoPresenter.guardarProducto(producto);
                    goToPromocionDetalle();
                    dismissDialog();
                }
            } else {
                if(producto != null){
                    iProductoPresenter.guardarProducto(producto);
                }
            }

        }
    }

    private void goToPromocionDetalle() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.producto_container, PromocionDetalleFragment.newInstance(activity, promocion, EnumVariables.MODO_CREACION.getValor()));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.btnCancelar)
    void onClickBtnCancelar(){
        dismissDialog();
    }

    @OnItemSelected(R.id.spUnidadProducto)
    void onItemSeletedSpUnidadProducto(int position){
        if(position > 0){
            unidadSeleccionada = true;
            unidad = unidades[position];
        } else {
            unidadSeleccionada = false;
        }
        System.out.println("Item - " + position + " - " + unidades[position]);
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar si los campos editText se en cuentran con informacion
     * @Fecha 16/01/2022
     * @return
     */
    private boolean validarCampos() {

        boolean valido = false;

        if(isPromocion){
            if(!edtNombreProducto.getText().toString().equals("")){
                if(!edtPrecioProducto.getText().toString().equals("")){
                    if(!edtDescripcionProducto.getText().toString().equals("")){
                        valido = true;
                    }
                }
            }
        } else {
            if(!edtNombreProducto.getText().toString().equals("")){
                if(!edtPrecioProducto.getText().toString().equals("")){
                    if(unidadSeleccionada && !unidad.equals("")){
                        if(!edtVariacionProducto.getText().toString().equals("")){
                            if(!edtDescripcionProducto.getText().toString().equals("")){
                                valido = true;
                            }
                        }
                    }
                }
            }
        }

        return valido;
    }

    private void setDatos() {

        if(isPromocion){
            if(promocion == null){
                promocion = new Promocion();
            }

            promocion.setNombre(edtNombreProducto.getText().toString());
            promocion.setTotal(Double.parseDouble(edtPrecioProducto.getText().toString().contains(",") ? edtPrecioProducto.getText().toString().replace(",",".") : edtPrecioProducto.getText().toString()));
            promocion.setDescripccion(edtDescripcionProducto.getText().toString().trim());

        } else {

            if(producto == null){
                producto = new Producto();
            }

            if(chbxRealizaInventario.isChecked()){
                producto.setRealiza_inventario("S");
            } else {
                producto.setRealiza_inventario("N");
            }
            producto.setNombre(edtNombreProducto.getText().toString());
            producto.setUnidad(unidad);
            producto.setPrecio(Double.parseDouble(edtPrecioProducto.getText().toString().contains(",") ? edtPrecioProducto.getText().toString().replace(",",".") : edtPrecioProducto.getText().toString()));
            producto.setDescripcion_unidad(edtDescripcionProducto.getText().toString().trim());
            producto.setVariacion(Double.parseDouble(edtVariacionProducto.getText().toString().contains(",") ? edtVariacionProducto.getText().toString().replace(",",".") : edtVariacionProducto.getText().toString()));

        }
    }

    private void llenarCampos(Producto product) {
        edtNombreProducto.setText(producto.getNombre());
        edtPrecioProducto.setText("" + producto.getPrecio());
        edtVariacionProducto.setText("" + producto.getVariacion());
        edtDescripcionProducto.setText(producto.getDescripcion_unidad() != null ? producto.getDescripcion_unidad():"");

        for(int i=1; i < unidades.length; i++){
            if(unidades[i].equals(producto.getUnidad())){
                spUnidadProducto.setSelection(i);
                unidadSeleccionada = true;
                break;
            } else {
                unidadSeleccionada = false;
            }
        }

        if(producto.getRealiza_inventario().equals("S")){
            chbxRealizaInventario.setChecked(true);
        } else {
            chbxRealizaInventario.setChecked(false);
        }
    }

    /**
     * -------------- METODOS INTERFACE ICrearProductoDialogFragmentView --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar barra de carga
     * @Fecha 12/01/2022
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar barra de carga
     * @Fecha 12/01/2022
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar dialogo
     * @Fecha 12/01/2022
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }
}
