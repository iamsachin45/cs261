package com.example.cs261;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity
{
    private FirebaseUser user;
    TextView textview,text;
    String userphone;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userphone = user.getPhoneNumber().toString();
        myRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child(userphone);
        textview = (TextView)findViewById(R.id.t4);
        text = (TextView)findViewById(R.id.t5);
        checkprofile();
    }
    public void checkprofile()
    {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String sys_balance = snapshot.child("balance").getValue().toString();
                String sys_mobilenumber = snapshot.child("number").getValue().toString();
                //     Toast.makeText(profile.this, "Your Main Balance is "+sys_balance, Toast.LENGTH_SHORT).show();
                textview.setText(sys_balance);
                text.setText(sys_mobilenumber);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //    Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void back(View view)
    {
        Intent intent = new Intent(profile.this,MainActivity2.class);
        startActivity(intent);
    }


}