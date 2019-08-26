package com.example.lavaderocolores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Onboarding extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    View mStep1;
    View mStep2;
    View mStep3;

    RadioButton mRButton1;
    RadioButton mRButton2;
    RadioButton mRButton3;

    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mStep1 = findViewById(R.id.Step1);
        mStep2 = findViewById(R.id.Step2);
        mStep3 = findViewById(R.id.Step3);

        mStep1.setVisibility(View.VISIBLE);
        mStep2.setVisibility(View.INVISIBLE);
        mStep3.setVisibility(View.INVISIBLE);

        mRButton1 = findViewById(R.id.btnStep1);
        mRButton2 = findViewById(R.id.btnStep2);
        mRButton3 = findViewById(R.id.btnStep3);

        mRButton1.toggle();

        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }



        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        Token= task.getResult().getToken();
                        Log.e("ras", ""+Token);
                    }
                });
    }

    public void goFragment(View v) {
        if (mStep1.getVisibility() == View.VISIBLE) {
            mRButton1.toggle();
            mRButton2.toggle();

            mStep1.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.go_salida);
            mStep1.startAnimation(animacion1);

            mStep2.setVisibility(View.VISIBLE);
            Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.go_entrada);
            mStep2.startAnimation(animacion2);

            mStep3.setVisibility(View.INVISIBLE);
            Button btnLogin = findViewById(R.id.goLogin);
            btnLogin.setText("siguiente");
            Button btnOmitir = findViewById(R.id.btnOmitir);
            btnOmitir.setVisibility(View.VISIBLE);
        } else if (mStep2.getVisibility() == View.VISIBLE) {
            mRButton2.toggle();
            mRButton3.toggle();

            mStep1.setVisibility(View.INVISIBLE);

            mStep2.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.go_salida);
            mStep2.startAnimation(animacion1);

            mStep3.setVisibility(View.VISIBLE);
            Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.go_entrada);
            mStep3.startAnimation(animacion2);

            Button btnLogin = findViewById(R.id.goLogin);
            btnLogin.setText("¡Empezar Birralandia!");

            Button btnOmitir = findViewById(R.id.btnOmitir);
            btnOmitir.setVisibility(View.INVISIBLE);
        } else {
            VerificarUsuarioOnline();
          //  startActivity(new Intent(this, ListCervecerias.class));
        }
    }

    public void goStep1(View v) {
        if (mStep2.getVisibility() == View.VISIBLE) {
            mStep2.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.salida);
            mStep2.startAnimation(animacion1);
        }
        if (mStep3.getVisibility() == View.VISIBLE) {
            mStep3.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.salida);
            mStep3.startAnimation(animacion1);
        }
        mStep1.setVisibility(View.VISIBLE);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.entrada);
        mStep1.startAnimation(animacion2);
        Button btnLogin = findViewById(R.id.goLogin);
        btnLogin.setText("siguiente");
        Button btnOmitir = findViewById(R.id.btnOmitir);
        btnOmitir.setVisibility(View.VISIBLE);
    }

    public void goStep2(View v) {
        if (mStep1.getVisibility() == View.VISIBLE) {
            mStep1.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.go_salida);
            mStep1.startAnimation(animacion1);
            mStep2.setVisibility(View.VISIBLE);
            Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.go_entrada);
            mStep2.startAnimation(animacion2);
        }
        if (mStep3.getVisibility() == View.VISIBLE) {
            mStep3.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.salida);
            mStep3.startAnimation(animacion1);
            mStep2.setVisibility(View.VISIBLE);
            Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.entrada);
            mStep2.startAnimation(animacion2);
        }
        Button btnLogin = findViewById(R.id.goLogin);
        btnLogin.setText("siguiente");
        Button btnOmitir = findViewById(R.id.btnOmitir);
        btnOmitir.setVisibility(View.VISIBLE);
    }

    public void goStep3(View v) {
        if (mStep1.getVisibility() == View.VISIBLE) {
            mStep1.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.go_salida);
            mStep1.startAnimation(animacion1);
            mStep3.setVisibility(View.VISIBLE);
            Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.go_entrada);
            mStep3.startAnimation(animacion2);
        }
        if (mStep2.getVisibility() == View.VISIBLE) {
            mStep2.setVisibility(View.INVISIBLE);
            Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.go_salida);
            mStep2.startAnimation(animacion1);

            mStep3.setVisibility(View.VISIBLE);
            Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.go_entrada);
            mStep3.startAnimation(animacion2);
        }

        Button btnLogin = findViewById(R.id.goLogin);
        btnLogin.setText("¡Empezar Birralandia!");
        Button btnOmitir = findViewById(R.id.btnOmitir);
        btnOmitir.setVisibility(View.INVISIBLE);
    }


    private void VerificarUsuarioOnline() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();


                final Map<String, Object> UserCurrent = new HashMap<>();

                UserCurrent.put("uid", user.getUid());
                UserCurrent.put("email", user.getEmail() );
                UserCurrent.put("nombre", user.getDisplayName());
                UserCurrent.put("token", Token+"");

                db.collection("users")
                        .whereEqualTo("uid", user.getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    if(task.getResult().isEmpty()){
                                        db.collection("users")
                                                .document(user.getUid())
                                                .set(UserCurrent)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        startActivity(new Intent(Onboarding.this,MainActivity.class));

                                                   }
                                                });
                                    }else{
                                        startActivity(new Intent(Onboarding.this,MainActivity.class));
                                        finish();
                                    }

                                } else {



                                }
                            }
                        });




            } else {

            }
        }
    }
}
