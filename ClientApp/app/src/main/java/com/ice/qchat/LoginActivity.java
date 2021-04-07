package com.ice.qchat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        usernameEditText.setText( sp.getString("username", "") );
        passwordEditText.setText( sp.getString("password", "") );

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (username.length()>=2 && password.length()>=2) {
                    startActivity(new Intent(LoginActivity.this,com.ice.qchat.MainActivity.class));
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor edit = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                    edit.putString("username", username);
                    edit.putString("password", password);
                    edit.commit();
                    Tool.connect(username);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(getApplicationContext(), username + "|" + password + "\n" +
                            LoginActivity.this.getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}