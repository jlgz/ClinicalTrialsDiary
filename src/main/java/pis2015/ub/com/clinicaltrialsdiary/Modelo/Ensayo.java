package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.util.ArrayList;
import  android.content.Context;
import pis2015.ub.com.clinicaltrialsdiary.R;

/**
 * Created by mrusinan7.alumnes on 27/04/15.
 */
public class Ensayo {
    private String name;
    private String fecha;
    private String tipo;
    private int nextIndex;
    private ArrayList<String> sintomas;
    private ArrayList<Paciente> pacientes;
    private ArrayList<Cuestionario> cuestionarios;
    private Dosis dosis;
    //private String nomPaciente; //señala que paciente tiene la cita
    //private String nomDoctor;

    public Ensayo() {
        name="";
        fecha="";
        tipo="";
        sintomas = new ArrayList<>();
        pacientes = new ArrayList<>();
        cuestionarios = new ArrayList<>();
        dosis = new Dosis();
        nextIndex=-1;
    }

    // SETTERS
    public void setListaPacientes(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setName(String nombre) {
        this.name = nombre;
    }
    public void setSintomas(ArrayList<String> st){
        this.sintomas = st;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setDosis(Dosis d) {
        this.dosis=d;
    }
    public void setListaCuestionarios(ArrayList<Cuestionario> cuestionarios) {
        this.cuestionarios = cuestionarios;
    }

    // GETTERS
    public Paciente getPacienteAt(int i) {
        return this.pacientes.get(i);
    }
    public Dosis getDosis() { return this.dosis; }
    public Cuestionario getCuestionarioAt(int i) { return this.cuestionarios.get(i); }

    public String getName() { return this.name; }
    public String getTipo() { return this.tipo; }
    public String getFecha() { return this.fecha; }


    public int getNumPacientes() {
        return this.pacientes.size();
    }

    public ArrayList<String> getListaSintomas() {
        return this.sintomas;
    }

    public String[] getStringList() {
        String[] st = new String[3];
        st[0] = name;
        st[1] = fecha;
        st[2] = String.valueOf(pacientes.size());
        return  st;
    }

    // metodos soporte
    public void addPaciente(Paciente pac) {
        boolean existe = false;
        for(Paciente p: this.pacientes) {
            if(p.getNomUsuari().equals(pac.getNomUsuari())) {
                existe=true;
            }
        }
        if(!existe) {
            this.pacientes.add(pac);
        }
    }

    public int getIndex() {
        return this.nextIndex;
    }

    public int getCuestionariosSize() {
        return this.cuestionarios.size();
    }

    public void sumaNextIndex() {
        nextIndex++; // el primero sera 0
    }

    public void addCuestionario(Cuestionario cuest) {
        this.cuestionarios.add(cuest);
    }

    public ArrayList<Paciente> getPacientes() {
        return this.pacientes;
    }

    public ArrayList<Cuestionario> getCuestionarios() {
        return this.cuestionarios;
    }

    public ArrayList<String> getListPacientes() {
        ArrayList<String> listPacientes = new ArrayList<>();
        for (Paciente p: this.pacientes) {
            listPacientes.add(p.getNomUsuari());
        }
        return listPacientes;
    }

    public void setNextIndex(int index) {
        this.nextIndex=index;
    }

    public void modCuest(int cuestionario, Cuestionario newCuestionario) {
        this.cuestionarios.remove(cuestionario);
        this.cuestionarios.add(cuestionario, newCuestionario);
    }

    public Integer indexOf(Paciente p) {
        for(int j = 0; j< pacientes.size();j++){
            if(pacientes.get(j).getNomUsuari().equals(p.getNomUsuari())){
                return j;
            }
        }
        return -1;
    }

    public ArrayList<String> getPreguntasCuestionarioAt(int cuestionario) {
        return this.cuestionarios.get(cuestionario).getPreguntas();
    }

    public  ArrayList<String[]> getListCuestPac(int pac) {
       return pacientes.get(pac).getInfoListCuestionarios();
    }
}
