package com.example.cs261;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity
{
    private FirebaseUser user;
    String userphone;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userphone = user.getPhoneNumber().toString();
        myRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child(userphone);
     //   Intent intent = getIntent();
       // userphone = intent.getStringExtra("phone");
      //  databasehelper db = new databasehelper();
       // userphone// = db.getBalance();
     //   Toast.makeText(MainActivity2.this, userphone, Toast.LENGTH_SHORT).show();
        Button signout = findViewById(R.id.signputbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity2.this,MainActivity.class);
              //  i.putExtra("user",user);
                startActivity(i);
                finish();

            }
        });

    }
    public void checkbalance(View view)
    {
     ///   String _balance =
      //    Intent i = new Intent(MainActivity2.this,checkbalance.class);
     //     Intent in = getIntent();
   //   user =  in.getStringExtra("user");
       //   startActivity(i);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //    String count = "sachin";
                   String sys_balance = snapshot.child("balance").getValue().toString();
                 //   Toast.makeText(MainActivity2.this,"hello",Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity2.this, "Your Main Balance is "+sys_balance, Toast.LENGTH_SHORT).show();
                 //   Intent i = new Intent(MainActivity2.
                //   this,checkbalance.class);
               //     i.putExtra("sysbalance",sys_balance);
                 //   startActivity(i);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //    Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
   public void transfer(View view)
    {
        Intent intent = new Intent(MainActivity2.this,checkbalance.class);
        startActivity(intent);
       /*  myRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot)
             {
               Intent intent = new Intent(MainActivity2.this,checkbalance.class);
               startActivity(intent);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });*/
    }

    public void profile(View view)
    {
         Intent intent = new Intent(MainActivity2.this,profile.class);
         startActivity(intent);
    }

}
