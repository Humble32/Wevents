package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Lastname;
    private EditText Email;
    private EditText Password;
    private EditText Confirm;
    private Button RegBtn;
    //private Toolbar Toolbar;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name=findViewById(R.id.reg_name);
        Lastname=findViewById(R.id.reg_last);
        Email=findViewById(R.id.reg_email);
        Password=findViewById(R.id.reg_password);
        Confirm=findViewById(R.id.reg_confirm);
        RegBtn=findViewById(R.id.reg_btn);
/*      Toolbar=findViewById(R.id.reg_toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        Auth=FirebaseAuth.getInstance();

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= Name.getText().toString();
                String lastname= Lastname.getText().toString();
                String email= Email.getText().toString();
                String password= Password.getText().toString();
                String confirm= Confirm.getText().toString();
                if(!name.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirm.isEmpty()){
                    if(password.compareTo(confirm)==0) {
                        if (isValid(password)) {
                            Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Password must content at least 8 characters, having letter,digit and special symbol!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Password and Confirm password didn't match!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"Please fill empty field",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isValid(String passwordhere){
        int f1=0, f2=0,f3=0;
        if(passwordhere.length()<8){
            return false;
        } else {
            for (int p=0; p<passwordhere.length();p++){
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1=1;
                }
            }
            for (int r=0; r<passwordhere.length();r++){
                if(Character.isDigit(passwordhere.charAt(r))){
                    f2=1;
                }
            }
            for (int s=0; s<passwordhere.length();s++){
                char c=passwordhere.charAt(s);
                if(c>=33&&c<=46||c==64){
                    f3=1;
                }
            }
            if (f1==1 && f2==1 && f3==1)
                return true;
            return false;
        }
    }
    public void SignIn(View view) {
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }


}