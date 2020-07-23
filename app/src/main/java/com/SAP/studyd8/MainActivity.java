package com.SAP.studyd8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class MainActivity extends AppCompatActivity {
    TextView welcomeText;
    FirebaseAuth fAuth;
    Button signoutButton;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            welcomeText = findViewById(R.id.welcomeText);
            fAuth = getInstance();
            signoutButton = findViewById(R.id.signoutButton);

            FirebaseUser user = fAuth.getCurrentUser();
            if(fAuth.getCurrentUser()==null){
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
            signoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut( );
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            });
            assert user != null;
            welcomeText.setText("hello " + user.getEmail());
        }
        catch (Exception e){
//            Toast.makeText(getApplicationContext(),"Unfortunately an error occurred "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}