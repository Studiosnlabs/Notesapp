package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashSet;

public class Activity2 extends AppCompatActivity {
    int noteId;
    EditText editNote ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        editNote = findViewById(R.id.editText);


        Intent intent=getIntent();
         noteId=intent.getIntExtra("noteNumber",-1);
        if (noteId !=-1){

            editNote.setText(MainActivity.notes.get(noteId));



        }else{

            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            MainActivity.notes.set(noteId,String.valueOf(charSequence));
            MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

                HashSet<String> set= new HashSet<>(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });











    }
}
