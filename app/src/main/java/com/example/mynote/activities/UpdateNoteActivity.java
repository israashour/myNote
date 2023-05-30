package com.example.mynote.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateNoteActivity extends AppCompatActivity {

    private EditText editTextContent;
    private String noteId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        editTextContent = findViewById(R.id.editTextContent);
        db = FirebaseFirestore.getInstance();

        noteId = getIntent().getStringExtra("noteId");
        String noteContent = getIntent().getStringExtra("noteContent");
        editTextContent.setText(noteContent);
    }

    public void onUpdateNoteClick(View view) {
        String content = editTextContent.getText().toString();
        if (!content.isEmpty()) {
            Note note = new Note();
            note.setContent(content);

            db.collection("notes")
                    .document(noteId)
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
            Toast.makeText(this, "Note content cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDeleteNoteClick(View view) {
        db.collection("notes")
                .document(noteId)
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
