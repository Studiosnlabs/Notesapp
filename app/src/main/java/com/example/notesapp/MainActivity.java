package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView listView;
   static ArrayAdapter arrayAdapter;
   static ArrayList<String> notes=new ArrayList<>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case R.id.addNote:
                Intent intent = new Intent(getApplicationContext(),Activity2.class);
                startActivity(intent);
                return true;
                default:
                    return false;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView =(ListView) findViewById(R.id.notesList);

        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if (set==null){
            notes.add("Example note,click to add yours");
        }else{
            notes=new ArrayList(set);
        }



        notes.add("Notes Example,click to add yours");
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Activity2.class);
                intent.putExtra("noteNumber",i);
                startActivity(intent);




            }
        });


listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int itemToDelete =i;
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this note fosho?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       notes.remove(itemToDelete);
                       arrayAdapter.notifyDataSetChanged();

                        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

                        HashSet<String> set= new HashSet<>(MainActivity.notes);

                        sharedPreferences.edit().putStringSet("notes",set).apply();
                    }
                })
                .setNegativeButton("No",null)
                .show();

        return true;
    }
});



    }
}
