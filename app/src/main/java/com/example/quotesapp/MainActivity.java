package com.example.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quotesapp.Adapters.CustomAdapter;
import com.example.quotesapp.Model.Quotes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ListView listView;
    ArrayList<Quotes> arrayList;
    CustomAdapter customAdapter;
    DatabaseHelper databaseHelper;
    Button clearBtn;
    Spinner spinner;
    int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreCreateDB.copyDB(this);
        listView = findViewById(R.id.listView);
        spinner = findViewById(R.id.spin);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        Resources resources = getResources();
        colors = resources.getIntArray(R.array.colors);
        inflateList();
        inflateSpinner();

        clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(view -> {
            inflateList();
            inflateSpinner();
        });
    }

    private void inflateList() {
        arrayList = databaseHelper.getAllData();
        customAdapter = new CustomAdapter(this, arrayList, colors);
        listView.setAdapter(customAdapter);
    }

    private void inflateFilteredList(String author) {
        arrayList = databaseHelper.selectedAuthor(author);
        customAdapter = new CustomAdapter(this, arrayList, colors);
        listView.setAdapter(customAdapter);
    }

    private void inflateSpinner() {
        String[] authors = databaseHelper.getAuthors();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authors);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(!(adapterView.getSelectedItem().equals("-----"))) {
            inflateFilteredList(adapterView.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}