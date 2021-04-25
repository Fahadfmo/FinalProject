package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4, et5;
    Button btn1, btn2, btn3, btn4;
    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

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

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot users: snapshot.getChildren()){
                    list.add(String.valueOf(users.child("userId").getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                Intent intent = new Intent(FirebaseActivity.this, FirebaseUsersList.class);
                startActivity(intent);
            }
        });

    }

    private void deleteUser(String id) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        if(list.contains(id)) {
            mRef.removeValue();
            Toast.makeText(this, "User Deleted successfully from Firebase Database", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No User with given Id found in Firebase Database", Toast.LENGTH_SHORT).show();
        }
    }

    private void addUser(String id, String fName, String lName, String phone, String email) {
        if(id.equals("")){
            Toast.makeText(this, "User id cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(list.contains(id)) {
            Toast.makeText(this, "User with id already present in Firebase Database", Toast.LENGTH_SHORT).show();
        }else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users").child(id);
            myRef.child("userId").setValue(id);
            myRef.child("firstName").setValue(fName);
            myRef.child("lastName").setValue(lName);
            myRef.child("emailAddress").setValue(email);
            myRef.child("phoneNumber").setValue(phone);
            Toast.makeText(this, "User Added successfully to Firebase Database", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser(String id, String fName, String lName, String phone, String email) {
        if (!list.contains(id)) {
            Toast.makeText(this, "No User with given Id found in Firebase Database", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users").child(id);
            myRef.child("userId").setValue(id);
            myRef.child("firstName").setValue(fName);
            myRef.child("lastName").setValue(lName);
            myRef.child("emailAddress").setValue(email);
            myRef.child("phoneNumber").setValue(phone);
            Toast.makeText(this, "User Updated successfully in Firebase Database", Toast.LENGTH_SHORT).show();
        }
    }
}