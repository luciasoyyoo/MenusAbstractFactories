package com.hogar.saludables;
import com.hogar.interfaces.Cena;

public class CenaSaludable implements Cena {
    private final String n;
    private final int c;
    private final String d, i, p;
    public CenaSaludable(String n, int c, String d, String i, String p) {
        this.n=n;
        this.c=c;
        this.d=d;
        this.i=i;
        this.p=p;
    }

    public String obtenerNombre() { return n; }
    public int obtenerCalorias() { return c; }
    public String obtenerDescripcion() { return d; }
    public String obtenerIngredientes() { return i; }
    public String obtenerPasos() { return p; }
}