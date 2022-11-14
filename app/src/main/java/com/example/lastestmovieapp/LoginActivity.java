package com.example.lastestmovieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEt, passwordEt;
    private Button loginBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.loginEmailEt);
        passwordEt = findViewById(R.id.loginPassEt);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        initClicks();
    }

    private void initClicks() {
        loginBtn.setOnClickListener(this::loginAccount);
        signUpBtn.setOnClickListener(this::gotoSignUp);
    }

    public void loginAccount(View view) {
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter Email!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Enter Password!");
            return;
        }

        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {


                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            assert user != null;
                            if (user.isEmailVerified()) {
                                String userId = Objects.requireNonNull(task.getResult().getUser()).getUid();

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(userId)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    Utils.currentUser = snapshot.getValue(UserAccount.class);
                                                    progressdialog.dismiss();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else{
                                                    progressdialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "No User Found", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                progressdialog.dismiss();
                                                Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                // email is not verified, so just prompt the message to the user and restart this activity.
                                // NOTE: don't forget to log out the user.
                                Toast.makeText(LoginActivity.this, "Email Not Verified", Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();
                                progressdialog.dismiss();
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);

                            }


                        }
                    }).addOnFailureListener(exception -> Toast.makeText(LoginActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show());

    }


    public void gotoSignUp(View v) {

            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);

    }
}