package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.IProductoBusquedaFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarProductoDialogFragment;

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
}
