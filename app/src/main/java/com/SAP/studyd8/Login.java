package com.SAP.studyd8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText loginUserEmail,loginUserPassword;
    Button loginRegisterButton,loginButton;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginUserPassword = findViewById(R.id.loginUserPassword);
        loginUserEmail = findViewById(R.id.loginUserEmail);
        loginRegisterButton = findViewById(R.id.loginRegisterButton);
        loginButton = findViewById(R.id.loginButton);
        fAuth = FirebaseAuth.getInstance();
        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginUserEmail.getText().toString().trim();
                String password = loginUserPassword.getText().toString().trim();
                if (fAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                if ( TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                    loginUserEmail.setError("Please enter a valid email address");
                    return;
                }
                if(password.length()<1){
                    loginUserPassword.setError("Please enter you password");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login failed due to "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}