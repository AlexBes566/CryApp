package com.example.cryapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryapp.Adapters.NotificationsAdapter;
import com.example.cryapp.R;
import com.example.cryapp.Adapters.Transaction;
import com.example.cryapp.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    ArrayList<Transaction> username = new ArrayList<Transaction>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications,
                container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NotificationsAdapter(getContext(),setInitialData()));

        return view;
    }

    private ArrayList<Transaction> setInitialData(){
        username.add(new Transaction("Александр","5000 ETH", R.drawable.intransaction));
        username.add(new Transaction("Владимир","200 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Fedor","23423 ETH", R.drawable.intransaction));
        username.add(new Transaction("Andrey","234234 ETH", R.drawable.intransaction));
        username.add(new Transaction("Mikhail","20122 ETH", R.drawable.intransaction));
        username.add(new Transaction("Папа","53513 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Дядя","3135 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Бабушка","5343 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Брат","232153 ETH", R.drawable.intransaction));
        username.add(new Transaction("Константин Игоревич","888 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Александр","5000 ETH", R.drawable.intransaction));
        username.add(new Transaction("Владимир","200 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Fedor","23423 ETH", R.drawable.intransaction));
        username.add(new Transaction("Andrey","234234 ETH", R.drawable.intransaction));
        username.add(new Transaction("Mikhail","20122 ETH", R.drawable.intransaction));
        username.add(new Transaction("Папа","53513 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Дядя","3135 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Бабушка","5343 ETH", R.drawable.outtransaction));
        username.add(new Transaction("Брат","232153 ETH", R.drawable.intransaction));
        username.add(new Transaction("Константин Игоревич","888 ETH", R.drawable.outtransaction));

        return username;
    }
}