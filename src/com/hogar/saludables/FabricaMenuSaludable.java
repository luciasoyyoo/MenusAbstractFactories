package com.hogar.saludables;
import com.hogar.interfaces.*;
//import com.hogar.datos.GestorCSV;

public class FabricaMenuSaludable implements FabricaMenuDiario {
    public Desayuno crearDesayuno() {
        String[] d = GestorCSV.obtenerPlato("Saludable", "Desayuno");
        return new DesayunoSaludable(d[0], Integer.parseInt(d[1]), d[2], d[3], d[4]);
    }
    public Almuerzo crearAlmuerzo() {
        String[] d = GestorCSV.obtenerPlato("Saludable", "Almuerzo");
        return new AlmuerzoSaludable(d[0], Integer.parseInt(d[1]), d[2], d[3], d[4]);
    }
    public Cena crearCena() {
        String[] d = GestorCSV.obtenerPlato("Saludable", "Cena");
        return new CenaSaludable(d[0], Integer.parseInt(d[1]), d[2], d[3], d[4]); }
}