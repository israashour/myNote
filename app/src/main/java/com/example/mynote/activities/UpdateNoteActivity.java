package com.example.mynote.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mynote.Models.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private FirebaseFirestore db;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        db = FirebaseFirestore.getInstance();

        // Retrieve the note object from intent
        note = (Note) getIntent().getSerializableExtra("note");

        // Set the existing title and content in EditText fields
        editTextTitle.setText(note.getTitle());
        editTextContent.setText(note.getContent());
    }

    public void onUpdateNoteClick(View view) {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        if (!title.isEmpty() && !content.isEmpty()) {
            note.setTitle(title);
            note.setContent(content);

            db.collection("notes")
                    .document(note.getId())
                    .set(note)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateNoteActivity.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UpdateNoteActivity.this, "Failed to update note", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Note title and content cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDeleteNoteClick(View view) {
        db.collection("notes")
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateNoteActivity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdateNoteActivity.this, "Failed to delete note", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
