package com.example.lavaderocolores.Model;

public class Ofertas {

    private String mTitulo;
    private String mBody;
    private String mPuntosCoste;
    private String mPorcentajeDescuento;
    private String uidPromo;
    private String puntosCurrentUs;

    public Ofertas(String Titulo,  String Body, String puntos, String PorcentajePuntos, String id, String puntosCurrentUser){
        mTitulo = Titulo;
        mBody = Body;
        mPuntosCoste = puntos;
        mPorcentajeDescuento = PorcentajePuntos;
        uidPromo = id;
        puntosCurrentUs = puntosCurrentUser;
    }

    public String getPuntosCurrentUs() { return puntosCurrentUs; }

    public String getTitulo(){
        return mTitulo;
    }

    public String getmBody(){
        return mBody;
    }

    public String getUidPromo(){
        return uidPromo;
    }

    public String getmPuntosCoste(){ return mPuntosCoste;  }

    public String getmPorcentajeDescuento(){ return mPorcentajeDescuento; }

}
