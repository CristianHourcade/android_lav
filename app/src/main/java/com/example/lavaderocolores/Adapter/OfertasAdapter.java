package com.example.lavaderocolores.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lavaderocolores.Model.Ofertas;
import com.example.lavaderocolores.R;

import org.w3c.dom.Text;

import java.util.List;

public class OfertasAdapter extends
        RecyclerView.Adapter<OfertasAdapter.ViewHolder> {


@Override
public OfertasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.card_item_oferta, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
        }

// Involves populating data into the item through holder
@SuppressLint("ResourceAsColor")
@Override
public void onBindViewHolder(OfertasAdapter.ViewHolder viewHolder, int position) {

        // Get the data model based on position
        Ofertas ofertas = mOfertas.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.Title;
        textView.setText(ofertas.getTitulo());

        TextView body = viewHolder.Body;
        body.setText(ofertas.getmBody());

        Button btn = viewHolder.Points;
        String puntosCoste = "";

        if(ofertas.getmPuntosCoste().equals("0")){
            puntosCoste = "¡Canjear Cupon Gratis!";
        }else{

            Log.e("hola",""+ofertas.getPuntosCurrentUs());
            Log.e("sxsxs:",""+ofertas.getmPuntosCoste());


            if(Integer.parseInt(ofertas.getPuntosCurrentUs()) > Integer.parseInt(ofertas.getmPuntosCoste())){
                puntosCoste = "Canjear " + ofertas.getmPuntosCoste() + " Puntos";
            }else{
                puntosCoste = "Te faltan " + (Integer.parseInt(ofertas.getmPuntosCoste())-Integer.parseInt(ofertas.getPuntosCurrentUs())) + " Puntos";
                viewHolder.Points.setBackgroundColor(R.color.colorAccent);
                viewHolder.btnGo.setVisibility(View.INVISIBLE);
            }


        }

        btn.setText(puntosCoste);

        TextView uidLabel = viewHolder.UidPromo;
        uidLabel.setText(ofertas.getUidPromo());
        }

// Returns the total count of items in the list
@Override
public int getItemCount() {
        return mOfertas.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    public TextView Title;
    public TextView Body;
    public Button Points;
    public TextView UidPromo;
    public ImageView btnGo;
    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        btnGo = (ImageView) itemView.findViewById(R.id.btnGo);
        Title = (TextView) itemView.findViewById(R.id.TitleOffer);
        Body = (TextView) itemView.findViewById(R.id.bodyOferta);
        Points = (Button) itemView.findViewById(R.id.puntosOfertaNumero);
        UidPromo = (TextView) itemView.findViewById(R.id.uidCard);
    }
}

    private List<Ofertas> mOfertas;


    public OfertasAdapter(List<Ofertas> oferta) {
        mOfertas = oferta;
    }
}
