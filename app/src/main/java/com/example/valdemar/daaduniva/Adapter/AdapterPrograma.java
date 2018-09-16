package com.example.valdemar.daaduniva.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.valdemar.daaduniva.Models.ItemPrograma;
import com.example.valdemar.daaduniva.R;

import java.util.ArrayList;

public class AdapterPrograma extends RecyclerView.Adapter<AdapterPrograma.ViewHolder>{
    Context context;
    Activity activity;

    ArrayList<ItemPrograma> items;

    OnClick onClick;

    public AdapterPrograma(Context context,Activity activity, ArrayList<ItemPrograma> items){
        this.context = context;
        this.activity = activity;
        this.items = items;

        try{
            onClick = (OnClick) activity;
        }catch (ClassCastException e){
            //throw new ClassCastException("It not work adapter onclick");
        }
    }

    public interface OnClick{
        void OnCLickPrograma(ItemPrograma item, int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.row_programa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final  int position){
        final ItemPrograma item = items.get(position);

        holder.horaIni.setText(item.getHoraIni());
        holder.horaFin.setText(item.getHoraFin());
        holder.fecha.setText(item.getFecha());
        holder.eventoNombre.setText(item.getNombre());
        holder.eventoDescribe.setText(item.getDescripcion());

        holder.contend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.OnCLickPrograma(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View contend;
        TextView horaIni, horaFin, eventoNombre, eventoDescribe,fecha;

        public ViewHolder(View itemView) {
            super(itemView);

            contend = itemView.findViewById(R.id.view_content_id);
            fecha = itemView.findViewById(R.id.date_id);
            horaIni = itemView.findViewById(R.id.schedule_begin);
            horaFin = itemView.findViewById(R.id.schedule_end);
            eventoNombre = itemView.findViewById(R.id.name_event_id);
            eventoDescribe = itemView.findViewById(R.id.descrip_event_id);
        }
    }
}
