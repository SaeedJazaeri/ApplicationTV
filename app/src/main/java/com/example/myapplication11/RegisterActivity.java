package com.example.myapplication11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private ImageButton btnGoBack;
    private EditText firstNameRegister, lastNameRegister,
                     phoneNumberRegister, emailRegister,
                     passwordRegister, repeatPasswordRegister;
    private CheckBox termsAndCondRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view) {
                if (checkTextsCorrectness()) {
                        MainCallAPIRegisterAsyncTask task = new MainCallAPIRegisterAsyncTask(
                                firstNameRegister.getText().toString(),
                                lastNameRegister.getText().toString(),
                                phoneNumberRegister.getText().toString(),
                                emailRegister.getText().toString(),
                                passwordRegister.getText().toString(),
                                repeatPasswordRegister.getText().toString(),
                                emailRegister.getText().toString()
                                );
                        task.execute();
                    }
                }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

    }

    private void initView(){
        btnRegister = findViewById(R.id.et_register_button);
        btnGoBack = findViewById(R.id.btn_goBack);
        firstNameRegister = findViewById(R.id.et_first_name);
        lastNameRegister = findViewById(R.id.et_last_name);
        phoneNumberRegister = findViewById(R.id.et_phone_number);
        emailRegister = findViewById(R.id.et_email);
        passwordRegister = findViewById(R.id.et_password);
        repeatPasswordRegister = findViewById(R.id.et_password2);
        termsAndCondRegister = findViewById(R.id.et_terms_and_conditions);
    }

    private boolean checkTextsCorrectness(){
        if(firstNameRegister.getText().toString().equals("")) {
            Toast.makeText(this, "لطفا نام خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(lastNameRegister.getText().toString().equals("")) {
            Toast.makeText(this, "لطفا نام خانوادگی خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(emailRegister.getText().toString().equals("")) {
            Toast.makeText(this, "لطفا ایمیل خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(phoneNumberRegister.getText().toString().equals("")) {
            Toast.makeText(this, "لطفا شماره تلفن همراه خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordRegister.getText().toString().equals("")) {
            Toast.makeText(this, "لطفا رمزعبور خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(repeatPasswordRegister.getText().toString().equals("")) {
            Toast.makeText(this, "لطفا تایید پسوورد خود را وارد کنید", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!repeatPasswordRegister.getText().toString().equals(passwordRegister.getText().toString())) {
            Toast.makeText(this, "رمزعبور شما با تکرار آن یکسان نیست! مجددا وارد کنید.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!termsAndCondRegister.isChecked()) {
            Toast.makeText(this, "شما باید با قوانین و مقررات موافقت کنید!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void ackActivity() {
        Intent intent = new Intent(RegisterActivity.this, RegisterAcknowledgeActivity.class);
        intent.putExtra("firstName", firstNameRegister.getText().toString());
        intent.putExtra("lastName", lastNameRegister.getText().toString());
        intent.putExtra("phoneNumber", phoneNumberRegister.getText().toString());
        intent.putExtra("email", emailRegister.getText().toString());
        intent.putExtra("password", passwordRegister.getText().toString());
        intent.putExtra("passwordRepeat", repeatPasswordRegister.getText().toString());
        intent.putExtra("username", emailRegister.getText().toString());

        startActivity(intent);
        finish();
    }

    private void unable2Ack() {
        Toast.makeText(this, "در ثبتنام شما مشکلی پیش اومده! لطفا بعدا مجددا تلاش کنید.", Toast.LENGTH_SHORT).show();
    }


    private class MainCallAPIRegisterAsyncTask extends CallAPIRegisterAsyncTask  {

        MainCallAPIRegisterAsyncTask(String firstName, String lastName,
                                     String phoneNumber, String email,
                                     String password, String passwordRepeat,
                                     String username) {
            super(firstName, lastName, phoneNumber, email, password, passwordRepeat, username);
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