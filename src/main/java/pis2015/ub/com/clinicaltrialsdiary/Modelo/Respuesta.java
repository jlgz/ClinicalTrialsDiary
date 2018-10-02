package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.util.ArrayList;

/**
 * Created by user on 26/04/2015.
 */
public class Respuesta {
    private ArrayList<String> respuestas;
    private int cuestionario;

    public Respuesta() {
        this.respuestas=new ArrayList<>();
        this.cuestionario=0;
    }

    public void addRespuesta(String s){
        respuestas.add(s);
    }

    public void setIntCuestionario(int cuestionario) {
        this.cuestionario = cuestionario;
    }

    public void setRespuestas(ArrayList<String> respuestas) {
        this.respuestas = respuestas;
    }

    public ArrayList<String> getRespuestas() {
        return this.respuestas;
    }

    public boolean fromCuestionario(int cuestionario) {
        return this.cuestionario==cuestionario;
    }
}
