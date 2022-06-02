package com.example.cryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.cryapp.Adapters.Contacts;

import java.util.ArrayList;

public class ContactsListActivity extends AppCompatActivity {

    private ArrayList<Contacts> contactsArrayList = new ArrayList<Contacts>();
    private SQLDB sqldb;
    private String contact, block_address;
    private ImageButton back_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        sqldb = new SQLDB(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //RecyclerView recyclerView = findViewById(R.id.contacts_recyclerList);
        ContactsAdapter.OnContactsClickListener contactsClickListener = new ContactsAdapter.OnContactsClickListener() {
            @Override
            public void onContactsClick(Contacts contacts, int position, String identificator, String contact_name_text, String contact_blockchain_address_text) {
                Intent intent = new Intent(ContactsListActivity.this, ContactInformationActivity.class);
                intent.putExtra("id", identificator);
                intent.putExtra("login", contact_name_text);
                intent.putExtra("blockchain", contact_blockchain_address_text);
                startActivity(intent);
            }
        };

        //ContactsAdapter adapter = new ContactsAdapter(this, setInitialData(), contactsClickListener);
        //recyclerView.setAdapter(adapter);
        RecyclerView recyclerView = findViewById(R.id.contacts_recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactsListActivity.this));
        recyclerView.setAdapter(new ContactsAdapter(ContactsListActivity.this,setInitialData(),contactsClickListener));

        back_main = findViewById(R.id.back_main);
        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList<Contacts> setInitialData(){

        SQLiteDatabase db = sqldb.getWritableDatabase();

        //Проверка на то, что контакт добавлен в базу данных.
        Cursor data = sqldb.read_data_base(db);
        if(data.moveToFirst()){
            String a1 ="";
            int idColIndex = data.getColumnIndex("id");
            int nameColIndex = data.getColumnIndex("LOGIN");
            int emailColIndex = data.getColumnIndex("BLOCKCHAIN_ADDRESS");
            do {
                contactsArrayList.add(new Contacts(data.getString(idColIndex), data.getString(nameColIndex), data.getString(emailColIndex)));
            } while (data.moveToNext());
        }
        return contactsArrayList;
    }
}