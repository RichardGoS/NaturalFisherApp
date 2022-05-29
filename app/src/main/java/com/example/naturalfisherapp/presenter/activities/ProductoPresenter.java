package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;
import android.util.Log;

import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;
import com.example.naturalfisherapp.data.models.interpretes.ProductosTransporte;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.IProductoBusquedaFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.dialog.ICrearProductoDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IPromocionDetalleFragmentView;

import java.util.ArrayList;
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
    private IPromocionDetalleFragmentView iPromocionDetalleFragmentView;

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

    public ProductoPresenter(Context context, IPromocionDetalleFragmentView iPromocionDetalleFragmentView) {
        this.context = context;
        this.iPromocionDetalleFragmentView = iPromocionDetalleFragmentView;
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

            Call<ProductosTransporte> call = service.getProductos();

            call.enqueue(new Callback<ProductosTransporte>() {
                @Override
                public void onResponse(Call<ProductosTransporte> call, Response<ProductosTransporte> response) {
                    validarProductos(response.body());
                }

                @Override
                public void onFailure(Call<ProductosTransporte> call, Throwable t) {
                    validarProductos(null);
                }
            });
            /*






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
            });*/

        } catch (Exception e){
            e.printStackTrace();
            validarProductos(null);
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite consultar los productos activos para las ventas
     * @Fecha 22/02/2022
     */
    @Override
    public void consultarProductosActivosVenta() {
        try {

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<ProductosTransporte> call = service.getProductosActivosVentas();

            call.enqueue(new Callback<ProductosTransporte>() {
                @Override
                public void onResponse(Call<ProductosTransporte> call, Response<ProductosTransporte> response) {
                    validarProductos(response.body());
                }

                @Override
                public void onFailure(Call<ProductosTransporte> call, Throwable t) {
                    validarProductos(null);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            validarProductos(null);
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite consultar los productos activos para la promocion
     * @Fecha 10/05/2022
     */
    @Override
    public void consultarProductosPromocion() {

        try {
            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Producto>> call = service.getProductosPromoVentas();

            call.enqueue(new Callback<List<Producto>>() {
                @Override
                public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                    validarProductosPromo(response.body());
                }

                @Override
                public void onFailure(Call<List<Producto>> call, Throwable t) {
                    validarProductosPromo(null);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("consultarProductos", "Error al consultar los productos promocion");
            validarProductosPromo(null);
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

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite eliminar el producto
     * @Fecha 13/01/2022
     */
    @Override
    public void eliminarProducto(Object producto) {
        try {
            if(iDetalleProductoDialogFragment != null){
                iDetalleProductoDialogFragment.showProgress("Eliminando...");
            }

            if(producto.getClass() == Producto.class){
                service = ClientApiService.getClient().create(InterfaceApiService.class);

                Call<Boolean> call = service.eliminarProducto((Producto) producto);

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
            }
        } catch (Exception e){
            e.printStackTrace();
            validarProductoEliminado(false);
            if(iDetalleProductoDialogFragment != null){
                iDetalleProductoDialogFragment.hideProgress();
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite almacenar la promocion
     * @Fecha 05/05/2022
     */
    @Override
    public void guardarPromocion(Promocion promocion) {

        try {

            if(iPromocionDetalleFragmentView != null){
                iPromocionDetalleFragmentView.showProgress("Almacenando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Promocion> call = service.savePromocion(promocion);

            call.enqueue(new Callback<Promocion>() {
                @Override
                public void onResponse(Call<Promocion> call, Response<Promocion> response) {
                    validarProductoPromocion(response.body());
                }

                @Override
                public void onFailure(Call<Promocion> call, Throwable t) {
                    validarProductoPromocion(null);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Creacion Promocion","Se produjo un error al almacenar la promocion " + e.getMessage());
            validarProductoPromocion(null);
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite eliminar una promocion
     * @Fecha 14/05/2022
     */
    @Override
    public void eliminarPromocion(Promocion promocion) {

        try {

            if(iPromocionDetalleFragmentView != null){
                iPromocionDetalleFragmentView.showProgress("Eliminando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Boolean> call = service.eliminarPromocion(promocion);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    validarPromocionEliminada(response.body());
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    validarPromocionEliminada(false);
                }
            });

        } catch (Exception e) {
            Log.e("EliminacionPromocion","Se produjo un error al eliminar la promocion " + e.getMessage());
            e.printStackTrace();
            validarPromocionEliminada(false);
        }

    }


    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar que la promocion sea eliminada con exito
     * @Fecha 10/05/22
     * @Param body true eliminado con exito de lo contrario false
     */
    private void validarPromocionEliminada(Boolean body) {

        if(body != null && body){
            if(iPromocionDetalleFragmentView != null){
                iPromocionDetalleFragmentView.hideProgress();
                iPromocionDetalleFragmentView.goToProductoActivity();
            }

        } else {
            Log.e("ValidarPromoEliminada", "Error al eliminar la promo ");
            if(iPromocionDetalleFragmentView != null){
                iPromocionDetalleFragmentView.hideProgress();
            }
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar informacion devuelta por la consulta
     * @Fecha 10/05/22
     * @Param productos devueltos en la respuesta
     */
    private void validarProductosPromo(List<Producto> productos) {

        if(productos != null && !productos.isEmpty()){
            crearListaProductosGeneralActivosPromo(productos);

            if(agregarProductoDialogFragment != null){
                agregarProductoDialogFragment.cargarAdapter(InformacionSession.getInstance().getProductosActivosPromo());
            }

        } else {
            System.out.println("ERROR: respuesta Null");
        }

    }

    private void crearListaProductosGeneralActivosPromo(List<Producto> productos) {
        List<GeneralProductos> generalProductos = new ArrayList<>();

        for(Producto producto: productos){
            GeneralProductos gProducto = new GeneralProductos();
            gProducto.setProducto(producto);
            generalProductos.add(gProducto);
        }

        InformacionSession.getInstance().setProductosActivosPromo(generalProductos);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar informacion devuelta por la consulta
     * @Fecha 09/07/21
     * @Param productos devueltos en la respuesta
     */
    private void validarProductos(ProductosTransporte productos) {

        List<Object> listaObjetos = new ArrayList<Object>();

        if(productos != null){
            if(productos.getProductos() != null && productos.getPromocion() != null){
                if(!productos.getProductos().isEmpty() && !productos.getPromocion().isEmpty()){
                    InformacionSession.getInstance().setNumProductos(productos.getProductos().size());
                    InformacionSession.getInstance().setNumPromocion(productos.getPromocion().size());
                    listaObjetos = ordenarProductos(productos);
                } else if(!productos.getProductos().isEmpty()){
                    InformacionSession.getInstance().setNumProductos(productos.getProductos().size());
                    InformacionSession.getInstance().setNumPromocion(0);
                    listaObjetos = volcarProductos(listaObjetos, productos.getProductos());
                } else if(!productos.getPromocion().isEmpty()){
                    InformacionSession.getInstance().setNumPromocion(productos.getPromocion().size());
                    InformacionSession.getInstance().setNumProductos(0);
                    listaObjetos = volcarPromociones(listaObjetos, productos.getPromocion());
                }

                if (listaObjetos != null && !listaObjetos.isEmpty()){
                    if(iProductoBusquedaFragmentView != null){
                        InformacionSession.getInstance().setProductosConsultados(crearListaProductosGeneral(listaObjetos));
                        iProductoBusquedaFragmentView.cargarAdapter(InformacionSession.getInstance().getProductosConsultados(), InformacionSession.getInstance().getNumProductos(), InformacionSession.getInstance().getNumPromocion());
                    } else if(agregarProductoDialogFragment != null){
                        InformacionSession.getInstance().setProductosActivosVenta(crearListaProductosGeneral(listaObjetos));
                        agregarProductoDialogFragment.cargarAdapter(InformacionSession.getInstance().getProductosActivosVenta());
                    }
                    listaObjetos.clear();

                } else {
                    System.out.println("ERROR: al ordenar la lista");
                }

                if(iProductoBusquedaFragmentView != null){
                    iProductoBusquedaFragmentView.hideProgress();
                }

            } else {
                System.out.println("ERROR: Productos Vacio");
                if(iProductoBusquedaFragmentView != null){
                    iProductoBusquedaFragmentView.hideProgress();
                }
            }
        } else {
            System.out.println("ERROR: Productos Vacio");
            if(iProductoBusquedaFragmentView != null){
                iProductoBusquedaFragmentView.hideProgress();
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ingresar la informacion a List<GeneralProductos>
     * @Fecha 10/05/22
     * @param listaObjetos
     * @return List<GeneralProductos>
     */
    private List<GeneralProductos> crearListaProductosGeneral(List<Object> listaObjetos) {

        List<GeneralProductos> generalProductos = new ArrayList<>();

        for (Object object: listaObjetos){
            GeneralProductos general = new GeneralProductos();

            if(object.getClass() == Producto.class){
                general.setProducto((Producto) object);
            } else if(object.getClass() == Promocion.class){
                general.setPromocion((Promocion) object);
            }
            generalProductos.add(general);
        }

        //InformacionSession.getInstance().setProductosActivosVenta(generalProductos);

        return generalProductos;

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ordenar la lista de objetos recibida
     * @Fecha 06/05/2022
     * @Param ProductosTransporte objeto con listas dentro
     */
    private List<Object> ordenarProductos(ProductosTransporte productos) {
        List<Object> listaObjetos = new ArrayList<>();

        for (int i=0; i < productos.getProductos().size();){
            for (int j=0; j < productos.getPromocion().size();){
                int comparacion = productos.getProductos().get(i).getNombre().compareTo(productos.getPromocion().get(j).getNombre());
                if(comparacion < 0){
                    listaObjetos.add(productos.getProductos().get(i));
                    productos.getProductos().remove(i);
                    comparaVacio(listaObjetos, productos.getProductos(), productos.getPromocion());
                } else if(comparacion == 0){
                    listaObjetos.add(productos.getProductos().get(i));
                    listaObjetos.add(productos.getPromocion().get(j));

                    productos.getProductos().remove(i);
                    productos.getPromocion().remove(i);
                    comparaVacio(listaObjetos, productos.getProductos(), productos.getPromocion());
                } else if(comparacion > 0){
                    listaObjetos.add(productos.getPromocion().get(j));
                    productos.getPromocion().remove(i);
                    comparaVacio(listaObjetos, productos.getProductos(), productos.getPromocion());
                }
            }
        }

        return listaObjetos;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar si las listas de productos y promociones estan vacias
     * @Fecha 06/05/2022
     * @Param List<Object> lista de objetos
     * @Param List<Producto> productos
     * @Param List<Promocion> promociones
     */
    private void comparaVacio(List<Object> listaObjetos, List<Producto> productos, List<Promocion> promociones) {
        if(!productos.isEmpty() || !promociones.isEmpty()) {
            if(productos.isEmpty()) {
                volcarPromociones(listaObjetos, promociones);
            } else if(promociones.isEmpty()) {
                volcarProductos(listaObjetos, productos);
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite volcar los datos de la lista de productos
     * @Fecha 06/05/2022
     * @Param List<Object> ordenada
     * @Param List<Producto> productos
     */
    private List<Object> volcarProductos(List<Object> listaObjetos, List<Producto> productos) {
        for(Producto producto: productos) {
            listaObjetos.add(producto);
        }
        productos.clear();
        return listaObjetos;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite volcar los datos de la lista de promociones
     * @Fecha 06/05/2022
     * @Param List<Object> ordenada
     * @Param List<Promocion> promociones
     */
    private List<Object> volcarPromociones(List<Object> listaObjetos, List<Promocion> promociones) {
        for(Promocion promocion: promociones) {
            listaObjetos.add(promocion);
        }
        promociones.clear();
        return listaObjetos;
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

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar la respuesta de la promocion
     * @Fecha 06/05/2022
     * @Param Promocion promocion
     */
    private void validarProductoPromocion(Promocion promocion) {
        if(promocion != null){
            if(iPromocionDetalleFragmentView!=null){
                iPromocionDetalleFragmentView.hideProgress();
                iPromocionDetalleFragmentView.goToProductoActivity();
            }
        } else {
            System.out.println("ERROR:  Se produjo un error al almacenar la promocion");
            if(iPromocionDetalleFragmentView!=null){
                iPromocionDetalleFragmentView.hideProgress();
            }
        }
    }
}
