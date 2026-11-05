package com.hogar.infantil;

import com.hogar.datos.GestorCSV;
import com.hogar.interfaces.Almuerzo;
import com.hogar.interfaces.Cena;
import com.hogar.interfaces.Desayuno;
import com.hogar.interfaces.FabricaMenuDiario;

public class FabricaMenuInfantil implements FabricaMenuDiario {
    public Desayuno crearDesayuno() { String[] d = GestorCSV.obtenerPlato("Infantil", "Desayuno"); return new DesayunoInfantil(d[0], Integer.parseInt(d[1]), d[2], d[3], d[4]); }
    public Almuerzo crearAlmuerzo() { String[] d = GestorCSV.obtenerPlato("Infantil", "Almuerzo"); return new AlmuerzoInfantil(d[0], Integer.parseInt(d[1]), d[2], d[3], d[4]); }
    public Cena crearCena() { String[] d = GestorCSV.obtenerPlato("Infantil", "Cena"); return new CenaInfantil(d[0], Integer.parseInt(d[1]), d[2], d[3], d[4]); }
}