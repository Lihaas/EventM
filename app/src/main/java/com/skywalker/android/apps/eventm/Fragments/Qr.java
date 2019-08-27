package com.skywalker.android.apps.eventm.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.skywalker.android.apps.eventm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Qr extends Fragment {

    String EventName,TeamName;
    Boolean Validity;
    ImageView qrCode;

    public Qr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_qr ,container, false);
        qrCode = rootView.findViewById(R.id.qrCodeImage);

        try{
            Bundle bundle = this.getArguments();
            EventName = bundle.getString("EventName");
            Validity = bundle.getBoolean("Validity");
            TeamName = bundle.getString("TeamName");
        }
        catch (Exception e){}

        String s = "Event Name -" + EventName +"\n"+"Validity - " + Validity + "\n" + "Team Name - "+TeamName ;


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(s, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        }catch (Exception e){}

        return rootView;
    }

}
