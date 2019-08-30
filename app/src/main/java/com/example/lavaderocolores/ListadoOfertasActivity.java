package com.example.lavaderocolores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lavaderocolores.Adapter.OfertasAdapter;
import com.example.lavaderocolores.Model.Ofertas;
import com.example.lavaderocolores.Model.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ListadoOfertasActivity extends AppCompatActivity {


    private String puntosCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ofertas);

        puntosCurrentUser = getIntent().getStringExtra("puntos");

        TextView puntosShow = (TextView) findViewById(R.id.CuantosPuntosListadoOferta);
        puntosShow.setText("Tenes " + puntosCurrentUser);

        final ArrayList<Ofertas> list = new ArrayList<Ofertas>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final RecyclerView recyclerView = findViewById(R.id.ofertaList);


        db.collection("oferta")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    list.add(new Ofertas(document.getData().get("title").toString(), document.getData().get("body").toString(),
                                            document.getData().get("puntos").toString(), "2", document.getId(),puntosCurrentUser.split(" Puntos")[0]));
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ListadoOfertasActivity.this));
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(new OfertasAdapter(list));

                            }
                        } else {
                            Log.d("x", "Error getting documents: ", task.getException());
                        }
                    }
                });




        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ListadoOfertasActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    Button aux = (Button) view.findViewById(R.id.puntosOfertaNumero);

                    int value = aux.getText().toString().indexOf("Te faltan");

                    Log.e("sda",""+aux.getText());


                        if(value == -1){

                            TextView uidLabel = findViewById(R.id.uidCard);
                            Intent intent = new Intent(ListadoOfertasActivity.this, PaymentOfertActivity.class);
                            intent.putExtra("uid_promo", uidLabel.getText());
                            intent.putExtra("puntos", puntosCurrentUser);

                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack2();

    }

    public void goBack(View v){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.entrada, R.anim.salida);
        finish();
    }

    public void goBack2(){
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.entrada, R.anim.salida);
        finish();
    }
}
