package com.example.cryapp.Adapters;

import android.widget.ImageView;

public class Transaction {
    private String username, amount_transaction;
    private int image_transaction;

    public Transaction(String username, String amount_transaction, int image_transaction){
        this.username = username;
        this.amount_transaction = amount_transaction;
        this.image_transaction = image_transaction;
    }

    public String getUsername(){ return this.username; }

    public void setUsername(){ this.username = username; }

    public String getAmount_transaction(){ return this.amount_transaction; }

    public void setAmount_transaction(){ this.amount_transaction = amount_transaction; }

    public int getImage_transaction(){ return this.image_transaction; }

    public void setimage_transaction(){ this.image_transaction = image_transaction; }
}
