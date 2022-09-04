package com.example.myapplication11;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAcknowledgeActivity extends AppCompatActivity {

    TextView loginAckOptNumber;
    Button loginAckBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acknowledge);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        initView();

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        loginAckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AckCallAPILoginAsyncTask task = new AckCallAPILoginAsyncTask(
                        username,
                        password,
                        loginAckOptNumber.getText().toString());
                task.execute();
            }
        });

    }

    private void initView(){
        loginAckOptNumber = findViewById(R.id.login_tv_opt_number);
        loginAckBtn = findViewById(R.id.btn_login);
    }

    private void ackActivity(String token, String username,
                             String first_name, String last_name,
                             String email, String phone_number) {
        Toast.makeText(this, "ورود شما با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginAcknowledgeActivity.this, HomePageActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("username", username);
        intent.putExtra("first_name", first_name);
        intent.putExtra("last_name", last_name);
        intent.putExtra("email", email);
        intent.putExtra("phone_number", phone_number);
        startActivity(intent);
        finish();
    }

    private void unable2Ack() {
        Toast.makeText(this, "کد صحت سنجی صحیح نمی‌باشد!!", Toast.LENGTH_SHORT).show();
    }

    private class AckCallAPILoginAsyncTask extends CallAPILoginAsyncTask {
        private String opt_number;

        AckCallAPILoginAsyncTask(String username, String password, String opt_number) {
            super(username, password);
            this.opt_number = opt_number;
        }

        @Override
        protected void setVariables(JSONObject jsonRequest) throws JSONException {
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            jsonRequest.put("opt_number", opt_number);
        }

        @Override
        protected void onPostExecute(String result) {

            if(isAck) {
                String  token,
                        username,
                        first_name,
                        last_name,
                        email,
                        phone_number;
                char qout = '"';
                result = result.replaceAll("[{}\"]","");
                result = result.replaceAll(",", "\n");
                String[] arrOfStr = result.split("\n");

                token = arrOfStr[1];
                username = arrOfStr[2];
                first_name = arrOfStr[3];
                last_name = arrOfStr[4];
                email = arrOfStr[5];
                phone_number = arrOfStr[6];
                ackActivity(token, username,
                            first_name, last_name,
                            email, phone_number);
            } else {
                unable2Ack();
            }
        }
    }


}