package com.example.naturalfisherapp.view.interfaces.dialog;

import java.util.List;

public interface ISelectOptionDialogFragment {

    void cargarAdapter(List<String> listOptions, int posicionSeleccionada);
    void dismissDialog();
    void cambiarOpcion(String opcion, int posicionSeleccionada);

}
