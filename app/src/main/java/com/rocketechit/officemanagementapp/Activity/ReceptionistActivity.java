package com.rocketechit.officemanagementapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.rocketechit.officemanagementapp.JavaClass.CheckNetwork;
import com.rocketechit.officemanagementapp.R;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class ReceptionistActivity extends AppCompatActivity {

    @BindView(R.id.qrcode_set_IV)
    ImageView qrcodeSetIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist);
        ButterKnife.bind(this);

    }

    private void codeGenerator() {

        Random random = new Random();
        int number = random.nextInt(1000);
        String number_St = String.valueOf(number);

        Toast.makeText(this, "" + number_St, Toast.LENGTH_SHORT).show();

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(number_St, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcodeSetIV.setImageBitmap(bitmap);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("QRCode").child("number").setValue(number_St);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_receptionist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.logout_receptionist:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.code_receptionest:
                codeGenerator();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
