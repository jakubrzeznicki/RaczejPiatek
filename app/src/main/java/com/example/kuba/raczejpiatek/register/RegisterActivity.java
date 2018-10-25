package com.example.kuba.raczejpiatek.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuba.raczejpiatek.R;
import com.example.kuba.raczejpiatek.login.LoginActivity;
import com.example.kuba.raczejpiatek.main.MainActivity;
import com.example.kuba.raczejpiatek.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nickNameEditText;
    private EditText passwordEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        init();

        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void init() {
        nickNameEditText = (EditText) findViewById(R.id.editTextNickName);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        phoneEditText = (EditText) findViewById(R.id.editTextPhone);
        registerButton = (Button) findViewById(R.id.buttonRegister);
    }


    private void registerUser(){
        final String email = emailEditText.getText().toString().trim();
        final String password  = passwordEditText.getText().toString().trim();
        final String username  = nickNameEditText.getText().toString().trim();
        final String phone  = phoneEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Podaj adres email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Podaj haslo",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Podaj nazwe uzytkownika",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Podaj numer telefony",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Trwa proces rejestracji");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(username,email,password,phone);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this,"Rejestracja udana",Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this,"Blad rejestracji",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegisterActivity.this,"Błąd rejestracji",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

}
