package com.example.cs261;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class checkbalance extends AppCompatActivity {
    //   TextView getbalance;
    public ProgressBar pbar;
    private FirebaseUser user;
    String userphone;
    DatabaseReference myRef,myref2;
    DatabaseReference root,root1;
    EditText phonenum,amount;
    String _balance,_phone;
    String user_bal,up_bal;
     int userba,transbal,tra_bl,count;
 //   @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbalance);
        phonenum = findViewById(R.id.editTextPhone);
        amount = findViewById(R.id.amount);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
         root = db.getReference();
         root1 = db.getReference();
  //   pbar = (ProgressBar)findViewById(R.id.progressBar);
      //   _phone = "+91"+_phone;
        user = FirebaseAuth.getInstance().getCurrentUser();
        userphone = user.getPhoneNumber().toString();
      //   Toast.makeText(checkbalance.this,userphone,Toast.LENGTH_SHORT).show();
    //  getbalance = findViewById(R.id.balance2);
    }


    public void transferbalance(View view)
    {
       _phone = phonenum.getText().toString();
        _balance = amount.getText().toString();
       // Toast.makeText(checkbalance.this,_balance,Toast.LENGTH_SHORT).show();
        transbal = Integer.parseInt(_balance);
        _phone = "+91"+_phone;

/*
        myRef = FirebaseDatabase.getInstance().getReference().child(userphone);

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    String userbalance = snapshot.child("balance").getValue().toString();
                    Toast.makeText(checkbalance.this, userbalance, Toast.LENGTH_SHORT).show();
                    userba = Integer.parseInt(userbalance);
                    if(transbal<userba)
                    {
                        count = userba - transbal;
                        root1.child(userphone).child("balance").setValue(count);
                        Toast.makeText(checkbalance.this,"Successfull",Toast.LENGTH_SHORT).show();


                        myref2 = FirebaseDatabase.getInstance().getReference().child(_phone);
                        myref2.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                  if(snapshot.exists()) {
                                      String updatebal = snapshot.child("balance").getValue().toString();
                                      tra_bl = Integer.parseInt(updatebal);
                                      tra_bl = tra_bl + transbal;
                                      root.child(_phone).child("balance").setValue(tra_bl);
                                      //    Toast.makeText(checkbalance.this,updatebal,Toast.LENGTH_SHORT).show();
                                  }
                                  else
                                  {
                                      Toast.makeText(checkbalance.this,"No such user exist!",Toast.LENGTH_SHORT).show();
                                  }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        phonenum.setText("");
                        amount.setText("");


                    }
                    else
                    {
                        changeactivity();
                        Toast.makeText(checkbalance.this,"Low Balance",Toast.LENGTH_SHORT).show();

                       // finish();
                    }
                }


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

*/
        myRef = FirebaseDatabase.getInstance().getReference().child(userphone);

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    String userbalance = snapshot.child("balance").getValue().toString();
                //    Toast.makeText(checkbalance.this, userbalance, Toast.LENGTH_SHORT).show();
                    userba = Integer.parseInt(userbalance);
                    if(transbal<userba)
                    {
                     //   count = userba - transbal;
                     //   root1.child(userphone).child("balance").setValue(count);
                      //  Toast.makeText(checkbalance.this,"Successfull",Toast.LENGTH_SHORT).show();


                        myref2 = FirebaseDatabase.getInstance().getReference().child(_phone);
                        myref2.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                if(snapshot.exists())
                                {

                                    String updatebal = snapshot.child("balance").getValue().toString();
                                    tra_bl = Integer.parseInt(updatebal);
                                    tra_bl = tra_bl + transbal;
                                    root.child(_phone).child("balance").setValue(tra_bl);
                                      count = userba - transbal;
                                       root1.child(userphone).child("balance").setValue(count);
                                      Toast.makeText(checkbalance.this,"Successfull",Toast.LENGTH_SHORT).show();
                                    //    Toast.makeText(checkbalance.this,updatebal,Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(checkbalance.this,"No such user exist!",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error)
                            {

                            }
                        });

                        phonenum.setText("");
                        amount.setText("");


                    }
                    else
                    {
                        changeactivity();
                        Toast.makeText(checkbalance.this,"Low Balance",Toast.LENGTH_SHORT).show();

                        // finish();
                    }
                }


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


     //   HashMap hashMap = new HashMap();
    //    hashMap.put("balance",tra_bl);
    //    Toast.makeText(checkbalance.this, _balance + " " + user_bal +" " + up_bal, Toast.LENGTH_SHORT).show();
    //  checkamunt(transbal,userba);


    }


    private void changeactivity()
    {
       Intent intent = new Intent(checkbalance.this,MainActivity2.class);
       startActivity(intent);
       finishActivity(0);
    }
 /*   public void addupdatebalance()
    {
      //  FirebaseDatabase db = FirebaseDatabase.getInstance();
      //  DatabaseReference root = db.getReference();
      //  DatabaseReference root1 = db.getReference();
         tra_bl = tra_bl + transbal;
    //   String upbal = String.format("%d",tra_bl);
  //      Toast.makeText(checkbalance.this,upbal,Toast.LENGTH_SHORT).show();
        count = userba - transbal;
     //   String useco = String.format("%d",usercou);
    //    Toast.makeText(checkbalance.this,upbal,Toast.LENGTH_SHORT).show();
        root.child(_phone).child("balance").setValue(tra_bl);
        root1.child(userphone).child("balance").setValue(count);
       // Toast.makeText(checkbalance.this,"Successfull",Toast.LENGTH_SHORT).show();
        //      Toast.makeText(checkbalance.this,upbal,Toast.LENGTH_SHORT).show();
    }
*/

}