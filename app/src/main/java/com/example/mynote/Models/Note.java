package com.example.mynote.Models;

public class Note {
    private String id;
    private String content;

    public Note() {
        // Empty constructor needed for Firestore
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return content;
    }
}

