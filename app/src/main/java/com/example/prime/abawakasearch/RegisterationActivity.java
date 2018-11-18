package com.example.prime.abawakasearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterationActivity extends AppCompatActivity {
        private FirebaseUser currentUser;
        private FirebaseAuth mAuth;
        private DatabaseReference database;
        private DatabaseReference rootRef;

        private Button createAcctButton;
        private EditText userEmail, userPassword;
        private TextView alreadyhaveanacct;
        private ProgressDialog progressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registeration);

            database = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            rootRef = database;

            initializeFields();
            alreadyhaveanacct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RegisterationActivity.this, LoginActivity.class));
                }
            });
            createAcctButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createNewAcct();
                }
            });
        }

        private void createNewAcct() {
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                userEmail.setError("Please Enter Emaill...");
            }
            if (TextUtils.isEmpty(password)) {
                userPassword.setError("Please Enter Password");
            } else {
//            progressDialog.setTitle("Creating New Account...");
//            progressDialog.setMessage("Pls wait");
//            progressDialog.setCanceledOnTouchOutside(true);
//            progressDialog.show();

                mAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    String currentUserId = mAuth.getCurrentUser().getUid();

                                    rootRef.child("Business").child(currentUserId).push().setValue("");
                                    Log.d("pushed", "registered to database");
                                    takeUserToMainActivity();

                                    Toast.makeText(RegisterationActivity.this,
                                            "Registration Successful", Toast.LENGTH_SHORT).show();
//                                    takeUserToMainActivity();
                                    Log.d("change activity", "started main activity successfully");
//                                progressDialog.dismiss();
                                } else {
                                    String message = task.getException().toString();
                                    Toast.makeText(RegisterationActivity.this,
                                            "Sorry " + message, Toast.LENGTH_SHORT).show();
//                                progressDialog.dismiss();
                                }
                            }
                        });
//
            }
        }

        private void takeUserToMainActivity() {
            Intent intent = new Intent(RegisterationActivity.this,
                    MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        private void initializeFields() {
            createAcctButton = (Button) findViewById(R.id.register_button);
            userEmail = (EditText) findViewById(R.id.register_email);
            userPassword = findViewById(R.id.register_password);
            alreadyhaveanacct = findViewById(R.id.back_to_login);

        }
    }


