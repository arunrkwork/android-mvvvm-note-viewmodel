package com.e.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    List<Note> list;

    public NoteAdapter(List<Note> list) {
        this.list = list;
    }

    public void setList(List<Note> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_note, null
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtDesc.setText(list.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDesc;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }

    public void addItems(List<Note> mList){
        list = mList;
        notifyDataSetChanged();
    }

}
