package com.example.cryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.web3j.abi.datatypes.Int;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import jnr.ffi.annotations.In;

public class LoginActivity extends AppCompatActivity {

    public Button login_btn,registration_btn;
    public EditText password;
    public Web3j web3j;
    public SLData value = new SLData(this);
    public setupBounceCastle castle = new setupBounceCastle();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        castle.setBouncyCastle();
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        registration_btn = findViewById(R.id.registration_btn);
        btn_activation();
    }

    /* В классе ниже происходит обработка нажатия кнопок LoginActivity. При нажатии кнопки registration_btn происходит переход на страницу регистрации, при нажатии login_btn
    * происходит проверка введенного пароля и если пароль корректен, подключение к блокчейн системе и переход к основной активности MainActivity */

    public void btn_activation() {
        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //String saved_text = value.load_data("password_app_string");
                    File f = new File(
                            "/data/data/com.example.cryapp/shared_prefs/data_source.xml");
                    String password_string = password.getText().toString().trim();
                    if (!f.exists()){
                        Toast.makeText(LoginActivity.this, "Пожалуйста, пройдите регистрацию.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password_string.isEmpty()){
                            Toast.makeText(LoginActivity.this, "Пожалуйста, введите пароль", Toast.LENGTH_SHORT).show();
                        } else {
                            String saved_text = value.load_data("password_app_string");
                            if (password_string.equals(saved_text)){
                                ConnectToBlockchain regedit = new ConnectToBlockchain();
                                regedit.execute();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Пожалуйста, введите пароль", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /* В классе ниже происходит обработка подключения к блокчейн системе */

    public class ConnectToBlockchain extends AsyncTask<String, Integer, Web3ClientVersion> {

        @Override
        protected void onPreExecute(){
            Toast.makeText(LoginActivity.this, "Подождите, происходит подключение к блокчейн системе", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Web3ClientVersion doInBackground(String... password_wallet_string) {
            web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/f5ed25abb81d4229b9763adcf94f4181"));
            Web3ClientVersion clientVersion = null;
            try {
                clientVersion = web3j.web3ClientVersion().sendAsync().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return clientVersion;
        }

        protected void onPostExecute (Web3ClientVersion clientVersion){
            if (!clientVersion.hasError()) {
                Toast.makeText(LoginActivity.this, "Подключено", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, clientVersion.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}