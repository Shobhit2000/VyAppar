package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gov.ModalClasses.user;
import com.example.gov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {

   EditText otp,phonemuber;

    FirebaseAuth auth;
    com.example.gov.ModalClasses.user user;
    Button sendotp;
    Button checkotp;
    String codeSent;
    Intent intent;
    Intent intent1;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        intent=getIntent();

        otp=findViewById(R.id.otpt);


        auth=FirebaseAuth.getInstance();
        checkotp=findViewById(R.id.check);

        user= (user) intent.getSerializableExtra("values");


        verifyPhoneNumber(user.getPhone_number());


        checkotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { verifySignIncode(codeSent);

            }
        });







    }


    public void verifySignIncode(String verifyNUmber)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyNUmber, otp.getText().toString());
        signInWithPhoneAuthCredential(credential);

    }


    public void createUser(FirebaseUser user1)
    {

        Map<String, Object> user2=new HashMap<>();
        user2.put("name",user.getName());
        user2.put("address",user.getAddress());
        user2.put("email",user.getEmail());
        user2.put("type",user.getType());
        user2.put("userId",user1.getUid());
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        if(user.getType()==0)
        {
            value="Customer";
        }else
        {
            value="Vendor";
        }

        firebaseFirestore.collection(value).document(user1.getUid()).set(user2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

              boolean s=  intent.getBooleanExtra("type",false);
                if(user.getType()==1)
                {


                intent1=new Intent(getApplicationContext(), VendorAuth.class);
                intent1.putExtra("value","0");

                startActivity(intent1);
                Log.e("verify","ya");}
                else if(user.getType()==0){
                    intent1=new Intent(getApplicationContext(), Activity_MAIN.class);
                    intent1.putExtra("value","0");

                    startActivity(intent1);
                    Log.e("verify","ya");
                }


            }
        });






    }


    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"User Signed IN",Toast.LENGTH_LONG).show();






                            FirebaseUser user = task.getResult().getUser();

                            createUser(user);
                            // ...
                        } else {

                        }
                    }
                });
    }

    public void verifyPhoneNumber(String number)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               "+"+"91"+number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
        Toast.makeText(getApplicationContext(),"phone",Toast.LENGTH_LONG).show();

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without




            //     user action.




        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.


            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request]
                Log.e("verify",e.getMessage());
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.e("verify",e.getMessage());
                // ...
            }

            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            super.onCodeSent(verificationId, token);
            Log.e("verify",verificationId);
            codeSent=verificationId;







            // ...
        }
    };



}