package com.example.moviapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.moviapp.Models.User;
import com.example.moviapp.databinding.ActivitySignUpBinding;
import com.example.moviapp.utils.MyUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init parameter
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://moviapp-3d13c-default-rtdb.asia-southeast1.firebasedatabase.app/");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account.");

        CharSequence email = binding.edtEmail.getText();
        CharSequence password = binding.edtPassword.getText();
        CharSequence confirmPassword = binding.edtConfirmPassword.getText();

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyUtils.isValidEmail(email)) {
                    if (!password.toString().isEmpty() || !confirmPassword.toString().isEmpty() || !email.toString().isEmpty()) {
                        if (password.toString().equals(confirmPassword.toString())) {
                            binding.txtWarning.setVisibility(View.GONE);
                            progressDialog.show();
                            auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                User user = new User(email.toString());

                                                String idUser = task.getResult().getUser().getUid();
                                                user.setUserID(idUser);

                                                database.getReference().child("Users").child(idUser).setValue(user);

                                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                                Toast.makeText(SignUpActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {
                            binding.txtWarning.setVisibility(View.VISIBLE);
                            binding.txtWarning.setText(R.string.similarPassword);
                        }
                    } else {
                        binding.txtWarning.setVisibility(View.VISIBLE);
                        binding.txtWarning.setText(R.string.fillBlank);
                    }
                } else {
                    binding.txtWarning.setVisibility(View.VISIBLE);
                    binding.txtWarning.setText(R.string.wrongFormatEmail);
                }
            }
        });

        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LauncherActivity.class));
            }
        });

        binding.txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

    }
}