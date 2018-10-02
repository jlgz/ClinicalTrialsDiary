package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 26/04/2015.
 */
public class Paciente extends Usuario {
    private String fecha_nacimiento;
    private String estado;
    private String infoPlus;
    private ArrayList<Sintoma> sintomas;
    private ArrayList<Respuesta> respuestas;
    private ArrayList<Cuestionario> cuestionarios;
    private ArrayList<Integer> cuestionariosIndex; //indice de los cuestionarios q se le añade al paciente

    public Paciente() {
        super();
        fecha_nacimiento="0/0/0";
        infoPlus="";
        estado="";
        sintomas = new ArrayList<>();
        respuestas = new ArrayList<>();
        cuestionarios = new ArrayList<>();
        cuestionariosIndex = new ArrayList<>();

    }

    // Definiendo las variables
    public void setFechaNacimiento (String e) {
        fecha_nacimiento = e;
    }
    public void setEstado (String e) {
        estado = e;
    }
    public void setInfoPlus (String e) {
        infoPlus = e;
    }
    public void setTelefono (String e) {
        telefono = e;
    }
    public void setCorreo (String e) {
        correo = e;
    }

    // Recuperar los valores
    public String getFechaNacimiento () {
        return fecha_nacimiento;
    }
    public String getEstado () {
        return estado;
    }
    public String getInfoPlus () {
        return infoPlus;
    }
    public String getTelefono () {
        return telefono;
    }
    public String getCorreo () {
        return correo;
    }
    // retorna la edad del paciente a partir de la fecha_nacimiento, que debe tener el formato dd/mm/yyyy
    public int getEdad() {
        if (this.fecha_nacimiento.equals("0/0/0")) {
            return 0;
        } else {
            Date date = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = formato.format(date);
            String[] fechaNac = this.fecha_nacimiento.split("/"); //vector fecha nacimiento con [dia,mes,año]
            String[] fechaAct = fechaActual.split("/"); // vector fecha actual con [dia,mes,año]
            int year = Integer.parseInt(fechaAct[2]) - Integer.parseInt(fechaNac[2]);
            int mes = Integer.parseInt(fechaAct[1]) - Integer.parseInt(fechaNac[1]);
            if (mes < 0) {
                year = year - 1; //si estamos en meses previos al de fechaNac restamos 1 año (aun no ha pasado su cumpleaños)
            } else if (mes == 0) { //si estamos en el mismo mes que fechaNac comprobamos el dia
                int dia = Integer.parseInt(fechaAct[0]) - Integer.parseInt(fechaNac[0]);
                if (dia < 0) { //si el dia de fechaNac aun no ha pasado, restamos 1 año (aun no ha pasado su cumpleaños)
                    year = year - 1;
                }
            }
            return year;
        }
    }

    public void setCuestionariosIndex(ArrayList<Integer> cuestionariosIndex) {
        this.cuestionariosIndex = cuestionariosIndex;
    }
    public ArrayList<Integer> getCuestionariosIndex() {
        return this.cuestionariosIndex;
    }

    public void setRespuestas(ArrayList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public void setSintomas(ArrayList<Sintoma> sintomas) {
        this.sintomas = sintomas;
    }

    public void addRespuesta(Respuesta resp) {
        this.respuestas.add(resp);
    }

    public void addCuestionario(Cuestionario cuest) {
        this.cuestionarios.add(cuest); //añadimos el cuestionario
    }

    public int getCuestionarioSize() {
        return this.cuestionarios.size();
    }

    public void modificarCuestionario(int cuestionarioindex, String nombre, String diasResp, String fecha, ArrayList<String> preguntas) {
        for(Cuestionario c: cuestionarios){
            if(c.getIndex() == cuestionarioindex){
                this.cuestionarios.get(cuestionarioindex).modificarCuestionario(nombre, diasResp, fecha, preguntas);
                break;
            }
        }

    }

    public Sintoma getSintomaAt(int i) {
        return this.sintomas.get(i);
    }

    public int getNumSintomas() {
        return this.sintomas.size();
    }

    public ArrayList<String> getRespuestasAt(int cuestionario) {
        ArrayList<String> info = new ArrayList<>();
        Cuestionario c = this.cuestionarios.get(cuestionario);
        if (this.respuestas.size()!=0) {
            for (Respuesta r : this.respuestas) {
                if (r.fromCuestionario(c.getIndex())) {
                    info = r.getRespuestas();
                } else { //si no encuentra respuesta al cuestionario, devuelve un arraylist del mismo tamaño que las preguntas
                    for (String s : this.cuestionarios.get(cuestionario).getPreguntas()) {
                        info.add("No hay respuesta");
                    }
                }
            }
        } else { // si no hay ninguna respuesta tambien retorna un arraylist del tamañano de las preguntas
            for (String s : this.cuestionarios.get(cuestionario).getPreguntas()) {
                info.add("No hay respuesta");
            }
        }
        return info;
    }

    public ArrayList<String> getCuestionarioAt(int cuestionario) {

        return this.cuestionarios.get(cuestionario).getPreguntas();
    }

    public void addCuestionarioIndex(int nextIndex) {
        this.cuestionariosIndex.add(nextIndex);
    }

    public void eliminarIndex(int i) {
        this.cuestionariosIndex.remove((Object) i);
    }

    public int getCuestionariosIndexAt(int i) {
        return this.cuestionariosIndex.get(i);
    }

    public void eliminarCuestionario(int i) {
        for(int j = 0; j < cuestionarios.size();j++){
            if (cuestionarios.get(j).getIndex() == i){
                cuestionarios.remove(j);
                break;
            }
        }
    }

    public boolean indiceInList(int index) {
         for(int t: this.cuestionariosIndex){
             if(t == index){
                 return true;
             }
         }
        return false;
    }

    public ArrayList<String[]> getInfoListCuestionarios() {
        ArrayList<String[]> list = new ArrayList<>();
        if (this.cuestionarios.size()!=0) {
            for (Cuestionario c : this.cuestionarios) {
                String[] info = new String[2];
                info[0] = c.getTitulo();
                info[1] = c.getDiasParaResp();
                list.add(info);
            }
        } else {
            String[] info = {"",""};
            list.add(info);
        }
        return list;
    }

    public String getCitaPaciente() {
        if (this.citas.size() != 0) {
            Cita c = this.citas.get(0);
            return c.getFecha()+" "+c.getHora();
        } else {
            return "No hay proximas citas";
        }
    }

    public String getUltimoReporte() {
        if (this.sintomas.size()!=0) { //devuelve info del ultimo sintoma
            return this.sintomas.get(this.sintomas.size()-1).getInfo();
        }
        return "No hay sintomas";
    }

    public Cuestionario getCuestionario(int cuestionario) {
        return this.cuestionarios.get(cuestionario);
    }

    //indica si un cuestionario ya tiene respuesta
    public boolean cuestionarioRespondido(int position) {
        boolean respondido = false;
        Cuestionario c = this.cuestionarios.get(position);
        if (this.respuestas.size()!=0) {
            for (Respuesta r : this.respuestas) {
                if (r.fromCuestionario(c.getIndex())) { //si encuentra respuestas del cuestionario devuelve true, sino devuelve false
                    respondido = true;
                }
            }
        }
        return respondido;
    }

    public void addSintoma(String s, String fecha, String hora) {
        Sintoma sint = new Sintoma(s,hora,fecha);
        this.sintomas.add(sint);
    }
}
