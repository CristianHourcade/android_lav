package com.example.lavaderocolores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
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
    private int auxValue;
    private int mIsStatusScroll;
    private int mWasStatusScroll;
    private int statusGlobalScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ofertas);


        auxValue = 0;
        mIsStatusScroll = 0;
        mWasStatusScroll = 1;
        statusGlobalScroll = 0;
        puntosCurrentUser = getIntent().getStringExtra("puntos");


        final CardView aux = findViewById(R.id.puntosCard);
        Animation anim = (Animation) AnimationUtils.loadAnimation(ListadoOfertasActivity.this,R.anim.to_show_up);
        aux.startAnimation(anim);


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
                                            document.getData().get("puntos").toString(), document.getData().get("descuento").toString(), document.getId(),puntosCurrentUser.split(" Puntos")[0],document.getData().get("producto").toString()+":"));
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ListadoOfertasActivity.this));
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(new OfertasAdapter(list));

                            }
                        } else {
                            Log.d("x", "Error getting documents: ", task.getException());
                        }
                    }
                });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                /***************************************
                 * Case 0 : Finalizado el evento scroll
                 * Case 1 : Tocando el scrol
                 * Case 2 : Moviendose el scroll
                 ********************************+******/

                statusGlobalScroll = newState;
                Log.e("Estado anterior", ""+mWasStatusScroll);
                Log.e("Estado actual",""+mIsStatusScroll);
                Log.e(":B","-------------------------");
                switch (newState){
                    case 0:
                      //  Log.e("tag","Finalizado");
                        if(mIsStatusScroll == 0 && mWasStatusScroll != mIsStatusScroll){
                            final CardView aux = findViewById(R.id.puntosCard);
                            Animation anim = (Animation) AnimationUtils.loadAnimation(ListadoOfertasActivity.this,R.anim.to_hide_down);
                            aux.startAnimation(anim);

                            anim.setAnimationListener(new Animation.AnimationListener(){
                                @Override
                                public void onAnimationStart(Animation arg0) {
                                }
                                @Override
                                public void onAnimationRepeat(Animation arg0) {
                                }
                                @Override
                                public void onAnimationEnd(Animation arg0) {
                                    aux.setVisibility(View.INVISIBLE);
                                    mWasStatusScroll = mIsStatusScroll;

                                }
                            });
                        }else{
                           // Log.e("Entro en bajando","je");
                            if(auxValue != 0 && mWasStatusScroll != mIsStatusScroll){

                                mIsStatusScroll = 1;
                                final CardView aux = findViewById(R.id.puntosCard);
                                Animation anim = (Animation) AnimationUtils.loadAnimation(ListadoOfertasActivity.this,R.anim.to_show_up);
                                aux.startAnimation(anim);

                                anim.setAnimationListener(new Animation.AnimationListener(){
                                    @Override
                                    public void onAnimationStart(Animation arg0) {
                                    }
                                    @Override
                                    public void onAnimationRepeat(Animation arg0) {
                                    }
                                    @Override
                                    public void onAnimationEnd(Animation arg0) {
                                        aux.setVisibility(View.VISIBLE);
                                        mWasStatusScroll = mIsStatusScroll;

                                    }
                                });
                            }
                            auxValue = auxValue + 1;
                        }
                        break;
                    case 1:
                       // Log.e("tag", "Tocando");
                        break;
                    case 2:
                        //Log.e("tag","scrolleando");
                        break;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0){

                    /** Bajando **/
                    if(auxValue != 0 ) {
                        mIsStatusScroll = 0;
                    }

                }else{

                    /** Subiendo **/
                    mIsStatusScroll = 1;

                }
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ListadoOfertasActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    Button aux = (Button) view.findViewById(R.id.puntosOfertaNumero);

                    int value = aux.getText().toString().indexOf("Te faltan");



                        if(value == -1){

                            TextView uidLabel = view.findViewById(R.id.uidCard);
                            TextView valueProcut = view.findViewById(R.id.Producto_seleccionado);
                            TextView porcentaje = view.findViewById(R.id.porc);


                            Intent intent = new Intent(ListadoOfertasActivity.this, PaymentOfertActivity.class);
                            intent.putExtra("uid_promo", uidLabel.getText());
                            intent.putExtra("puntos", puntosCurrentUser);
                            intent.putExtra("producto",valueProcut.getText());
                            intent.putExtra("porcentaje",porcentaje.getText());

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
