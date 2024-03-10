package com.yuri.quiorb.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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

    public static void fecharBtn(Activity activity, ImageButton backBtn){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public static void configurarTopTxt(TextView topTxt, String nomeTela){
        topTxt.setText(nomeTela);
    }
}
