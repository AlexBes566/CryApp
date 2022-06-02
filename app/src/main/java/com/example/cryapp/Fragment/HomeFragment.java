package com.example.cryapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cryapp.AddContactActivity;
import com.example.cryapp.ContactsListActivity;
import com.example.cryapp.MainActivity;
import com.example.cryapp.R;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment{

    private ProgressBar progressBar;
    public Web3j web3j;
    TextView balance;
    String password_wallet, walletDirectory, walletName;
    Credentials credentials;

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);

        ImageButton my_data = view.findViewById(R.id.my_data);
        ImageButton my_balance_btn = view.findViewById(R.id.my_balance_btn);
        ImageButton add_username = view.findViewById(R.id.add_username);
        ImageButton send_transaction = view.findViewById(R.id.send_transaction);
        ImageButton contacts = view.findViewById(R.id.contacts);
        progressBar = view.findViewById(R.id.progressBar);
        balance = view.findViewById(R.id.balance);

        my_data.setEnabled(false);

        my_balance_btn.setOnClickListener(view1 -> {
            walletBalance regedit = new walletBalance();
            regedit.execute();
        });

        add_username.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getActivity(), AddContactActivity.class);
            startActivity(intent);
        });

        contacts.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getActivity(), ContactsListActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private class walletBalance extends AsyncTask<String, String, String> {

        protected void onPreExecute(){
            progressBar.setVisibility(ProgressBar.VISIBLE);
            balance.setVisibility(TextView.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String wallet_balance = "";
            try {
                MainActivity activity = (MainActivity)getActivity();
                Bundle results = activity.getMyData();
                password_wallet = results.getString("password_wallet");
                walletDirectory = results.getString("walletDirectory");
                walletName = results.getString("walletName");
                credentials = WalletUtils.loadCredentials(password_wallet, walletDirectory + "/" + walletName);
                web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/f5ed25abb81d4229b9763adcf94f4181"));
                EthGetBalance ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
                wallet_balance = (Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER)).toString();
            } catch (ExecutionException | InterruptedException | CipherException | IOException e) {
                e.printStackTrace();
            }
            return wallet_balance;
        }

        protected void onPostExecute (String wallet_balance){
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            balance.setVisibility(TextView.VISIBLE);
            balance.setText(wallet_balance + " ETH");
        }
    }
}