package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SQLiteList extends ListActivity {

    ListView listView;
    ArrayList<String> data;
    ArrayAdapter< String > listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        data = new ArrayList<>();
        DatabaseHelper MyDB = new DatabaseHelper(this);
        listView = getListView();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(listAdapter);

        data.clear();
        Cursor cur = MyDB.ViewList();
        while (cur.moveToNext()) {
            String userStr = "\nUser Id: " + cur.getString(0) + "\n" +
                    "First Name: " + cur.getString(1) + "\n" +
                    "Last Name: " + cur.getString(2) + "\n" +
                    "Email: " + cur.getString(3) + "\n" +
                    "Phone: " + cur.getString(4) + "\n";

            data.add(userStr);
        }
        listAdapter.notifyDataSetChanged();
    }
}