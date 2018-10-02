package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 26/04/2015.
 */
public class Cita {
    /* Variables de la Cita */
    private String nomDoc;
    private String nomPac;
    private String fecha;
    private String hora;

    public Cita() {
        nomDoc = "";
        nomPac = "";
        fecha = "";
        hora = "";
    }

/* Definiendo las variables */
	public void setFecha (String e)
	{ fecha = e; }
	public void setHora (String e)
	{ hora = e; }
    public void setNomDoc (String e)
    { this.nomDoc = e; }
    public void setNomPac (String e)
    { this.nomPac = e; }

/* Recuperar los valores */
	public String getFecha ()
	{ return fecha; }
	public String getHora ()
	{ return hora; }
    public String getNomDoc ()
    { return this.nomDoc; }
    public String getNomPac ()
    { return this.nomPac; }

    // para devolver lista de citas del doctor
    public String[] getStringCita(){
        String [] st = {nomPac,fecha,hora};
        return st;
    }
}
