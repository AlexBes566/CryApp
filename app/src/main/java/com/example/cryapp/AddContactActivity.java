package com.example.cryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

public class AddContactActivity extends AppCompatActivity {

    private EditText name_contact_edit, blockchain_address_edit;
    private Button add_contact;
    private ImageButton scan_qr;
    private TextView vyhod;
    SQLDB sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_add_contact);
        sqldb = new SQLDB(this);

        name_contact_edit = findViewById(R.id.name_contact_edit);
        blockchain_address_edit = findViewById(R.id.blockchain_address_edit);
        scan_qr = findViewById(R.id.scan_qr);
        add_contact = findViewById(R.id.add_contact);
        vyhod = findViewById(R.id.vyhod);

        scan_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(AddContactActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }

        });

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_contact();
                //Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });

    }

    private void create_contact() {
        String contact = name_contact_edit.getText().toString().trim();
        String block_address = blockchain_address_edit.getText().toString().trim();

        SQLiteDatabase db = sqldb.getWritableDatabase();
        sqldb.insertDB(db, contact, block_address);

        //Проверка на то, что контакт добавлен в базу данных.
        Cursor data = sqldb.read_data_base(db);
        if(data.moveToFirst()){
            String a1 ="";
            int idColIndex = data.getColumnIndex("id");
            int nameColIndex = data.getColumnIndex("LOGIN");
            int emailColIndex = data.getColumnIndex("BLOCKCHAIN_ADDRESS");
            do {
                String all_data = "ID = " + data.getInt(idColIndex) +
                        ", name = " + data.getString(nameColIndex) +
                        ", email = " + data.getString(emailColIndex);
                a1 = a1 + all_data +  "/n";
            } while (data.moveToNext());
            vyhod.setText(a1);
        }
    }
}