package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 26/04/2015.
 */
public abstract class Usuario
{
    /* Variables del Usuario */
    protected String nombre;
    protected String apellidos;
    protected String nomUsuari;
    protected String password;
    protected String sexo;
    protected String correo;
    protected String telefono;
    protected ArrayList<Cita> citas;

    public Usuario ()
    {
        nombre		    = "";
        apellidos	    = "";
        nomUsuari   	= "";
        password	    = "";
	    sexo		    = "";
        correo          = "";
        telefono        = "";
        citas           = new ArrayList<>();
    }

/* Definiendo las variables */
	public void setNombre (String e)
	{ nombre = e; }
	public void setApellidos (String e)
	{ apellidos = e; }
	public void setNomUsuari (String e)
	{ nomUsuari = e; }
	public void setPassword (String e)
	{ password = e; }
	public void setSexo (String e)
	{ sexo = e; }

/* Recuperar los valores */
	public String getNombre ()
	{ return nombre; }
	public String getApellidos ()
	{ return apellidos; }
	public String getNomUsuari ()
	{ return nomUsuari; }
	public String getPassword ()
	{ return password; }
	public String getSexo ()
	{ return sexo; }

    public void addCita(Cita c) {
        // se añaden las citas
        if (!this.citaPasada(c)) { //si la cita no ha pasado se añade al usuario
            this.citas.add(c);
        }
    }

    public String[] getNextCita() {
        String[] info = new String[3];
        if (this.citas.size()!=0) {
            info[0] = this.citas.get(0).getNomDoc();
            info[1] = this.citas.get(0).getFecha();
            info[2] = this.citas.get(0).getHora();
        } else {
            info[0] = "No hay citas";
        }
        return info;
    }

    // borramos las citas pasadas
    public boolean citaPasada(Cita c) {
        boolean pasada=false;
        if (c.getFecha().equals("")) {
            //no hay fecha determinada, por tanto no se añade (como si hubiera pasado)
            pasada=true;
        } else {
            Date date = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = formato.format(date);
            String[] fechaCita = c.getFecha().split("/"); //vector fecha cita con [dia,mes,año]
            String[] fechaAct = fechaActual.split("/"); // vector fecha actual con [dia,mes,año]
            int year = Integer.parseInt(fechaAct[2]) - Integer.parseInt(fechaCita[2]);
            int mes = Integer.parseInt(fechaAct[1]) - Integer.parseInt(fechaCita[1]);
            int dia = Integer.parseInt(fechaAct[0]) - Integer.parseInt(fechaCita[0]);
            if (year>0) { // si el año de fechaAct>fechaCita, fecha pasada
                pasada= true;
            } else if (year==0) { //si es el mismo año miramos el mes
                if (mes > 0) { //si mes fechaAct>fechaCita, fecha pasada
                   pasada= true;
                } else if (mes == 0) { //si estamos en el mismo mes que fechaCita comprobamos el dia
                    if (dia > 0) { //si el dia de fechaAct>fechaCita, fecha pasada
                        pasada = true;
                    }
                }
            }
        }
        return pasada;
    }

}
