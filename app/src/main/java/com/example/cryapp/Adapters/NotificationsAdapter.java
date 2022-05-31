package com.example.cryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryapp.R;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Transaction> username;

    public NotificationsAdapter(Context context, List<Transaction> username){
        this.username = username;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        Transaction transaction = username.get(position);
        holder.username.setText(transaction.getUsername());
        holder.amount_transaction.setText(transaction.getAmount_transaction());
        holder.image_transaction.setImageResource(transaction.getImage_transaction());
    }

    @Override
    public int getItemCount() {
        return username.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image_transaction;
        final TextView username, amount_transaction;
        ViewHolder(View view){
            super(view);
            username = view.findViewById(R.id.username);
            amount_transaction = view.findViewById(R.id.amount_transaction);
            image_transaction = view.findViewById(R.id.image_transaction);
        }
    }
}
