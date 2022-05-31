package com.example.cryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.cryapp.Adapters.Contacts;
import com.example.cryapp.Adapters.ContactsAdapter;
import com.example.cryapp.Adapters.NotificationsAdapter;
import com.example.cryapp.Adapters.Transaction;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    ArrayList<Contacts> contactsArrayList = new ArrayList<Contacts>();
    SQLDB sqldb;
    String contact, block_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        sqldb = new SQLDB(this);

        RecyclerView recyclerView = findViewById(R.id.contacts_recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
        recyclerView.setAdapter(new ContactsAdapter(ContactsActivity.this,setInitialData()));
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
                contactsArrayList.add(new Contacts(data.getString(nameColIndex), data.getString(emailColIndex)));
            } while (data.moveToNext());
        }
        return contactsArrayList;
    }
}