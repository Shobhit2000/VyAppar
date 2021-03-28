package com.example.gov.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.gov.Activity.PhoneAuth;
import com.example.gov.ModalClasses.user;
import com.example.gov.R;

public class RegisterActivity extends AppCompatActivity {

    int type;
    ScrollView scrollView;
    Button button;
    com.example.gov.ModalClasses.user user;
    EditText name,dob,age,phNumber,address,emailId;
    Bundle bundle;
    Boolean a=true;

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.service:
                if (checked){
                    type=1;
                    a=true;
                    scrollView.setVisibility(View.VISIBLE);}


                    break;
            case R.id.customer:
                if (checked){
                    scrollView.setVisibility(view.INVISIBLE);
        type=0;
        a=false;
        Toast.makeText(getApplicationContext(),"Lets go",Toast.LENGTH_LONG).show();}

                    break;


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        scrollView=findViewById(R.id.scroll);
        button=findViewById(R.id.button);
        bundle=new Bundle();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=findViewById(R.id.name);
                dob=findViewById(R.id.dob);
                age=findViewById(R.id.age);
                phNumber=findViewById(R.id.pn);
                address=findViewById(R.id.address);
                emailId=findViewById(R.id.emailId);

                user=new user(name.getText().toString(),dob.getText().toString(),age.getText().toString(),phNumber.getText().toString(),emailId.getText().toString(),address.getText().toString(),type);

                Intent intent=new Intent(getApplicationContext(), PhoneAuth.class);
                intent.putExtra("values",user);
                intent.putExtra("type",a);
                startActivity(intent);

            }
        });


    }
}