package com.example.naturalfisherapp.utilidades;

import android.app.Activity;
import android.content.Intent;

import com.example.naturalfisherapp.view.activities.MenuPrincipalActivity;

public class UtilidadesView {

    public static void goToActivity(Activity activityContext, Class<Activity> activity){
        Intent intent = new Intent(activityContext, activity.getClass());
        activityContext.startActivity(intent);
        activityContext.finish();
    }

}
