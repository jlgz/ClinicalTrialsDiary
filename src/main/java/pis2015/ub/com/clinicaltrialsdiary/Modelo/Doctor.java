package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.util.ArrayList;

/**
 * Created by user on 26/04/2015.
 */
public class Doctor extends Usuario {
    private String hospital;
    private String departamento;
    private ArrayList<Ensayo> ensayos;
    private ArrayList<String> sintomas;

    public Doctor()
    {
        super();
        hospital	    = "";
        departamento	= "";
        ensayos = new ArrayList<>();
        sintomas = new ArrayList<>();
        sintomas.add("Fiebre");
        sintomas.add("Mareos");
        sintomas.add("Dolor de cabeza");
    }

// Definiendo las variables 
	public void setHospital (String e) {
        hospital = e;
    }
	public void setDepartamento (String e) {
        departamento = e;
    }
    public void setEnsayos (ArrayList<Ensayo> e) { ensayos = e; }
    public void setSintomas(ArrayList<String> st){
        sintomas = st;
    }
// Recuperar los valores 
	public String getHospital () {
        return hospital;
    }
	public String getDepartamento () {
        return departamento;
    }


    public ArrayList<Ensayo> getEnsayos() {
        return ensayos;
    }


    public void addEnsayo(Ensayo ens) {
        this.ensayos.add(ens);
    }

    public Ensayo getEnsayoAt(int ensayo) {
        return this.ensayos.get(ensayo);
    }

    public ArrayList<String[]> getStringListEnsayo() {
        ArrayList array = new ArrayList<String[]>();
        for(Ensayo en: ensayos) {
            array.add(en.getStringList());
        }
        return array;
    }

    public ArrayList<String[]> getListaCitas() {
        ArrayList array = new ArrayList<String[]>();
        for(Cita c: this.citas) {
            array.add(c.getStringCita());
        }
        return array;
    }

    public ArrayList<String> getListaSintomas(int ensayo) {
        return this.ensayos.get(ensayo).getListaSintomas();

    }
    public ArrayList<String> getListaSintomas() {
        return sintomas;
    }

    public void newSintoma(String text) {
        sintomas.add(text);
    }

    public void setSintomasEnsayo(int en,ArrayList<String> sintomas) {
        this.ensayos.get(en).setSintomas(sintomas);
    }

    public ArrayList<String[]> getInfoListCuestionariosPac(int pac,int ens) {
        return ensayos.get(ens).getListCuestPac(pac);
    }
}
