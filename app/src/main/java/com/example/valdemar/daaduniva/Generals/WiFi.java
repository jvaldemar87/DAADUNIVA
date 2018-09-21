package com.example.valdemar.daaduniva.Generals;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.widget.Toast;

public class WiFi {
    public static boolean connectionWifi(Context ctx)
    {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo redes_wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo datos  = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (String.valueOf(redes_wifi.getState()).equals("CONNECTED") || String.valueOf(datos.getState()).equals("CONNECTED"))
            bConectado = true;
        else
            Toast.makeText(ctx, "NO hay conexión a internet.", Toast.LENGTH_SHORT).show();
        return bConectado;
    }
}
