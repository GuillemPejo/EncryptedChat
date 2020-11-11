package com.example.criptografia_xat_guillem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MissatgeAdapter extends RecyclerView.Adapter<MissatgeAdapter.MyViewHolder> {
    private List<Missatge> missatgeList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView missatge;
        public TextView username;

        public MyViewHolder(View v) {
            super(v);

            missatge = v.findViewById(R.id.msg);
            username = v.findViewById(R.id.username);
        }
    }

    public MissatgeAdapter(List<Missatge> missatgeList) {
        this.missatgeList = missatgeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Missatge item = missatgeList.get(position);
        holder.missatge.setText(item.getMissatge());
        holder.username.setText(item.getUsername());

    }

    @Override
    public int getItemCount() {
        return this.missatgeList.size();
    }
}