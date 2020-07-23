package com.SAP.studyd8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    EditText university,name;
    Button finishButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.profileName);
        university =findViewById(R.id.profileUniversity);

        fStore = FirebaseFirestore.getInstance();
        fAuth =FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        userId=fAuth.getCurrentUser().getUid();
        finishButton = findViewById(R.id.finishButton);
        try {
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user_name = name.getText().toString().trim();
                    String user_university = university.getText().toString().trim();
                    DocumentReference documentReference = fStore.collection("users").document(userId);

                    Map<String, Object> user_data = new HashMap<>();
                    user_data.put("name", user_name);
                    user_data.put("university", user_university);
                    documentReference.set(user_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}