package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.util.ArrayList;

/**
 * Created by user on 26/04/2015.
 * No veig realment que se vol fer aqui
 */
public class Cuestionario {
    /* Variables del Cuestionario */
    private ArrayList<String> preguntas;
    private String titulo;
    private String diasParaResp;
    private String fecha;
    private int index;
    //private boolean notificar;
    //String<Int> IndicesPacientesqResponen
    public Cuestionario() {
        fecha = "";
        titulo = "";
        diasParaResp = "";
        index=0;
        preguntas = new ArrayList<String> ();
    }
    public ArrayList<String> getPreguntas(){
        return this.preguntas;
    }
    public String[] getStringList() {
        String[] st = {titulo,diasParaResp+" "+fecha};
        return st;
    }
    public int getIndex() {
        return this.index;
    }

    public String getTitulo() {
        return this.titulo;
    }
    public String getDiasParaResp() {
        return this.diasParaResp;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDias(String dias) {
        this.diasParaResp = dias;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPreguntas(ArrayList<String> preguntas) {
        this.preguntas = preguntas;
    }

    public void modificarCuestionario(String nombre, String diasResp, String fecha, ArrayList<String> preguntas) {
        this.setTitulo(nombre);
        this.setDias(diasResp);
        this.setFecha(fecha);
        this.setPreguntas(preguntas);
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
