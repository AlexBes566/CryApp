package com.example.cryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryapp.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Contacts> contactsList;

    public ContactsAdapter(Context context, List<Contacts> contactsList){
        this.contactsList = contactsList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_contacts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contacts contacts = contactsList.get(position);
        holder.contact_name.setText(contacts.getContact_name());
        holder.contact_blockchain_address.setText(contacts.getContact_blockchain_address());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView contact_name, contact_blockchain_address;

       ViewHolder(View view) {
            super(view);
            contact_name = view.findViewById(R.id.contact_name);
            contact_blockchain_address = view.findViewById(R.id.contact_blockchain_address);
       }
    }
}
