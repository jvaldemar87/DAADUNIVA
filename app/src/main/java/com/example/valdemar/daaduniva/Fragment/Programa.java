package com.example.valdemar.daaduniva.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valdemar.daaduniva.Adapter.AdapterPrograma;
import com.example.valdemar.daaduniva.Models.ItemPrograma;
import com.example.valdemar.daaduniva.R;

import java.util.ArrayList;

public class Programa extends Fragment implements View.OnClickListener {
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
        arrayList.clear();
        arrayList.add(new ItemPrograma("0","27 / 09 / 2018","09:30","11:00","Registro","descripcion de cada evento"));
        arrayList.add(new ItemPrograma("0","27 / 09 / 2018","09:30","11:00","El desarrollo territorial y el uso de energías renovables","Conferencia ALUMNI 1: Gerardo Lara Gómez (SEMADET)"));
        adapter = new AdapterPrograma(context,activity,arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
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
