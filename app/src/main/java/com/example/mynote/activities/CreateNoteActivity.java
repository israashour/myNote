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

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editTextContent;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        editTextContent = findViewById(R.id.editTextContent);
        db = FirebaseFirestore.getInstance();
    }

    public void onSaveNoteClick(View view) {
        String content = editTextContent.getText().toString();
        if (!content.isEmpty()) {
            Note note = new Note();
            note.setContent(content);

            db.collection("notes")
                    .add(note)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateNoteActivity.this, "Note added successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CreateNoteActivity.this, "Failed to add note", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Note content cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
