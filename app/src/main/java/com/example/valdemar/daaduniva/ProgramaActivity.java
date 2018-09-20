package com.example.valdemar.daaduniva;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.valdemar.daaduniva.Adapter.AdapterPrograma;
import com.example.valdemar.daaduniva.Fragment.Programa;
import com.example.valdemar.daaduniva.Models.ItemPrograma;
import com.example.valdemar.daaduniva.Models.ItemViewFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
                Toast.makeText(context, "onClickAgendar: " +strDate, Toast.LENGTH_SHORT).show();
                break;
            case R.id.name_event_id:
                Toast.makeText(context, "onClickAgendar2222: " +strDate, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void onClickDescribe(View view){
        switch (view.getId()) {
            case R.id.descrip_event_id:
                Toast.makeText(context, "onClickDescribe", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void OnCLickPrograma(ItemPrograma item, int position) {
        setEvent(item);
        Toast.makeText(context, "Evento agendado", Toast.LENGTH_SHORT).show();

    }

    public void OnclickDescipcion(ItemPrograma item, int position){
        getDocument(item);


    }

    private void getDocument(ItemPrograma item) {
        //item.getId();
        Toast.makeText(context, "funcion para descargar PDF: "+item.getId(), Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse(item.getNombre());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void setEvent(ItemPrograma item){
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*2000);
        intent.putExtra("title", item.getNombre());
        startActivity(intent);
    }
}