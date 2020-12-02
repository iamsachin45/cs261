package com.example.cs261;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private String verificationId;
    private  ProgressBar progressBar;
    private FirebaseAuth mAuth;
    public  EditText phoneNo;
    private  EditText otp;
    private  Button login;
    private  TextView getotp;
    private  TextView otpsent;
    private TextView resendOTP;
    private boolean getotpclicked = false;
    private  TextView countdowntimer;
  // private   String sachin;
    private String number;
   public int balance;
 // private    String name;
    Dialog dialog;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
    }

    public void initElements(){

        mAuth = FirebaseAuth.getInstance();
        phoneNo = findViewById(R.id.txtPhone);
        otp = findViewById(R.id.txtotp);
        getotp = findViewById(R.id.get_otp);
        login  = findViewById(R.id.btnLogin);
        resendOTP = findViewById(R.id.resend_otp);
        otpsent = findViewById(R.id.otp_sent);
        countdowntimer = findViewById(R.id.countdown_timer);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getotpOnclick();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginOnClick();;

            }
        });
        login.setTextColor(Color.parseColor("#C0BEBE"));

    }

    public void loginOnClick(){

        number = phoneNo.getText().toString().trim();

        String otp1 = otp.getText().toString().trim();
         addusers(number);

        if (number.length() == 10 && otp1.length() > 4){

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            verifyCode(otp1);


        }else {

            if (number.isEmpty() || number.length() < 10){

                phoneNo.setError("Valid number is required");
                phoneNo.requestFocus();

            }else if (otp1.isEmpty() || otp1 .length() < 5){

                otp.setError("Valid OTP is required");
                otp.requestFocus();


            }


        }

    }

    private void addusers(String temp)
    {

     //   name = sachin;
       balance = 45000;


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference();

      //  String phonenum;
       databasehelper userhelp = new databasehelper(number,balance);
        temp = "+91"+temp;
        root.child(temp).setValue(userhelp);
    //    DatabaseReference rootnode = FirebaseDatabase.getInstance().getReference("Users");
     //  rootnode.setValue(name);

    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential)
    {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user !=null){

                                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                                i.putExtra("phone",number);
                                startActivity(i);
                                finish();

                            }else {

                                if (dialog != null){

                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
    }

    public void getotpOnclick(){

        if (!getotpclicked){

            String num   = phoneNo.getText().toString().trim();

            if(num.length() != 10){

                phoneNo.setError("Valid number is required");
                phoneNo.requestFocus();

            }else {

                getotpclicked = true;
                String phoneNumber = "+91" + num;
                sendVerificationCode(phoneNumber);
                getotp.setTextColor(Color.parseColor("#C0BEBE"));
                dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_wait);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


            }

        }


    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L,TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(@NonNull String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

            dialog.dismiss();
            login.setTextColor(Color.parseColor("#000000"));
            otpsent.setText("OTP has been sent yo your mobile number");
            otpsent.setVisibility(View.VISIBLE);
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;

            countdowntimer.setVisibility(View.VISIBLE);

            new CountDownTimer(60000,1000){


                @Override
                public void onTick(long millisUntilFinished) {
                    countdowntimer.setText("" + millisUntilFinished/1000);
                }
                @Override
                public void onFinish()
                {
                    resendOTP.setVisibility(View.VISIBLE);
                    countdowntimer.setVisibility(View.INVISIBLE);
                }
            }.start();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
        {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null)
            {
                otp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e)
        {
            getotpclicked = false;
            getotp.setTextColor(Color.parseColor("00000FF"));
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

}