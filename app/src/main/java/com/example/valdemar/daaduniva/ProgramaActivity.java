package com.example.valdemar.daaduniva;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.valdemar.daaduniva.Adapter.AdapterPrograma;
import com.example.valdemar.daaduniva.Fragment.Programa;
import com.example.valdemar.daaduniva.Generals.WiFi;
import com.example.valdemar.daaduniva.Models.ItemPrograma;
import com.example.valdemar.daaduniva.Models.ItemViewFragment;
import com.example.valdemar.daaduniva.Services.ApiServices;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProgramaActivity extends AppCompatActivity implements AdapterPrograma.OnClick{
    Context context = this;
    Activity activity = this;

    android.support.v4.app.FragmentManager fm;
    ArrayList<ItemViewFragment> fragments = new ArrayList<>();
    FrameLayout content;
    int intView = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programa);


        content = findViewById(R.id.content_id);
        fragments.add(new ItemViewFragment(0, "", new Programa()));
        fm = getSupportFragmentManager();
        changeView(0);
    }

    private void changeView(int position){
        if (intView != position){
            ItemViewFragment frag = fragments.get(position);
            fm.beginTransaction().replace(content.getId(), frag.getFragment()).commit();
            intView = position;
        }
    }

    public void onClickAgendar(View view){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = sdf.format(cal.getTime());
        switch (view.getId()) {
            case R.id.linearFecha:
                break;
            case R.id.name_event_id:
                break;
        }

    }

    public void onClickDescribe(View view){
        switch (view.getId()) {
            case R.id.descrip_event_id:
                break;
        }
    }

    @Override
    public void OnCLickPrograma(final ItemPrograma item, int position) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("¡ALERTA!")
                .setMessage("¿Estas seguro que deseas agendar el programa?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (WiFi.connectionWifi(context))
                            Server(item);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void OnclickDescipcion(ItemPrograma item, int position){
        getDocument(item);
    }

    private void getDocument(ItemPrograma item) {
        //item.getId();

        Uri uri = Uri.parse(item.getPdf());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void setEvent(ItemPrograma item){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateInit = null;
            Date dateFin = null;

            dateInit = dateFormat.parse(item.getFecha() + " " + item.getHoraIni());
            dateFin = dateFormat.parse(item.getFecha() + " " + item.getHoraFin());

            long secondsInit = dateInit.getTime();
            long secondsFin = dateFin.getTime();

            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", secondsInit);
            intent.putExtra("rrule", "FREQ=YEARLY");
            intent.putExtra("endTime", secondsFin);
            intent.putExtra("title", item.getNombre());
            startActivity(intent);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "No se puede completar la acción.", Toast.LENGTH_SHORT).show();
        }

    }

    private void Server(final ItemPrograma item){
        SharedPreferences preferences;
        preferences = getSharedPreferences("cache_univa",0);
        String email = preferences.getString("email","");

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW"),
                "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                        "Content-Disposition: form-data; name=\"event_id\"\r\n\r\n"+item.getId()+"\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                        "Content-Disposition: form-data; name=\"email\"\r\n\r\n"+ email +"\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW--");


        ApiServices.event service = ApiServices.getRetrofitInstance().create(ApiServices.event.class);
        Call<Integer> call = service.addEvent(requestBody);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                setEvent(item);
            }

            @Override
            public void onFailure(Call<Integer>  call, Throwable t) {
                Toast.makeText(context, "Problemas de petición.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}