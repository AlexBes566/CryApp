package com.example.cryapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

public class RegistrationActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button registration_btn, wallet_creation;
    private EditText login, password_app, password_wallet;
    private TextView directory_wallet;
    private String walletDirectory, walletName, login_string, password_app_string, password_wallet_string;
    final String LOG_TAG = "myLogs";
    private Context context;
    SLData value = new SLData(this);
    public setupBounceCastle castle = new setupBounceCastle();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        setContentView(R.layout.activity_registration);
        castle.setBouncyCastle();
        setGlobalVariables();
        setOnclick();
    }

    /* В setGlobalVariables происходит нахождение всех объектов разметки activity_registration.xml*/

    private void setGlobalVariables() {
        walletDirectory = getFilesDir().getAbsolutePath();
        registration_btn = findViewById(R.id.registration_btn);
        wallet_creation = findViewById(R.id.wallet_creation);
        login = findViewById(R.id.login);
        password_app = findViewById(R.id.password_app);
        password_wallet = findViewById(R.id.password_wallet);
        directory_wallet = findViewById(R.id.directory_wallet);
        progressBar = findViewById(R.id.progressBar);
    }

    /* В setOnclick происходит обработка нажатия кнопок RegistrationActivity. При нажатии кнопки wallet_creation происходит считывание данных с введенных полей EditText,
    *  далее полученные данные сохраняются и происходит формирование блокчейн кошелька  на основе введенных данных. */

    private void setOnclick() {
        wallet_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_app_string = password_app.getText().toString().trim();
                login_string = login.getText().toString().trim();
                password_wallet_string = password_wallet.getText().toString().trim();
                if (password_app_string.isEmpty() || login_string.isEmpty() || password_wallet_string.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Пожалуйста, введите все данные", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        value.save_data("password_app_string",password_app_string);
                        value.save_data("login" ,login_string);
                        value.save_data("password_wallet" , password_wallet_string);
                        Registration_in_blockchain rigid = new Registration_in_blockchain();
                        rigid.execute(password_wallet_string,walletName);
                    } catch (Exception e) {
                        Log.d(LOG_TAG, e.getMessage());
                        Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String walletPassword = password_wallet.getText().toString().trim();
                    String enterName = walletName;
                    if (walletPassword.isEmpty() || enterName.isEmpty()) {
                        Toast.makeText(RegistrationActivity.this, "Пожалуйста,введите все данные", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* В Registration_in_blockchain происходит создание кошелька в параллельном поток */

    private class Registration_in_blockchain extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(ProgressBar.VISIBLE);
            Toast.makeText(RegistrationActivity.this, "Подождите, кошелек создается", Toast.LENGTH_LONG).show();
            wallet_creation.setVisibility(View.INVISIBLE);
            registration_btn.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... password_wallet_string) {
            try {
                int myProgress = 0;
                String wallet_credentials = value.load_data("password_wallet");
                walletName = WalletUtils.generateNewWalletFile(wallet_credentials, new File(walletDirectory));
                publishProgress(myProgress);
            } catch (CipherException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
                e.printStackTrace();
            }
            return walletName;
        }

        protected void onPostExecute (String walletName){
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            registration_btn.setVisibility(View.VISIBLE);
            wallet_creation.setVisibility(View.VISIBLE);
            super.onPostExecute(String.valueOf(walletName));
            Toast.makeText(RegistrationActivity.this, "Кошелек создан. Нажмите кнопку регистрации.", Toast.LENGTH_LONG).show();
            value.save_data("walletName" ,walletName);
            value.save_data("walletDirectory" ,walletDirectory);
            directory_wallet.setText(String.format("Файл кошелька хранится: %s/%s", walletDirectory, walletName));
        }

    }
}
