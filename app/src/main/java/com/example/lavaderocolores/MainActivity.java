package com.example.lavaderocolores;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.okhttp.internal.Util;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CarouselView carouselView;
    TextView puntos;
    TextView cantOfertas;

    private SwipeRefreshLayout swipeRefreshLayout;

    int[] PromoImages = {R.drawable.img_1, R.drawable.img_2, R.drawable.f1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        carouselView = (CarouselView) findViewById(R.id.carouselView);
        puntos = findViewById(R.id.PuntosText);
        cantOfertas = findViewById(R.id.PromosText);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        searchDateOf();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchDateOf();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carouselView.setPageCount(PromoImages.length);
        carouselView.setImageListener(imageListener);
    }

    public void goPayment(View v) {
        startActivity(new Intent(this, PaymentActivity.class));
        finish();
        overridePendingTransition(R.anim.go_entrada, R.anim.go_salida);
    }


    public void ListadoProductos(View view) {
        Intent intent = new Intent(this, ListadoOfertasActivity.class);
        intent.putExtra("puntos", puntos.getText());
        startActivity(intent);
        overridePendingTransition(R.anim.go_entrada, R.anim.go_salida);

    }


    public void MostrarReferidos(View v) {
        startActivity(new Intent(this, ReferidosCompartirActivity.class));
        overridePendingTransition(R.anim.go_entrada, R.anim.go_salida);
    }


    public void Logout(View v) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, Onboarding.class));
                    }
                });
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(PromoImages[position]);
            imageView.setImageResource(PromoImages[position]);
        }
    };


    public void searchDateOf() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String aux;

                                if (document.getData().get("puntos") == null) {
                                    aux = "0";
                                } else {
                                    aux = document.getData().get("puntos").toString();
                                }

                                puntos.setText(aux + " Puntos");

                            }
                            if (swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }

                        } else {
                            Log.d("x", "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection("oferta")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer acumulador = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                acumulador++;
                            }
                            cantOfertas.setText(acumulador + " Ofertas");
                        } else {
                            Log.d("x", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Llamar(View v) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:49230544"));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivity(i);
    }

    public void whatsapp(View v) {
        PackageManager pm=getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "This is  a Test"; // Replace with your own message.

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }catch(Exception e){
        }

    }
}


