package com.example.cryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cryapp.Adapters.Contacts;

public class ContactInformationActivity extends AppCompatActivity {

    private TextView name_contact_text, blockchain_address_text;
    private Button contact_delete;
    SQLDB sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        sqldb = new SQLDB(this);
        findContact();

        contact_delete = findViewById(R.id.contact_delete);
        contact_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("id");
                System.out.println(id);
                SQLiteDatabase db = sqldb.getWritableDatabase();
                sqldb.delete_row_data_base(db, id);
                sqldb.close();
                Intent intent = new Intent(ContactInformationActivity.this, ContactsListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findContact() {
        name_contact_text = findViewById(R.id.name_contact_text);
        blockchain_address_text = findViewById(R.id.blockchain_address_text);
        String login = getIntent().getStringExtra("login");
        name_contact_text.setText(login);
        String blockchain = getIntent().getStringExtra("blockchain");
        blockchain_address_text.setText(blockchain);
    }
}