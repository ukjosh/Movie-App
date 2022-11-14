package com.example.lastestmovieapp;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEt, emailEt, passwordEt;
    private FirebaseAuth mAuth;
    private Uri imageUrl = null;
    CircleImageView uploadImage;
    Button createAccount;
    private ProgressDialog progressDialog;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        nameEt = findViewById(R.id.signNameEt);
        emailEt = findViewById(R.id.signEmailEt);
        passwordEt = findViewById(R.id.signPassEt);

        uploadImage = findViewById(R.id.uploadImage);

        uploadImage.setOnClickListener(v ->
            imagePickerLauncher.launch("image/*")
        );


        createAccount = findViewById(R.id.createAccountBtn);

        createAccount.setOnClickListener(v ->
            createAccount()
        );


    }


    ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult
            (new ActivityResultContracts.GetContent(),
            uri -> {
                // Handle the returned Uri
                imageUrl = uri;
            });

    public void createAccount() {
        String name = nameEt.getText().toString();
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();


        if(TextUtils.isEmpty(name)){
            nameEt.setError("Enter Name!");
            return;
        }

        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter Email!");
            return;
        }

        if(TextUtils.isEmpty(password)){
            passwordEt.setError("Enter Password!");
            return;
        }


        if(email.matches("admin@movieapp.com")){
            emailEt.setError("Email Used By Admin!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        final FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        user.sendEmailVerification()
                                .addOnCompleteListener( verificationTask -> {

                                    if (verificationTask.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this,
                                                "Verification email sent to " + user.getEmail(),
                                                Toast.LENGTH_SHORT).show();
                                        String UserId = user.getUid();

                                        if (bitmap == null){
                                            bitmap = BitmapFactory.decodeResource(this.getResources(),
                                                    R.drawable.profile_avatar);
                                        }
                                        uploadFile(bitmap, name, email, UserId);
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }).addOnFailureListener(exception ->
                        Toast.makeText(SignUpActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show());

    }

    private void uploadFile(Bitmap bitmap, String name, String email, String UserId) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String random = UUID.randomUUID().toString();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images/" + random + ".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        final UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot ->

                uploadTask.continueWithTask(task -> {

                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return storageRef.getDownloadUrl();

                }).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        imageUrl = task.getResult();
                        UserAccount user = new UserAccount(name, email, UserId, imageUrl.toString());
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(UserId)
                                .setValue(user);
                        Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        nameEt.setText(null);
                        emailEt.setText(null);
                        passwordEt.setText(null);

                        Glide.with(getApplicationContext()).load(R.drawable.profile_avatar).into(uploadImage);
                        progressDialog.dismiss();
                    }

                })).addOnFailureListener(exception -> {

                    Toast.makeText(SignUpActivity.this, "failed"+exception, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }).addOnProgressListener(snapshot -> {

                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading " + progress + "%");

                });

    }

}