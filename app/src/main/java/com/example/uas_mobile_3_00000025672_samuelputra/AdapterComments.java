package com.example.uas_mobile_3_00000025672_samuelputra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterComments extends RecyclerView.Adapter<AdapterComments.ViewHolder> {
    private ArrayList<comment> DaftarComment;
    private Context context;

    public AdapterComments(ArrayList<comment> comments,Context context){
        this.DaftarComment = comments;
        this.context =context;
    }


    @NonNull
    @Override
    public AdapterComments.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComments.ViewHolder holder, int position) {
        final String comment = DaftarComment.get(position).getComment();
        final String commenter = DaftarComment.get(position).getCommenter();

        holder.nameView.setText(comment);
        holder.descView.setText(commenter);
    }

    @Override
    public int getItemCount() {
        return DaftarComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView descView;
        TextView commentsView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.tv_namaMenu);
            descView = (TextView) itemView.findViewById(R.id.tv_descMenu);
        }
    }
}
