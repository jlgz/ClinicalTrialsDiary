package pis2015.ub.com.clinicaltrialsdiary.Modelo;

/**
 * Created by jguillza13.alumnes on 04/05/15.
 */
public class Sintoma {
    private String name;
    private String hora;
    private String fecha;
    //private String comentario;

    public Sintoma(String sintoma, String hora, String fecha) {
        this.name=sintoma;
        this.hora=hora;
        this.fecha=fecha;
    }
    public Sintoma(){
    }

    public String getName() {
        return name;
    }

    public void setName(String nameText) {
        name = nameText;
    }

    public String getData() {
        return hora;
    }

    public void setData(String data){
        hora = data;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String num){
        fecha = num;
    }

    public String getInfo() {
        return this.name + " " + this.fecha;
    }
}
