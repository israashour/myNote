package com.example.mynote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Note> notes;
    private ArrayAdapter<Note> adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        notes = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("notes")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("Firestore", "Listen failed: " + e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            for (DocumentChange dc : snapshot.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        Note note = dc.getDocument().toObject(Note.class);
                                        notes.add(note);
                                        break;
                                    case MODIFIED:
                                        // Handle modified note if needed
                                        break;
                                    case REMOVED:
                                        // Handle removed note if needed
                                        break;
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = notes.get(position);
                Intent intent = new Intent(MainActivity.this, UpdateNoteActivity.class);
                intent.putExtra("noteId", selectedNote.getId());
                intent.putExtra("noteContent", selectedNote.getContent());
                startActivity(intent);
            }
        });
    }

    public void onAddNoteClick(View view) {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);
    }
}
