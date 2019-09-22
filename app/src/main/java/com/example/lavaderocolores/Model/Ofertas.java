package com.example.lavaderocolores.Model;

public class Ofertas {

    private String mTitulo;
    private String mBody;
    private String mPuntosCoste;
    private String mPorcentajeDescuento;
    private String uidPromo;
    private String puntosCurrentUs;
    private String mProductoSeleccionado;

    public Ofertas(String Titulo,  String Body, String puntos, String PorcentajePuntos, String id, String puntosCurrentUser, String producto){
        mTitulo = Titulo;
        mBody = Body;
        mPuntosCoste = puntos;
        mPorcentajeDescuento = PorcentajePuntos;
        uidPromo = id;
        puntosCurrentUs = puntosCurrentUser;
        mProductoSeleccionado = producto;
    }

    public String getProducto() { return mProductoSeleccionado; }

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
