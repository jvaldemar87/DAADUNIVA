package com.example.valdemar.daaduniva.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.valdemar.daaduniva.Adapter.AdapterPrograma;
import com.example.valdemar.daaduniva.Generals.Constantes;
import com.example.valdemar.daaduniva.Models.ItemPrograma;
import com.example.valdemar.daaduniva.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Programa extends android.support.v4.app.Fragment implements View.OnClickListener {
    Context context;
    Activity activity;
    View rootView;
    TextView textViewTitle;
    RecyclerView recyclerView;
    ArrayList<ItemPrograma> arrayList = new ArrayList<>();
    AdapterPrograma adapter;
    ImageView imageBack;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        rootView = inflater.inflate(R.layout.fragment_programa,container,false);
        recyclerView = rootView.findViewById(R.id.recicleyViewId);
        textViewTitle = rootView.findViewById(R.id.text_title_id);
        imageBack = rootView.findViewById(R.id.imageBack);

        imageBack.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BuildRecyclerview();
    }

    private void BuildRecyclerview() {
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                Constantes.BASE_URL + "events",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("Servicios finalizados", response.toString());
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            JSONArray informacion = respuesta.getJSONArray("");
                            arrayList.clear();

                            for (int i = 0; informacion.length() > i; i++) {
                                JSONObject evento = informacion.getJSONObject(i);
                                String id = evento.getString("id");
                                String date = evento.getString("date");
                                String start_hour = evento.getString("start_hour");
                                String end_hour = evento.getString("end_hour");
                                String name = evento.getString("name");
                                String description = evento.getString("description");
                                String pdf = evento.getString("pdf");
                                arrayList.add(new ItemPrograma(
                                        id,
                                        date,
                                        start_hour,
                                        end_hour,
                                        name,
                                        description,
                                        pdf
                                ));
                                adapter = new AdapterPrograma(context, activity, arrayList);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        /*arrayList.clear();
        arrayList.add(new ItemPrograma("0","27 / 09 / 2018","09:30","11:00","Registro","descripcion de cada evento"));
        arrayList.add(new ItemPrograma("1","27 / 09 / 2018","09:30","11:00","El desarrollo territorial y el uso de energías renovables","Conferencia ALUMNI 1: Gerardo Lara Gómez (SEMADET)"));
        adapter = new AdapterPrograma(context,activity,arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageBack:
                activity.onBackPressed();
                break;
        }
    }
}
