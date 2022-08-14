package com.example.myapplication11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private ImageButton btnGoBack;
    private EditText usernameLogin, passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainCallAPILoginAsyncTask task = new MainCallAPILoginAsyncTask(
                        usernameLogin.getText().toString(),
                        passwordLogin.getText().toString());
                task.execute();
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private void initView(){
        btnLogin = findViewById(R.id.btn_login);
        btnGoBack = findViewById(R.id.btn_goBack);
        usernameLogin = findViewById(R.id.loginEmail);
        passwordLogin = findViewById(R.id.loginPassword);

    }



    private void ackActivity() {
        Intent intent = new Intent(LoginActivity.this, LoginAcknowledgeActivity.class);
        intent.putExtra("username", usernameLogin.getText().toString());
        intent.putExtra("password", passwordLogin.getText().toString());
        startActivity(intent);
        finish();
    }

    private void unable2Ack() {
        Toast.makeText(this, "نام‌کاربری یا رمز عبور درست نمی‌باشد!!", Toast.LENGTH_SHORT).show();
    }

    private class MainCallAPILoginAsyncTask extends CallAPILoginAsyncTask  {

        MainCallAPILoginAsyncTask(String username, String password) {
            super(username, password);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("ALI", result);
            if(isAck) {
                ackActivity();
            } else {
                unable2Ack();
            }
        }
    }


}