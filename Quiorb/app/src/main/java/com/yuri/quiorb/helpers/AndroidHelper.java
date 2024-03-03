package com.yuri.quiorb.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AndroidHelper {

    public static void trocarIntent(Context context, Class<?> clsIntent){
        Intent intent = new Intent(context, clsIntent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void mostrarToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
