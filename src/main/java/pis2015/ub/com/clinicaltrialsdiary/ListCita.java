package pis2015.ub.com.clinicaltrialsdiary;

/**
 * Created by jguillza13.alumnes on 04/05/15.
 */
public class ListCita{
    public ListCita(String name, String hora, String fecha) {
        this.name = name;
        this.hora = hora;
        this.fecha = fecha;

    }
    public ListCita() {
        this.name="";
        this.hora="";
        this.fecha="";
    }

    private String name;
    private String hora;
    private String fecha;

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
}
