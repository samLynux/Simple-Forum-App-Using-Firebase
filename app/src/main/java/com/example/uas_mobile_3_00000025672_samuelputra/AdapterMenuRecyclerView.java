package com.example.uas_mobile_3_00000025672_samuelputra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterMenuRecyclerView extends RecyclerView.Adapter<AdapterMenuRecyclerView.ViewHolder> implements View.OnClickListener {
    private ArrayList<Menu> DaftarMenu;
    private Context context;
    private String title;
    private int viewID = 0;


    public AdapterMenuRecyclerView(ArrayList<Menu> menus,Context context){
        this.DaftarMenu = menus;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent,false);
        v.setId(viewID++);
        v.setOnClickListener(this);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String name = DaftarMenu.get(position).getTitle();
        final String desc = DaftarMenu.get(position).getDeskripsi();
        final int comments = DaftarMenu.get(position).getComments();

        holder.nameView.setText(name);
        holder.descView.setText(desc);
        //holder.commentsView.setText(Integer.toString(comments));
    }

    @Override
    public int getItemCount() {
        return DaftarMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView descView;
        TextView commentsView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.tv_namaMenu);
            descView = (TextView) itemView.findViewById(R.id.tv_descMenu);
            commentsView = (TextView) itemView.findViewById(R.id.tv_commentsMenu);
        }
    }

    @Override
    public void onClick(View v) {

        int position = v.getId();
       final String title = DaftarMenu.get(position).getTitle();



        SharedPreferences sp = context.getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("thread", title);
        ed.apply();


        Intent intent = new Intent(context, CommentsActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();




    }
}

