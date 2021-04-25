package com.example.finalproject;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUsersList extends ListActivity {

    ListView listView;
    ArrayList<String> data;
    ArrayAdapter< String > listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        data = new ArrayList<>();
        listView = getListView();
        listAdapter = new ArrayAdapter < String > (this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(listAdapter);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for(DataSnapshot users: snapshot.getChildren()){
                    String userStr ="\nUser Id: " + users.child("userId").getValue() + "\n" +
                            "First Name: " + users.child("firstName").getValue() + "\n" +
                            "Last Name: " +  users.child("lastName").getValue() + "\n" +
                            "Email: " + users.child("emailAddress").getValue() + "\n" +
                            "Phone: " + users.child("phoneNumber").getValue() + "\n" ;

                    data.add(userStr);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}