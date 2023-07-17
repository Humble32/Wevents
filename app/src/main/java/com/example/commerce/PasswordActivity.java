package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private FirebaseAuth Auth;
    private Button Send;
    private EditText EmailS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Send=findViewById(R.id.send);
        EmailS=findViewById(R.id.send_email);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailS.getText().toString();
                if (!email.isEmpty()) {

                    Auth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordActivity.this, "Mail send", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PasswordActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(PasswordActivity.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}