package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class LoginScreen extends AppCompatActivity {


    Button signin;
    TextView register;
    EditText editTextPassword;
    String codeSent;
    TextView tv2;
    Boolean numberSent = true;
    FirebaseAuth auth;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        signin = findViewById(R.id.signin);
        register = findViewById(R.id.register);
        editTextPassword = findViewById(R.id.editTextPassword);
        tv2 = findViewById(R.id.tv2);
        auth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberSent == true) {
                    if (editTextPassword.getText().toString() == null) {
                        Toast.makeText(getApplicationContext(), "Enter Your PHone Number", Toast.LENGTH_LONG).show();
                    } else {
                        verifyPhoneNumber(editTextPassword.getText().toString());
                    }
                } else {
                    if (editTextPassword.getText().toString() == null)
                        Toast.makeText(getApplicationContext(), "Enter Your PHone Number", Toast.LENGTH_LONG).show();
                    else {

                        verifySignIncode(codeSent, editTextPassword.getText().toString());
                    }
                }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }


    public void verifySignIncode(String verifyNUmber, String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyNUmber, otp);
        signInWithPhoneAuthCredential(credential);

    }


    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User Signed IN", Toast.LENGTH_LONG).show();
                            final FirebaseUser user = task.getResult().getUser();

                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    check(user);


                                }
                            });


                            // ...
                        } else {


                        }
                    }
                });
    }


    public void check(final FirebaseUser user) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Vendor").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        value = "Vendor";

                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        firestore.collection(value).document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Long a = (Long) documentSnapshot.get("type");
                                db.collection("Customer").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                value = "Customer";
                                                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                                firestore.collection(value).document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this)
                                                                .setTitle("Where would you like to go")
                                                                .setMessage("Please select where you want to head")
                                                                .setPositiveButton("Vendor", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {


                                                                        Intent intent = new Intent(LoginScreen.this, VendorAuth.class);
                                                                        intent.putExtra("value", "1");
                                                                        startActivity(intent);

                                                                    }
                                                                }).setNegativeButton("Customer", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        Intent intent = new Intent(LoginScreen.this, Activity_MAIN.class);
                                                                        startActivity(intent);

                                                                    }
                                                                });
                                                        AlertDialog dialog = builder.create();
                                                        dialog.show();


                                                    }
                                                });
                                            } else {
                                                Intent intent = new Intent(LoginScreen.this, VendorAuth.class);
                                                intent.putExtra("value", "1");
                                                startActivity(intent);
                                            }


                                        }


                                    }
                                });

                            }
                        });
                    } else {


                        db.collection("Customer").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        value = "Customer";
                                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                        firestore.collection(value).document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                Long a = (Long) documentSnapshot.get("type");
                                                if (a == 0) {
                                                    Intent intent = new Intent(LoginScreen.this, Activity_MAIN.class);
                                                    startActivity(intent);

                                                } else if (a == 1) {
                                                    Intent intent = new Intent(LoginScreen.this, VendorAuth.class);
                                                    intent.putExtra("value", "1");
                                                    startActivity(intent);
                                                }


                                            }
                                        });
                                    }


                                }


                            }
                        });


                    }
                }


            }
        });


    }

    public void verifyPhoneNumber(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + "91" + number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
        Toast.makeText(getApplicationContext(), "phone", Toast.LENGTH_LONG).show();

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

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
                Log.e("verify", e.getMessage());
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.e("verify", e.getMessage());
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
            Log.e("verify", verificationId);
            codeSent = verificationId;

            editTextPassword.setHint("Eg-12345");
            editTextPassword.setText("");
            tv2.setText("Enter Your Otp here");
            numberSent = false;
            signin.setText("CHECK OTP");
            Toast.makeText(getApplicationContext(), "Enter the OTP ", Toast.LENGTH_SHORT).show();


            // ...
        }
    };

    @Override
    public void onBackPressed() {

    }
}