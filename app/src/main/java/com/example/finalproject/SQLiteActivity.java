package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SQLiteActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4, et5;
    Button btn1, btn2, btn3, btn4;
    ArrayList<String> list;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        dbHelper = new DatabaseHelper(this);
        Cursor cur = dbHelper.ViewList();
        while(cur.moveToNext())
        {
            String id = cur.getString(0);
            list.add(id);
        }
        list = new ArrayList<>();

        et1 = findViewById(R.id.et_1);
        et2 = findViewById(R.id.et_2);
        et3 = findViewById(R.id.et_3);
        et4 = findViewById(R.id.et_4);
        et5 = findViewById(R.id.et_5);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et1.getText().toString();
                String fName = et2.getText().toString();
                String lName = et3.getText().toString();
                String phone = et4.getText().toString();
                String email = et5.getText().toString();
                addUser(id, fName, lName, phone, email);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et1.getText().toString();
                String fName = et2.getText().toString();
                String lName = et3.getText().toString();
                String phone = et4.getText().toString();
                String email = et5.getText().toString();
                updateUser(id, fName, lName, phone, email);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et1.getText().toString();
                deleteUser(id);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SQLiteActivity.this, SQLiteList.class);
                startActivity(intent);
            }
        });

    }

    private void deleteUser(String id) {

        if(list.contains(id)) {
            dbHelper.DeleteUser(id);
            Toast.makeText(this, "User Deleted successfully from SQLite Database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No User with given Id found in SQLite Database", Toast.LENGTH_SHORT).show();
        }
    }

    private void addUser(String id, String fName, String lName, String phone, String email) {
        if(id.equals("")){
            Toast.makeText(this, "User id cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(list.contains(id)) {
            Toast.makeText(this, "User with id already present in SQLite Database", Toast.LENGTH_SHORT).show();
        }else {
            list.add(id);
            dbHelper.AddUser(id,fName,lName,phone,email);
        }
    }

    private void updateUser(String id, String fName, String lName, String phone, String email) {
        if (!list.contains(id)) {
            Toast.makeText(this, "No User with given Id found in SQLite Database", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.UpdateUser(id,fName,lName,phone,email);
        }
    }
}