package com.aunico.os.commitment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Viewholder> {

private ArrayList<Datareterive> datareterive;

    public RecyclerAdapter(ArrayList<Datareterive> datareterive) {
        this.datareterive = datareterive;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler,parent,false);
        Viewholder viewholder=new Viewholder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Datareterive retervedata=datareterive.get(position);
        holder.task.setText(retervedata.getTask());
        holder.date.setText(retervedata.getDate());
        holder.time.setText(retervedata.getTime());
        holder.priority.setText(retervedata.getPriority());
        if(holder.priority.getText().equals(" Low"))
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#008000"));
        }
        if (holder.priority.getText().equals(" Medium "))
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffa500"));
        }
        if (holder.priority.getText().equals("High"))
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#f00000"));

        }
    }

    @Override
    public int getItemCount() {
        return datareterive.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder
    {

       public TextView task,date,time,priority;
       public CardView cardView;
        public Viewholder(final View itemView) {
            super(itemView);

            task=itemView.findViewById(R.id.task);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            priority=itemView.findViewById(R.id.priority);
            cardView=itemView.findViewById(R.id.cardview);

        }
    }
}
