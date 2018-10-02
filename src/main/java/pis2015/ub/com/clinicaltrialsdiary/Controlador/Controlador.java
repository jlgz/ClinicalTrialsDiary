package pis2015.ub.com.clinicaltrialsdiary.Controlador;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;

import java.util.ArrayList;
import java.util.List;

import pis2015.ub.com.clinicaltrialsdiary.Activities.MainActivity;
import pis2015.ub.com.clinicaltrialsdiary.Modelo.Cuestionario;
import pis2015.ub.com.clinicaltrialsdiary.Modelo.Doctor;
import pis2015.ub.com.clinicaltrialsdiary.Modelo.Ensayo;


import pis2015.ub.com.clinicaltrialsdiary.Modelo.DatosApp;
import pis2015.ub.com.clinicaltrialsdiary.Modelo.Paciente;

/**
 * Created by user on 26/04/2015.
 */
public class Controlador extends Application {

    private DatosApp datosApp;
    public Controlador(){
        this.datosApp = DatosApp.getDatosApp();
    }
    private static int Cuest,En,Pac;

    public Application getApplication(){
        new Controlador();
        return this;
    }




    //INDICES

    public static void setCuestionarioAct(int c){Cuest= c;}
    public static void setEnsayoAct(int i) {En = i;}
    public static void setPacienteSelect(int i){Pac= i;}


    /*Login*/
    public int logar (String user, String password){ //ha de retornar: 0 si fail login, 1 si es un paciente, 2 si es medico
        return DatosApp.loguear(user, password); // carga los datos del usuario logueado en DatosApp
    }

    /*Registrar un doctor en la aplicacion*/
    public String[] registrarDoctor (String nombre, String apellidos, String sexo, String hospital, String departamento, String correoE){
        return DatosApp.registrarDoctor(nombre, apellidos, sexo, hospital, departamento, correoE);
    }


    /*Registrar un paciente en la aplicacion*/
    public static String[] crearUsuario (String nombre, String apellidos, String fecha, String sexo, String estado, String telefono, String correoE){
        return DatosApp.registrarPaciente(nombre, apellidos, fecha, sexo, estado, telefono, correoE);
    }

    // añadir ensayo a la lista de ensayos del medico
    public void addEnsayo(String name, String fecha, String tipo){
        DatosApp.addEnsayo(name, fecha, tipo);

    }
    //editar los valores del ensayo actual
    public void editEnsayo(String name, String fecha, String tipo){
        DatosApp.editEnsayo(name, fecha, tipo, En); // Pasamos los nuevos valores del ensayo
    }

   //guardar la respuesta en paciente (guardar en respuesta el indice de Cuestionario act
    public static void guardarRespuesta(ArrayList<String> respuestas){
        //guarda la respuesta en el paciente (tendra como atributo el indice del cuestionario al que responde)
        DatosApp.guardarRespuestas(respuestas, Cuest);
    }

    //crea un nuevo cuestionario para ensayo actual, recorrer lista de pacientes para y guardar en los que lo tienen permitido
    public static void newCuestionario(String nombre,String fecha,ArrayList<Integer>pacientes, ArrayList<String> preguntas, String DiasResp, Boolean Notificar){
        DatosApp.newCuestionario(nombre, fecha, pacientes, preguntas, DiasResp, Notificar, En);
    }

   //edita cuestionario actual
    public static void editCuestionario(String nombre,String fecha,ArrayList<Integer>pacientes, ArrayList<String> preguntas, String DiasResp, Boolean Notificar){
        DatosApp.editCuestionario(Cuest, En, nombre, fecha, pacientes, preguntas, DiasResp, Notificar);
    }

    //añade dosis al ensayo actual
    public static void addDosis(String name, String nPastillas, String nTomas, String xHoras){
        DatosApp.addDosis(name, nPastillas, nTomas, xHoras, En);
    }

    //añade paciente de esa id al ensayo actual
    public static boolean addPacToEnsayo(String id){
        return DatosApp.addPacienteEnsayo(id, En);
    }

    public static void addCita(String fecha, String hora) { //añadir cita tanto en paciente con el medico, lo unico que cambia es el nombre, en la cita del medico sera el nombr del paciente
        DatosApp.addCita(fecha, hora, En, Pac);
    }

    public String nextId(){
        String id = DatosApp.nextId();
        return id;
    } //retorna proximo id si se creara un usuario



    // ####### Metodos para llenar las activities

    //llenando medico principal
    public String getStringMedicoPrincipal(){
        return DatosApp.getStringMedicoPrincipal();
    }

    public ArrayList<String []> getStringListEnsayo(){
        return DatosApp.getStringListEnsayo();
    }

    //llenando Ensayo Selected
    public ArrayList<String[]> getStringPacienteListEnsayo(){
        return DatosApp.getListaPacientesEnsayo(En);
    }
    public String[] getInfoMed(){
        return DatosApp.getInfoMedico(En);
    }
    //llenando Administrar Paciente
    // busca la lista de sintomas del paciente
    public ArrayList<String[]> getStringListSintomaAdmPaciente(){
        return DatosApp.getListaSintomasEnsayo(En, Pac);
    }
    // busca los datos para llenar administrar_paciente
    public String[] getInfoAdmPaciente(){
        return DatosApp.getInfoAdmPaciente(En, Pac);
    }
    //llenando Sintomas (lista de sintomas del ensayo) ???
    // DUPLICADO CON getListaSintomasPac ??????
    public List<String> getListaSintomas(){
        return DatosApp.getListaSintomas(En);
    }
    public List<String> getListaSintomasDoctor(){
        return DatosApp.getListaSintomas();
    }
    // #################################### FALTA IMPLEMENTAR ESTOS METODOS (los que tienen "return null")

    //llenado lista de cuestionarios
    public ArrayList<String[]> getInfoListCuest(){
        return DatosApp.getInfoCuestionario(En);
    }
    //???????
    public ArrayList<String[]> getinfoCuestPacientes(){
        return DatosApp.getInfoCuestPaciente(En, Pac);
    }
    //llenando editCuestionarios ¿?¿
    public String[] getInfoCuest(){
        return null;
    } //dejar para final

    //llenando la lista de cuestionarios de un paciente (al entrar como doctor)
    public ArrayList<String[]> getInfoListCuestPac(){
        return DatosApp.getInfoListCuestPac();
    }
    //llenando LeerCuestionario (desde doctor, seleccionas paciente y miras cuestionarios)
    public ArrayList<String> getPreguntas(){
        return DatosApp.getPreguntasCuestionarioPaciente(En, Pac, Cuest);
    }

    public ArrayList<String> getRespuestas(){
        return DatosApp.getRespuestas(En, Pac, Cuest);
    }
    //llenando Proximas citas
    public ArrayList<String []> getListCitasMedico() {
        return DatosApp.getListaCitas();
    }
    //llenando Main Paciente
    public ArrayList<String []> getListCitasPac() {
        return DatosApp.getListaCitasPac();
    }
    //llenando preguntas en Paciente del Cuestionario
    public ArrayList<String> getPreguntasPac(){
        return DatosApp.getPreguntasCuestionarioPaciente(En, Pac, Cuest);
    }
    //llenando Paciente sintoma
   public ArrayList<String> getListaSintomasPac(){ //strings de los sintomas del ensayo
       return DatosApp.getListaSintomasPac();
   }

    public boolean resetearPassword(String email) {
        return DatosApp.resetearPassword(email);
    }

    public String getCorreoPaciente() {
        return DatosApp.getCorreoPaciente(En,Pac);
    }

    public String getTelefonoPaciente() {
        return DatosApp.getTelefonoPaciente(En, Pac);
    }

    public boolean checkConnectivity()
    {
        boolean enabled = true;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable()))
        {
            enabled = false;
        }
        return enabled;
    }
/*
    public String getHora() {
        datosApp.getHoraCita();
    }
*/
    public String[] getInfoPaciente() {
        return DatosApp.getInfoPaciente();
    }

    public void newSintomaDoctor(String text) {
        datosApp.newSintomaDoctor(text);
    }

    public void setSintomasEnsayo(ArrayList<String> sintomas) {
        datosApp.setSintomasEnsayo(En,sintomas);
    }

    public boolean cuestionarioRespondido(int position) {
        return DatosApp.cuestionarioRespondido(position);
    }

    public ArrayList<String[]> getInfoPacselect() {
        return datosApp.getInfoListCuestPac(Pac,En);
    }

    public void addSintoma(String s, String fecha, String hora) {
        datosApp.addSintoma(s,fecha,hora);
    }

    public String getStringEnsayo() {
        return DatosApp.getStringEnsayo(En);
    }

    public String[] getStringEnsayoEdit(){
        return DatosApp.getStringEnsayoEdit(En);
    }

    public String[] getDatosDosis() {
        return datosApp.getDatosDosis(En);
    }

    public String[] getDatosDosisPaciente(){
        return datosApp.getDatosDosisPaciente();
    }

    public int[] getNextCitaPac() {
        return datosApp.getNextCita();
    }
}

