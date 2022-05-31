package com.example.cryapp.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cryapp.MainActivity;
import com.example.cryapp.R;
import com.example.cryapp.databinding.FragmentDashboardBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class DashboardFragment extends Fragment {

    private String login, password_app_string, walletName;
    private ImageView qr_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,
                container, false);

        TextView text_dashboard = view.findViewById(R.id.text_dashboard);
        TextView registration_login_1 = view.findViewById(R.id.registration_login_1);
        TextView password_wallet_1 = view.findViewById(R.id.password_wallet_1);
        TextView address_ethereum = view.findViewById(R.id.address_ethereum);
        qr_image = view.findViewById(R.id.qr_image);

        load_value();

        String content = walletName;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qr_image.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //qrCode_creation regedit = new qrCode_creation();
        //regedit.execute();

        registration_login_1.setText(login);
        password_wallet_1.setText(password_app_string);
        address_ethereum.setText(walletName);

        return view;
    }

    public void load_value() {
        MainActivity activity = (MainActivity)getActivity();
        Bundle results = activity.getMyData();
        login = results.getString("login");
        password_app_string = results.getString("password_app_string");
        walletName = results.getString("walletName");
    }

   /* private class qrCode_creation extends AsyncTask<String, String, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmp = null;
            load_value();
            String content = walletName;
            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute (Bitmap bmp){
            qr_image.setImageBitmap(bmp);
        }
    } */

}