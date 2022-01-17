package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.IProductoBusquedaFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.dialog.ICrearProductoDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleProductoDialogFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public class ProductoPresenter implements IProductoPresenter {

    private InterfaceApiService service;
    private Context context;
    private IProductoBusquedaFragmentView iProductoBusquedaFragmentView;
    private IAgregarProductoDialogFragment agregarProductoDialogFragment;
    private ICrearProductoDialogFragmentView iCrearProductoDialogFragmentView;
    private IDetalleProductoDialogFragment iDetalleProductoDialogFragment;

    public ProductoPresenter() {
    }

    public ProductoPresenter(Context context, IProductoBusquedaFragmentView iProductoBusquedaFragmentView) {
        this.context = context;
        this.iProductoBusquedaFragmentView = iProductoBusquedaFragmentView;
    }

    public ProductoPresenter(Context context, IAgregarProductoDialogFragment agregarProductoDialogFragment) {
        this.context = context;
        this.agregarProductoDialogFragment = agregarProductoDialogFragment;
    }

    public ProductoPresenter(Context context, IProductoBusquedaFragmentView iProductoBusquedaFragmentView, ICrearProductoDialogFragmentView iCrearProductoDialogFragmentView){
        this.context = context;
        this.iProductoBusquedaFragmentView = iProductoBusquedaFragmentView;
        this.iCrearProductoDialogFragmentView = iCrearProductoDialogFragmentView;
    }

    public ProductoPresenter(Context context, IProductoBusquedaFragmentView iProductoBusquedaFragmentView, IDetalleProductoDialogFragment iDetalleProductoDialogFragment) {
        this.context = context;
        this.iProductoBusquedaFragmentView = iProductoBusquedaFragmentView;
        this.iDetalleProductoDialogFragment = iDetalleProductoDialogFragment;
    }

    public ProductoPresenter(Context context, ICrearProductoDialogFragmentView iCrearProductoDialogFragmentView, IDetalleProductoDialogFragment iDetalleProductoDialogFragment) {
        this.context = context;
        this.iCrearProductoDialogFragmentView = iCrearProductoDialogFragmentView;
        this.iDetalleProductoDialogFragment = iDetalleProductoDialogFragment;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IProductoPresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite consultar los productos
     * @Fecha 09/07/21
     */
    @Override
    public void consultarProductos() {

        try {
            if(iProductoBusquedaFragmentView != null){
                iProductoBusquedaFragmentView.showProgress();
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Producto>> call = service.getProductos();

            call.enqueue(new Callback<List<Producto>>() {
                @Override
                public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                    if(response != null && response.body() != null){
                        validarProductos(response.body());
                    } else {
                        if(iProductoBusquedaFragmentView != null){
                            iProductoBusquedaFragmentView.hideProgress();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Producto>> call, Throwable t) {
                    if(iProductoBusquedaFragmentView != null){
                        iProductoBusquedaFragmentView.hideProgress();
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            if(iProductoBusquedaFragmentView != null){
                iProductoBusquedaFragmentView.hideProgress();
            }
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite consultar los productos
     * @Fecha 13/01/2022
     */
    @Override
    public void guardarProducto(Producto producto) {
        try {
            if(iCrearProductoDialogFragmentView != null){
                iCrearProductoDialogFragmentView.showProgress("Almacenando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Producto> call = service.saveProducto(producto);

            call.enqueue(new Callback<Producto>() {
                @Override
                public void onResponse(Call<Producto> call, Response<Producto> response) {
                    validarProductoAlmacenado(producto, response.body());
                }

                @Override
                public void onFailure(Call<Producto> call, Throwable t) {
                    validarProductoAlmacenado(producto, null);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            if(iCrearProductoDialogFragmentView != null){
                iCrearProductoDialogFragmentView.hideProgress();
            }
        }
    }

    @Override
    public void eliminarProducto(Producto producto) {
        try {
            if(iDetalleProductoDialogFragment != null){
                iDetalleProductoDialogFragment.showProgress("Eliminando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Boolean> call = service.eliminarProducto(producto);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    //validarProductoAlmacenado(producto, response.body());
                    validarProductoEliminado(response.body());
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    //validarProductoAlmacenado(producto, null);
                    validarProductoEliminado(false);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            validarProductoEliminado(false);
            if(iDetalleProductoDialogFragment != null){
                iDetalleProductoDialogFragment.hideProgress();
            }
        }
    }


    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar informacion devuelta por la consulta
     * @Fecha 09/07/21
     * @Param productos devueltos en la respuesta
     */
    private void validarProductos(List<Producto> productos) {

        if(productos != null && !productos.isEmpty()){

            InformacionSession.getInstance().setProductosConsultados(productos);

            if(iProductoBusquedaFragmentView != null){
                iProductoBusquedaFragmentView.cargarAdapter(productos);
                iProductoBusquedaFragmentView.hideProgress();
            } else if(agregarProductoDialogFragment != null){
                agregarProductoDialogFragment.cargarAdapter(productos);
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar si se almaceno el producto con exito
     * @Fecha 13/01/2022
     * @param productoEnviado tipo de dato Producto enviado a almacenar
     * @param productoRetorno tipo de dato Producto retorno
     */
    private void validarProductoAlmacenado(Producto productoEnviado, Producto productoRetorno) {

        if(productoEnviado != null){
            if(productoRetorno != null){
                if(productoRetorno.getCodigo() != null && productoRetorno.getNombre().equals(productoEnviado.getNombre())){
                    if(iCrearProductoDialogFragmentView != null){
                        iCrearProductoDialogFragmentView.hideProgress();
                        iCrearProductoDialogFragmentView.dismissDialog();
                        if(iProductoBusquedaFragmentView != null){
                            consultarProductos();
                        } else if(iDetalleProductoDialogFragment != null){
                            if(productoRetorno.getCodigo() != null && productoRetorno.getCodigo().equals(productoEnviado.getCodigo())){
                                iDetalleProductoDialogFragment.mostrarMensaje("EXITO_ACTUALIZACION");
                            }

                        }
                    }
                }
            } else {
                if(iCrearProductoDialogFragmentView != null){
                    iCrearProductoDialogFragmentView.hideProgress();
                }
            }
        } else {
            if(iCrearProductoDialogFragmentView != null){
                iCrearProductoDialogFragmentView.hideProgress();
            }
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar si se elimino el producto con exito
     * @Fecha 16/01/2022
     * @param body
     */
    private void validarProductoEliminado(Boolean body) {
        if(body){
            if(iDetalleProductoDialogFragment != null){
                iDetalleProductoDialogFragment.hideProgress();
                iDetalleProductoDialogFragment.mostrarMensaje("EXITO_ELIMINACION");
            }
        }
    }
}
