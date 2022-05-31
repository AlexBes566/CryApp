package com.example.cryapp.Adapters;

public class Contacts {
    private String contact_name, contact_blockchain_address;

    public Contacts(String contact_name, String contact_blockchain_address){
        this.contact_name = contact_name;
        this.contact_blockchain_address = contact_blockchain_address;
    }

    public String getContact_name() {return this.contact_name;}

    public void setContact_name() {this.contact_name = contact_name;}

    public String getContact_blockchain_address() {return this.contact_blockchain_address;}

    public void setContact_blockchain_address() {this.contact_blockchain_address = contact_blockchain_address;}
}
