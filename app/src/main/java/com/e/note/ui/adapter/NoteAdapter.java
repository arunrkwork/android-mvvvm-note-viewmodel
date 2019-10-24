package com.e.note.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.note.R;
import com.e.note.data.entities.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    List<Note> list;
    private RecyclerViewClickListener mListener;

    public NoteAdapter(List<Note> list, RecyclerViewClickListener listener) {
        this.list = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_note, null
        ), mListener);
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

    public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        TextView txtTitle, txtDesc;

        public NoteHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }

    public void addItems(List<Note> mList){
        list = mList;
        notifyDataSetChanged();
    }

}
