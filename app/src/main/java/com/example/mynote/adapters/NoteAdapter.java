package com.example.mynote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mynote.Models.Note;
import com.example.mynote.R;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> noteList;

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false);
        }

        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewContent = convertView.findViewById(R.id.textViewContent);

        Note note = noteList.get(position);
        textViewTitle.setText(note.getTitle());
        textViewContent.setText(note.getContent());

        return convertView;
    }
}

