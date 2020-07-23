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

public class Register extends AppCompatActivity {
    EditText regUserEmail,regUserPassword,regUserConfirmPassword;
    Button regButton,regLoginButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUserPassword = findViewById(R.id.regUserPassword);
        regUserConfirmPassword = findViewById(R.id.regUserConfirmPassword);
        regUserEmail = findViewById(R.id.regUserEmail);
        regButton = findViewById(R.id.regButton);
        regLoginButton = findViewById(R.id.regLoginButton);
        fAuth = FirebaseAuth.getInstance();

        regLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });



        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regUserEmail.getText().toString().trim();
                String password = regUserPassword.getText().toString().trim();
                String confirmPassword = regUserConfirmPassword.getText().toString().trim();

                if (fAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                if(! password.equals(confirmPassword)){
                    regUserConfirmPassword.setError("Password doesn't match");
                    return;
                }
                if(password.length()<6){
                    regUserPassword.setError("Password should at least be 6 charecters long");
                    return;
                }
                if ( TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                    regUserEmail.setError("Please enter a valid email address");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Profile.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Failed due to"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}