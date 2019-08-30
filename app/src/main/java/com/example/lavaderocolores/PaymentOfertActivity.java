package com.example.lavaderocolores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class PaymentOfertActivity extends AppCompatActivity {

    private String uidPromo;
    private String puntos;

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 800;
    public final static int HEIGHT = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_ofert);


        uidPromo = getIntent().getStringExtra("uid_promo");
        puntos = getIntent().getStringExtra("puntos");

        ImageView imageView = (ImageView) findViewById(R.id.qrCode);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        try {
            Bitmap bitmap = encodeAsBitmap(user.getUid() + "|&|" + uidPromo);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }




    public void Volver(View v){
        Intent intent = new Intent(this,ListadoOfertasActivity.class);
        intent.putExtra("puntos",puntos);
        startActivity(intent);
        overridePendingTransition(R.anim.entrada, R.anim.salida);
        finish();
    }
}
