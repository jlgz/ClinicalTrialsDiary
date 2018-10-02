package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.util.ArrayList;

import com.parse.LogInCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * Created by user on 26/04/2015.
 */
public class DatosApp {
    private static ParseObject doctores;
    private static ParseObject pacientes;
    private static DatosApp datosApp = new DatosApp();
    //private static ArrayList<Usuario> listaUsuarios;
    //private static ArrayList<Paciente> listaPacientes;
    //private static ArrayList<Paciente> listaPacientes;
    //private static ArrayList<Doctor> listaDoctores;

    // AÑADIR ArrayList<Cuestionarios> para tener todos los cuestionarios de un ensayo

    private static int ensayoActual;

    // usuario actual
    private static ParseUser userActual;
    // doctor actual
    private static Doctor docActual;
    // paciente actual con el ensayo del paciente y el doctor del paciente
    private static Paciente pacActual;
    private static Ensayo ensayoPac;
    private static Doctor doctorPac;
    private static Dosis dosis;
    // boolean registro para saber si hay que registrar (o si ya existe el usuario)
    private static boolean registro;


    public static DatosApp getDatosApp(){
        return datosApp;
    }

    private DatosApp(){
        // deberian cargarse los datos aqui, leer los datos de la nuve
        doctores = new ParseObject("Doctores");
        pacientes = new ParseObject("Pacientes");
        //usuarioActual = null;
        //listaPacientes = null;
        //listaDoctores = null;
    }

    public static int loguear(String userName, String passw) {
        // se limpian los datos antiguos antes de loguear
        userActual=null;
        docActual=null;
        pacActual=null;
        ensayoPac=null;
        doctorPac=null;
        dosis = null;
        registro=false;
        int login = 0;
        // Busca en usuarios
        ParseUser.logInInBackground(userName, passw, new LogInCallback() { // no background
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    userActual = user;

                    //String estado = (String) user.get("actor");
                    // Hooray! The user is logged in.
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    userActual = null;
                    //Log.d("user", "del ParseUser " + user.getUsername());
                }
            }
        });
        try{ Thread.sleep(5500);}
        catch (InterruptedException e) {
            Thread.interrupted();

        }
        System.out.println(userName);
        System.out.println(passw);
        //System.out.println(userActual.get("passw"));

        // Mira si el user actual es doctor o paciente (en el caso de haber logueado con exito)
        if (userActual!=null) {
            String estado = userActual.getString("actor");
            if (estado.equals("doctor")) {
                System.out.println("estado doc");
                login = 2;
                // Busca en doctores
                ParseQuery<ParseObject> queryDoc = ParseQuery.getQuery("Doctores");
                queryDoc.whereEqualTo("nomUsuari", userName);

                //queryDoc.whereEqualTo("password", passw);
                queryDoc.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> docList, ParseException e) {
                        if (e == null) {
                            //

                            System.out.println("SSS");
                            cargarDatosDoctor(docList.get(0).getString("nombre"),
                                    docList.get(0).getString("apellidos"),
                                    docList.get(0).getString("nomUsuari"),
                                    docList.get(0).getString("sexo"),
                                    docList.get(0).getString("hospital"),
                                    docList.get(0).getString("departamento"),
                                    (ArrayList<String>) docList.get(0).get("sintomas"));

                            //Log.d("user", "Se han encontrado " + docList.size() + " usuarios iguales");
                        } else {

                            // no encuentra el doctor
                            //Log.d("user", "Error: " + e.getMessage());
                        }
                    }
                });
                try{ Thread.sleep(2200);}
                catch (InterruptedException w) {
                    Thread.interrupted();}
            } else if (estado.equals("pacient")) {
                login = 1;
                // Busca en pacientes
                ParseQuery<ParseObject> queryPac = ParseQuery.getQuery("Pacientes");
                queryPac.whereEqualTo("nomUsuari", userName);
                //queryPac.whereEqualTo("password", passw);
                queryPac.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> pacList, ParseException e) {
                        if (e == null) {
                            //
                            cargarDatosPaciente(pacList.get(0).getString("nombre"),
                                    pacList.get(0).getString("apellidos"),
                                    pacList.get(0).getString("nomUsuari"),
                                    pacList.get(0).getString("fecha"),
                                    pacList.get(0).getString("sexo"),
                                    pacList.get(0).getString("estado"),
                                    pacList.get(0).getString("info"),
                                    pacList.get(0).getString("telefono"),
                                    pacList.get(0).getString("correoElectronico"),
                                    (ArrayList<Integer>) pacList.get(0).get("cuestionariosIndex"));
                            //Log.d("user", "Se han encontrado " + pacList.size() + " usuarios iguales");
                        } else {
                            // no encuentra el paciente
                            //Log.d("user", "Error: " + e.getMessage());
                        }
                    }
                });
                try{ Thread.sleep(5200);}
                catch (InterruptedException e) {
                    Thread.interrupted();

                }
            }
        } else {
            // salta error si no ha logueado con exito
        }
        // devuelve 0 si no se ha conseguido loguear, 1 si es paciente, 2 si es doctor
        System.out.println(login);

        return login;
    }

    private static void cargarDatosDoctor(String nombre, String apellidos, String nomUsuari, String sexo, String hospital, String departamento,ArrayList<String> sintomas) {
        docActual = new Doctor();
        System.out.println("nombre"+nombre);
        docActual.setNombre(nombre);
        docActual.setApellidos(apellidos);
        docActual.setNomUsuari(nomUsuari);
        docActual.setSexo(sexo);
        docActual.setHospital(hospital);
        docActual.setDepartamento(departamento);
        docActual.setSintomas(sintomas);

        // busco los ensayos (y luego pacientes) que tienen como doctor al docActual
        ParseQuery<ParseObject> queryEnsayo = ParseQuery.getQuery("Ensayos");
        queryEnsayo.whereEqualTo("nomDoctor", nomUsuari);
        queryEnsayo.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> ensayoList, ParseException e) {
                if (e == null) {
                    //
                    final ArrayList<Ensayo> ensayos = new ArrayList<>();


                    for (ParseObject en: ensayoList) {
                        final ArrayList<Paciente> pacientes = new ArrayList<>();
                        final ArrayList<Cuestionario> cuestionarios = new ArrayList<>();
                        final Ensayo ens = new Ensayo();
                        ens.setName(en.getString("nombre"));
                        ens.setFecha(en.getString("fecha"));
                        ens.setTipo(en.getString("tipo"));
                        ens.setNextIndex(en.getInt("index"));
                        ens.setSintomas((ArrayList<String> )en.get("sintomas"));
                        // obtengo los cuestionarios de cada ensayo
                        ParseQuery<ParseObject> queryCuestionarios = ParseQuery.getQuery("Cuestionarios");
                        queryCuestionarios.whereEqualTo("nomEnsayo", en.get("nombre"));
                        queryCuestionarios.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> cuestList, ParseException e) {
                                if (e == null) {
                                    for (ParseObject c: cuestList) {
                                        Cuestionario cuest = new Cuestionario();
                                        cuest.setTitulo(c.getString("titulo"));
                                        cuest.setDias(c.getString("dias"));
                                        cuest.setFecha(c.getString("fecha"));
                                        cuest.setIndex(c.getInt("index"));
                                        cuest.setPreguntas((ArrayList<String>) c.get("preguntas"));
                                        cuestionarios.add(cuest);
                                    }
                                } else {
                                    // no encuentra cuestionarios
                                }
                            }
                        });
                        ParseQuery<ParseObject> queryDosis = ParseQuery.getQuery("Dosis");
                        queryDosis.whereEqualTo("nomEnsayo", en.get("nombre"));
                        queryDosis.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> dosisList, ParseException e) {
                                if (e == null && dosisList.size() != 0) { //solo hay una dosis por ensayo
                                    Dosis dosis = new Dosis();
                                    dosis.setCompuesto(dosisList.get(0).getString("nombre")); //nombre compuesto
                                    dosis.setHorarios(dosisList.get(0).getString("horarios"));
                                    dosis.setDosisDia(dosisList.get(0).getInt("dosis"));
                                    dosis.setPastillasDosis(dosisList.get(0).getInt("pastillas"));
                                    ens.setDosis(dosis);
                                } else {
                                    // no encuentra dosis
                                }
                            }
                        });
                        // obtengo la lista de pacientes
                        ArrayList<String> nPac = (ArrayList<String>) en.get("nomPacientes");
                        for (String pac: nPac) {
                            ParseQuery<ParseObject> queryPacientes = ParseQuery.getQuery("Pacientes");
                            queryPacientes.whereEqualTo("nomUsuari", pac); // solo hay uno, asi que "get(0)"
                            queryPacientes.findInBackground(new FindCallback<ParseObject>() {
                                public void done(List<ParseObject> pacList, ParseException e) {
                                    if (e == null && pacList.size() !=0) {
                                        Paciente pac = new Paciente();
                                        pac.setNombre(pacList.get(0).getString("nombre"));
                                        pac.setApellidos(pacList.get(0).getString("apellidos"));
                                        pac.setNomUsuari(pacList.get(0).getString("nomUsuari"));
                                        pac.setSexo(pacList.get(0).getString("sexo"));
                                        pac.setFechaNacimiento(pacList.get(0).getString("fecha"));
                                        pac.setEstado(pacList.get(0).getString("estado"));
                                        pac.setInfoPlus(pacList.get(0).getString("info"));
                                        pac.setTelefono(pacList.get(0).getString("telefono"));
                                        pac.setCorreo(pacList.get(0).getString("correoElectronico"));
                                        pac.setCuestionariosIndex((ArrayList<Integer>)pacList.get(0).get("cuestionariosIndex"));
                                        // carga los arrays (sintoma, cuestionario, respuestas) a paciente
                                        datosArraysPaciente(pac.getNomUsuari(), pac, pac.getCuestionariosIndex(),ens);
                                        // se añade el paciente del ensayo en el arraylist pacientes
                                        pacientes.add(pac);
                                        //}
                                    } else {
                                        // No hay paciente
                                    }
                                }
                            });
                        }
                        try{ Thread.sleep(2200);}
                        catch (InterruptedException e2) {
                            Thread.interrupted();

                        }


                        // se añaden los arraylist de pacientes y cuestionarios de un ensayo
                        ens.setListaPacientes(pacientes);
                        ens.setListaCuestionarios(cuestionarios);
                        // se añade el ensayo en la lista de ensayos (para hacer set en los ensayos del doctor)
                        ensayos.add(ens);
                    }
                    // se añade la lista de ensayos al doctor
                    docActual.setEnsayos(ensayos);
                } else {
                    // no hay ensayos
                    //Log.d("user", "Error: " + e.getMessage());
                }
            }
        });

        // busca citas del doctor
        ParseQuery<ParseObject> queryCitas = ParseQuery.getQuery("Citas");
        queryCitas.whereEqualTo("nomDoctor", nomUsuari);
        queryCitas.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> citasList, ParseException e) {
                if (e == null) {
                    for (ParseObject c : citasList) {
                        Cita cita = new Cita();
                        cita.setFecha(c.getString("fecha"));
                        cita.setHora(c.getString("hora"));
                        cita.setNomPac(c.getString("pacient")); //nombre+apellidos paciente
                        cita.setNomDoc(c.getString("doctor")); //nombre+apellidos doctor
                        docActual.addCita(cita);
                    }
                } else {
                    // no encuentra citas
                }
            }
        });
    }

    private static void cargarDatosPaciente(String nombre, String apellidos, final String nomUsuari, String fecha, String sexo, String estado, String info, String telefono, String correoE, ArrayList<Integer> cuest) {
        pacActual = new Paciente();
        pacActual.setNombre(nombre);
        pacActual.setApellidos(apellidos);
        pacActual.setNomUsuari(nomUsuari);
        pacActual.setSexo(sexo);
        pacActual.setFechaNacimiento(fecha);
        pacActual.setEstado(estado);
        pacActual.setInfoPlus(info);
        pacActual.setTelefono(telefono);
        pacActual.setCorreo(correoE);
        pacActual.setCuestionariosIndex(cuest);

        // busca el ensayo del paciente (que a la vez da el doctor del paciente) #solo datos basicos
        ParseQuery<ParseObject> queryEnsayo = ParseQuery.getQuery("Ensayos");
        queryEnsayo.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> ensayoList, ParseException e) {
                if (e == null) {
                    final ArrayList<Cuestionario> cuestionarios = new ArrayList<Cuestionario>();

                    for (ParseObject en: ensayoList) {

                        ArrayList<String> nPac = (ArrayList<String>) en.get("nomPacientes");
                        for (String pac : nPac) {
                            // si ha encontrado el ensayo en el que esta el paciente --->
                            if (pac.equals(nomUsuari)) {
                                // carga los cuestionarios del ensayo del paciente
                                ParseQuery<ParseObject> queryCuestionarios = ParseQuery.getQuery("Cuestionarios");
                                queryCuestionarios.whereEqualTo("nomEnsayo", en.get("nombre"));
                                queryCuestionarios.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> cuestList, ParseException e) {
                                        if (e == null) {
                                            for (ParseObject c: cuestList) {
                                                Cuestionario cuest = new Cuestionario();
                                                cuest.setTitulo(c.getString("titulo"));
                                                cuest.setDias(c.getString("dias"));
                                                cuest.setFecha(c.getString("fecha"));
                                                cuest.setIndex(c.getInt("index"));
                                                cuest.setPreguntas((ArrayList<String>) c.get("preguntas"));
                                                cuestionarios.add(cuest);
                                            }
                                        } else {
                                            // no encuentra cuestionarios
                                        }
                                    }
                                });
                                Ensayo ens = new Ensayo();
                                ens.setName(en.getString("nombre"));
                                ens.setFecha(en.getString("fecha"));
                                ens.setTipo(en.getString("tipo"));
                                ens.setNextIndex(en.getInt("index"));
                                ens.setSintomas((ArrayList<String>) en.get("sintomas"));
                                ens.setListaCuestionarios(cuestionarios);
                                // ensayo del paciente actual guardado
                                ensayoPac = ens;
                                // busca la dosis del ensayo
                                ParseQuery<ParseObject> queryDosis = ParseQuery.getQuery("Dosis");
                                queryDosis.whereEqualTo("nomEnsayo", ensayoPac.getName());
                                queryDosis.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> dosisList, ParseException e) {
                                        if (e == null && dosisList.size() != 0) { //solo hay una dosis por ensayo
                                            Dosis dosis = new Dosis();
                                            dosis.setCompuesto(dosisList.get(0).getString("nombre")); //nombre compuesto
                                            dosis.setHorarios(dosisList.get(0).getString("horarios"));
                                            dosis.setDosisDia(dosisList.get(0).getInt("dosis"));
                                            dosis.setPastillasDosis(dosisList.get(0).getInt("pastillas"));
                                            ensayoPac.setDosis(dosis);
                                        } else {
                                            // no encuentra dosis
                                        }
                                    }
                                });
                                // busqueda de los datos del doctor (a partir del nomDoctor de ensayo)
                                ParseQuery<ParseObject> queryDoc = ParseQuery.getQuery("Doctores");
                                queryDoc.whereEqualTo("nomUsuari", en.getString("nomDoctor"));
                                queryDoc.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> ensayoList, ParseException e) {
                                        if (e == null) {
                                            Doctor doc = new Doctor();
                                            doc.setNombre(ensayoList.get(0).getString("nombre"));
                                            doc.setApellidos(ensayoList.get(0).getString("apellidos"));
                                            doc.setNomUsuari(ensayoList.get(0).getString("nomUsuari"));
                                            doc.setSexo(ensayoList.get(0).getString("sexo"));
                                            doc.setHospital(ensayoList.get(0).getString("hospital"));
                                            doc.setDepartamento(ensayoList.get(0).getString("departamento"));
                                            // doctor del paciente actual guardado
                                            doctorPac = doc;
                                        } else {
                                            // no hay doctor
                                            doctorPac = null;
                                        }
                                    }
                                });
                                try{ Thread.sleep(3200);}
                                catch (InterruptedException e2) {
                                    Thread.interrupted();

                                }
                                break;
                            }
                        }
                    }

                    // llama al metodo que se encarga de cargar los arrays de pacientes
                    datosArraysPaciente(pacActual.getNomUsuari(), pacActual, pacActual.getCuestionariosIndex(), ensayoPac);
                } else {
                    // no hay ensayo
                    ensayoPac = null;
                }
            }
        });

        try{ Thread.sleep(6200);}
        catch (InterruptedException e) {
            Thread.interrupted();

        }

    }

    // carga los arrays (citas, cuestionarios, respuestas, sintomas) de paciente
    private static void datosArraysPaciente(String nomUsuari, final Paciente p, final ArrayList<Integer> cuestionarios, final Ensayo ens) {

        // busca las citas
        ParseQuery<ParseObject> queryCitas = ParseQuery.getQuery("Citas");
        queryCitas.whereEqualTo("nomPacient", nomUsuari);
        queryCitas.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> citasList, ParseException e) {
                if (e == null) {
                    for (ParseObject c : citasList) {
                        Cita cita = new Cita();
                        cita.setFecha(c.getString("fecha"));
                        cita.setHora(c.getString("hora"));
                        cita.setNomPac(c.getString("pacient")); //nombre+apellidos paciente
                        cita.setNomDoc(c.getString("doctor")); //nombre+apellidos doctor
                        p.addCita(cita);
                    }
                } else {
                    // no encuentra citas
                }
            }
        });

        // recorre los cuestionarios del ensayo, si el indice coincide con la lista de cuestionariosIndex de paciente entonces añade el cuestionario
        ParseQuery<ParseObject> queryCuestionario = ParseQuery.getQuery("Cuestionarios");
        queryCuestionario.whereEqualTo("nomEnsayo", ens.getName());
        queryCuestionario.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> cuestionarioList, ParseException e) {
                if (e == null) {
                    for (ParseObject cuest: cuestionarioList) {
                        for (Integer index : cuestionarios) { //p.getCuestionariosIndex()) {
                            if (cuest.get("index") == index) {
                                Cuestionario newCuest = new Cuestionario();
                                newCuest.setTitulo(cuest.getString("titulo"));
                                newCuest.setDias(cuest.getString("dias"));
                                newCuest.setFecha(cuest.getString("fecha"));
                                newCuest.setIndex(cuest.getInt("index")); // primer cuestionario en añadirse sera 0 (aunq borres un cuestionario el nextIndex seguira aumentando, nunca repite numeros)
                                newCuest.setPreguntas((ArrayList<String>)cuest.get("preguntas"));
                                p.addCuestionario(newCuest);
                            }
                        }
                    }
                } else {

                }
            }
        });

        // busca las respuestas del paciente
        ParseQuery<ParseObject> queryRespuesta = ParseQuery.getQuery("Respuestas");
        queryRespuesta.whereEqualTo("nomPacient", nomUsuari);
        queryRespuesta.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> respuestaList, ParseException e) {
                if (e == null) {
                    final ArrayList<Respuesta> respuestas = new ArrayList<>();
                    for (int i = 0; i < respuestaList.size(); i++) {
                        Respuesta resp = new Respuesta();
                        resp.setIntCuestionario(respuestaList.get(i).getInt("cuestionario"));
                        resp.setRespuestas((ArrayList<String>) respuestaList.get(i).get("respuestas"));
                        respuestas.add(resp);
                    }
                    // añade las respuestas del paciente
                    p.setRespuestas(respuestas);
                } else {
                    // no hay respuestas
                }
            }
        });

        // busca los sintomas del paciente
        ParseQuery<ParseObject> querySintoma = ParseQuery.getQuery("Sintomas");
        querySintoma.whereEqualTo("nomPacient", nomUsuari);
        querySintoma.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> sintomaList, ParseException e) {
                if (e == null) {
                    final ArrayList<Sintoma> sintomas = new ArrayList<>();
                    for (int i = 0; i < sintomaList.size(); i++) {
                        Sintoma sint = new Sintoma();
                        sint.setName(sintomaList.get(i).getString("sintoma"));
                        sint.setData(sintomaList.get(i).getString("hora"));
                        sint.setFecha(sintomaList.get(i).getString("fecha"));
                        sintomas.add(sint);
                    }
                    // añade los sintomas del paciente
                    p.setSintomas(sintomas);
                } else {
                    // no hay sintomas
                }
            }
        });
        try{ Thread.sleep(1200);}
        catch (InterruptedException e) {
            Thread.interrupted();

        }
    }

    public static String[] registrarDoctor(String nombre, String apellidos, String sexo, String hospital, String departamento, String correo) {
        String [] datosRegistro = new String[2]; //Se usa para pasar en el return los datos de nombreUsuario y Password hasta la activity
        String nomUsuari = getId(nombre, apellidos); //CORRREGIR: Si se registran 2 doctores con el mismo nombre/apellido se sobreescriben!
        String passw = getPassword();
        ParseUser user = new ParseUser();
        user.setUsername(nomUsuari); // Usuari
        user.setPassword(passw); // PASSW
        user.setEmail(correo);
        user.put("actor", "doctor");
        user.signUpInBackground(new SignUpCallback() {
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    registro = true;
                    // Hooray! Let them use the app now.
                } else {
                    registro = false;
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
        try{ Thread.sleep(5000);}
        catch (InterruptedException e) {
            Thread.interrupted();

        }
        //Doctor d = new Doctor(nombre, apellidos, id, passw, sexo, hospital, departamento);
        //listaDoctores.add(d);
        if (registro) {
            String[] st = {"Fiebre","Mareos", "Dolor de cabeza"};
            ArrayList<String> sintomas = new ArrayList<String>(Arrays.asList(st));
            ParseObject doctores = new ParseObject("Doctores");
            doctores.put("nombre", nombre);
            doctores.put("apellidos", apellidos);
            doctores.put("nomUsuari", nomUsuari);
            //doctores.put("passw", passw);
            doctores.put("sexo", sexo);
            doctores.put("hospital", hospital);
            doctores.put("departamento", departamento);
            doctores.put("sintomas",sintomas);
            doctores.saveInBackground();
        }
        try{ Thread.sleep(5000);}
        catch (InterruptedException e) {
            Thread.interrupted();

        }
        datosRegistro[0]=nomUsuari;
        datosRegistro[1]=passw;
        return datosRegistro;
    }

    public static String[] registrarPaciente(final String nombre, final String apellidos, final String fecha, final String sexo, final String estado, final String telefono, final String correoE) {
        final String nomUsuari = getId(nombre, telefono);
        String passw = getPassword();
        ParseUser user = new ParseUser();
        user.setUsername(nomUsuari); // ID
        user.setPassword(passw); // PASSW
        user.setEmail(correoE); // correo
        user.put("actor","pacient");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    //registro = true;
                    //Paciente p = new Paciente(nombre, apellidos, id, passw, fecha, sexo, estado, info, telefono, correoE);
                    //listaPacientes.add(p);
                    ParseObject pacientes = new ParseObject("Pacientes");
                    pacientes.put("nombre", nombre);
                    pacientes.put("apellidos", apellidos);
                    pacientes.put("nomUsuari", nomUsuari);
                    pacientes.put("fecha", fecha);
                    pacientes.put("cuestionariosIndex",new ArrayList<Integer>());
                    pacientes.put("sexo", sexo);
                    pacientes.put("estado", estado);
                    pacientes.put("telefono", telefono);
                    pacientes.put("correoElectronico", correoE);
                    pacientes.saveInBackground();
                    // Hooray! Let them use the app now.
                } else {
                    //registro = false;
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
        String[] info={nomUsuari,passw};
        return info;
    }









    ////////////// METODOS DE SOPORTE

    private static String getId(String nombre, String telefono) {
        nombre = nombre.replace(" ","");
        telefono = telefono.replace(" ","");
        return nombre+telefono;
    }

    // Genera password alfanumerica de longitud 6
    public static String getPassword() {
        String key = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String pswd = "";

        for (int i = 0; i < 6; i++) {
            pswd+=(key.charAt((int)(Math.random() * key.length())));
        }

        return pswd;
    }

    // ################### Metodos de add y get informacion

    public static void addEnsayo(String name, String fecha, String tipo) {
        // CREACION ENSAYO en la nube: con doctor pero sin paciente relacionado
        String[] st = {"Fiebre","Mareos", "Dolor de cabeza"};
        ArrayList<String> sintomas = new ArrayList<String>(Arrays.asList(st));
        ParseObject ensayos = new ParseObject("Ensayos");
        ensayos.put("nombre", name);
        ensayos.put("fecha", fecha);
        ensayos.put("tipo", tipo);
        ensayos.put("index", -1);
        ensayos.put("nomPacientes", new ArrayList<String>());
        ensayos.put("nomDoctor", docActual.getNomUsuari());
        ensayos.put("sintomas", sintomas);
        ensayos.saveInBackground();

        // creacion objeto ensayo, se añade a la lista de ensayos del docActual
        Ensayo ens = new Ensayo();
        ens.setSintomas(sintomas);
        ens.setName(name);
        ens.setFecha(fecha);
        ens.setTipo(tipo);
        docActual.addEnsayo(ens);
    }

    public static String[] getStringEnsayoEdit(int en){
        String [] datosEnsayoEdit = new String[3];
        Ensayo e;
        e = docActual.getEnsayoAt(en);
        datosEnsayoEdit[0]=e.getName();
        datosEnsayoEdit[1]=e.getFecha();
        datosEnsayoEdit[2]=e.getTipo();
        return datosEnsayoEdit;
    }


    // añade dosis al ensayo actual/modifica dosis del ensayo actual
    public static void addDosis(final String name, final String nPastillas, final String nTomas, final String xHoras, int ensayo) {
        final Ensayo ens = docActual.getEnsayoAt(ensayo);
        // se modifica de la nube si ya existe, sino la crea
        ParseQuery<ParseObject> queryDosis = ParseQuery.getQuery("Dosis");
        queryDosis.whereEqualTo("nomEnsayo", ens.getName());
        queryDosis.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> dosisList, ParseException e) {
                if (e == null && dosisList.size() != 0) { //si existe la dosis del ensayo la modifica con los nuevos datos
                    dosisList.get(0).put("nombre", name);
                    dosisList.get(0).put("pastillas", nPastillas);
                    dosisList.get(0).put("dosis", nTomas);
                    dosisList.get(0).put("horarios", xHoras);
                    dosisList.get(0).put("nomEnsayo", ens.getName());
                    dosisList.get(0).saveInBackground();
                } else { //si no hay dosis la crea
                    ParseObject dosis = new ParseObject("Dosis");
                    dosis.put("nombre", name);
                    dosis.put("pastillas", nPastillas);
                    dosis.put("dosis", nTomas);
                    dosis.put("horarios", xHoras);
                    dosis.put("nomEnsayo", ens.getName());
                    dosis.saveInBackground();
                }
            }
        });

        Dosis d = new Dosis();
        d.setCompuesto(name);
        d.setPastillasDosis(Integer.parseInt(nPastillas));
        d.setDosisDia(Integer.parseInt(nTomas));
        d.setHorarios(xHoras);
        // se añade la dosis al ensayo
        ens.setDosis(d);
    }

    public static Doctor getMedicoActual() {
        return null;
    }

    public static void editEnsayo(final String name, final String fecha, final String tipo, int ensayo) {
        // primero accedemos al ensayo actual: luego modificamos en docActual y nube
        Ensayo ens = docActual.getEnsayoAt(ensayo);

        // actualizamos los valores del ensayo (nombre, fecha y tipo) en la nube
        // buscamos el ensayo correspondiente
        ParseQuery<ParseObject> queryEnsayo = ParseQuery.getQuery("Ensayos");
        queryEnsayo.whereEqualTo("nombre", ens.getName());
        queryEnsayo.whereEqualTo("fecha", ens.getFecha());
        queryEnsayo.whereEqualTo("tipo", ens.getTipo());
        queryEnsayo.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> ensayoList, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    for (ParseObject p : ensayoList) {
                        p.put("nombre", name);
                        p.put("fecha", fecha);
                        p.put("tipo", tipo);
                        p.saveInBackground();
                    }
                }
            }
        });
        // modificamos el nomEnsayo de los cuestionarios en la nube (para que sigan apuntando correctamente)
        ParseQuery<ParseObject> queryCuestionario = ParseQuery.getQuery("Cuestionarios");
        queryCuestionario.whereEqualTo("nomEnsayo", ens.getName());
        queryCuestionario.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> cuestList, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    for (ParseObject c : cuestList) {
                        c.put("nomEnsayo", name);
                        c.saveInBackground();
                    }
                }
            }
        });
        try{ Thread.sleep(500);}
        catch (InterruptedException e) {
            Thread.interrupted();
        }
        // modificamos el ensayo de docActual
        ens.setName(name);
        ens.setFecha(fecha);
        ens.setTipo(tipo);
    }


    public static Ensayo getEnsayoAct() {
        return null;
    }

    public static void guardarRespuestas(ArrayList<String> arrayRespuestas, int cuestionario) {
        // añade en la nube la respuesta del cuestionario
        Cuestionario c = pacActual.getCuestionario(cuestionario); //obtengo el cuestionario seleccionado
        ParseObject respuestas = new ParseObject("Respuestas");
        respuestas.put("cuestionario", c.getIndex()); //guardo el atributo index del cuestionario seleccionado
        respuestas.put("nomPacient", pacActual.getNomUsuari());
        respuestas.put("respuestas", arrayRespuestas);
        respuestas.saveInBackground();

        // añade al objeto pacActual la respuesta
        Respuesta resp = new Respuesta();
        resp.setIntCuestionario(c.getIndex());
        resp.setRespuestas(arrayRespuestas);
        pacActual.addRespuesta(resp);
    } //Guarda las resupuestas de un cuestionario

    public static Cuestionario getCuestionario() {
        return null;
    } //Obtiene el cuestionario en el que nos encontramos

    public static String[] getInfoPaciente() {
        String[] info = new String[3];
        info[0]=pacActual.getNombre()+" "+pacActual.getApellidos();
        info[1]="Dr. "+doctorPac.getNombre()+" "+doctorPac.getApellidos();
        info[2]=doctorPac.getHospital();
        return info;
    }//Obtiene el paciente seleccionado

    public static void newCuestionario(String nombre, String fecha, ArrayList<Integer> pacientes, ArrayList<String> preguntas, String diasResp, Boolean notificar, int ensayo) {
        final Ensayo ens = docActual.getEnsayoAt(ensayo);
        ens.sumaNextIndex();//aumenta el index de cuestionario
        // cambia el index cuestionario en la nube
        ParseQuery<ParseObject> queryEnsayos = ParseQuery.getQuery("Ensayos");
        queryEnsayos.whereEqualTo("nombre", ens.getName()); // mirar q hace el getName
        queryEnsayos.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> ensayosList, ParseException e) {
                if (e == null) {
                    ensayosList.get(0).put("index",ens.getIndex());
                    ensayosList.get(0).saveInBackground();
                } else {
                    // no encuentra cuestionarios
                }
            }
        });

        // crea el cuestionario con los datos (posteriormente lo añade a cada paciente y al ensayo)
        Cuestionario cuest = new Cuestionario();
        cuest.setTitulo(nombre);
        cuest.setDias(diasResp);
        cuest.setFecha(fecha);
        cuest.setIndex(ens.getIndex()); // primer cuestionario en añadirse sera 0 (aunq borres un cuestionario el nextIndex seguira aumentando, nunca repite numeros)
        cuest.setPreguntas(preguntas);
        ens.addCuestionario(cuest);

        // crea el cuestionario en la nube (con el nomEnsayo)
        ParseObject cuestionarios = new ParseObject("Cuestionarios");
        cuestionarios.put("titulo", nombre);
        cuestionarios.put("dias", diasResp);
        cuestionarios.put("fecha", fecha);
        cuestionarios.put("index", ens.getIndex()); // lo dicho arriba (aunque borres un cuestionario nunca repetira index)
        cuestionarios.put("nomEnsayo", ens.getName());
        cuestionarios.put("preguntas", preguntas);
        cuestionarios.saveInBackground();
        // añade el cuestionario a los pacientes
        System.out.println(pacientes.get(0));
        for (int i: pacientes) {
            final Paciente p = ens.getPacienteAt(i);
            // añade los indices en DATOS
            p.addCuestionarioIndex(ens.getIndex());
            // añade el cuestionario en DATOS
            p.addCuestionario(cuest);

            // añade el array de int en la nube
            ParseQuery<ParseObject> queryPaciente = ParseQuery.getQuery("Pacientes");
            queryPaciente.whereEqualTo("nomUsuari", p.getNomUsuari());
            queryPaciente.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> pacList, ParseException e) {
                    if (e == null && pacList.size() != 0) { // si existe el paciente se añade y se actualiza la nube
                        pacList.get(0).put("cuestionariosIndex", p.getCuestionariosIndex());
                        pacList.get(0).saveInBackground();
                    } else {

                    }
                }
            });


        }
    } //Guarda el nuevo cuestionario creado


    // Edita los datos de un determinado cuestionario, modificando todos los pacientes que tenian ese cuestionario (y añadiendo el cuestionario a los nuevos pacientes)
    public static void editCuestionario(int cuestionario, int ensayo, final String nombre, final String fecha, ArrayList<Integer> pacientes, final ArrayList<String> preguntas, final String diasResp, Boolean notificar) {
        Ensayo ens = docActual.getEnsayoAt(ensayo);
        Cuestionario cuest = ens.getCuestionarioAt(cuestionario);
        ParseQuery<ParseObject> queryCuestionario = ParseQuery.getQuery("Cuestionarios");
        queryCuestionario.whereEqualTo("nomEnsayo", ens.getName());
        queryCuestionario.whereEqualTo("index", cuest.getIndex()); // solo hay uno cuestionario con nomEnsayo y el index correspondiente
        queryCuestionario.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> cuestionarioList, ParseException e) {
                if (e == null) {
                    // se actualiza el cuestionario en la nube
                    cuestionarioList.get(0).put("titulo", nombre);
                    cuestionarioList.get(0).put("dias", diasResp);
                    cuestionarioList.get(0).put("fecha", fecha);
                    // no se modifica el index
                    cuestionarioList.get(0).put("preguntas", preguntas);
                    cuestionarioList.get(0).saveInBackground();
                } else {
                    // no hay cuestionarios
                }
            }
        });
        // se crea el nuevo cuestionario con los nuevos datos, despues se añadira a los nuevos pacientes q lo tendran
        Cuestionario newCuestionario = new Cuestionario();
        newCuestionario.setTitulo(nombre);
        newCuestionario.setDias(diasResp);
        newCuestionario.setFecha(fecha);
        newCuestionario.setIndex(cuest.getIndex()); // se mantiene el index
        newCuestionario.setPreguntas(preguntas);
        ens.modCuest(cuestionario, newCuestionario);
        // recorrer todos los pacientes del ensayo, los q se quitan eliminar cuestionario, los nuevos añadirlo
        for (final Paciente p : ens.getPacientes()) {

            if (p.indiceInList(cuest.getIndex())) { // si coincide el index del paciente con el del cuestionario ---> modificar o eliminar
                if (!pacientes.contains(ens.indexOf(p))) {
                    p.eliminarIndex(cuest.getIndex());
                    p.eliminarCuestionario(cuest.getIndex());
                    ParseQuery<ParseObject> queryPac = ParseQuery.getQuery("Pacientes");
                    queryPac.whereEqualTo("nomUsuari", p.getNomUsuari());
                    queryPac.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> pacList, ParseException e) {
                            if (e == null) {
                                // se actualiza el cuestionario en la nube
                                pacList.get(0).put("cuestionariosIndex", p.getCuestionariosIndex());
                                pacList.get(0).saveInBackground();
                            } else {
                                // no hay cuestionarios
                            }
                        }
                    });

                }
                else{
                    p.modificarCuestionario(cuest.getIndex(),nombre,diasResp,fecha,preguntas);
                }
            } else { // si no coincide el index  del paciente con el del cuestionario significa que se ha de añadir el cuestionario y el index

                if (pacientes.contains(ens.indexOf(p))) {
                    p.addCuestionarioIndex(cuest.getIndex());
                    p.addCuestionario(newCuestionario);
                    ParseQuery<ParseObject> queryPac = ParseQuery.getQuery("Pacientes");
                    queryPac.whereEqualTo("nomUsuari", p.getNomUsuari());
                    queryPac.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> pacList, ParseException e) {
                            if (e == null) {
                                // se actualiza el cuestionario en la nube
                                pacList.get(0).put("cuestionariosIndex", p.getCuestionariosIndex());
                                pacList.get(0).saveInBackground();
                            } else {
                                // no hay cuestionarios
                            }
                        }
                    });

                }


            }
        }
    }


    // NO AÑADE EL ARRAY DE PACIENTES A ENSAYO, ni siquiera en DatosApp ni en la nube
    // añade un paciente a un ensayo
    public static boolean addPacienteEnsayo(final String nomUsuario, int ensayo) {
        // primero accedemos al ensayo actual
        final Ensayo ens = docActual.getEnsayoAt(ensayo);

        final Paciente pac = new Paciente();
        ParseQuery<ParseObject> queryPaciente = ParseQuery.getQuery("Pacientes");
        queryPaciente.whereEqualTo("nomUsuari", nomUsuario);
        queryPaciente.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> pacList, ParseException e) {

                if (e == null && pacList.size() != 0) { // si existe el paciente se añade y se actualiza la nube
                    registro = true;
                    pac.setNombre(pacList.get(0).getString("nombre"));
                    pac.setApellidos(pacList.get(0).getString("apellidos"));
                    pac.setNomUsuari(pacList.get(0).getString("nomUsuari"));
                    pac.setSexo(pacList.get(0).getString("sexo"));
                    pac.setFechaNacimiento(pacList.get(0).getString("fecha"));
                    pac.setEstado(pacList.get(0).getString("estado"));
                    pac.setInfoPlus(pacList.get(0).getString("info"));
                    pac.setTelefono(pacList.get(0).getString("telefono"));
                    pac.setCorreo(pacList.get(0).getString("correoElectronico"));
                    ens.addPaciente(pac);

                    ParseQuery<ParseObject> queryEnsayos = ParseQuery.getQuery("Ensayos");

                    queryEnsayos.whereEqualTo("nombre", ens.getName()); // mirar q hace el getName
                    queryEnsayos.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> ensayosList, ParseException e) {
                            if (e == null) {

                                ArrayList<String> nomPacientes = ens.getListPacientes();

                                ensayosList.get(0).put("nomPacientes", nomPacientes); // añade el nombre del paciente al arraylist nomPacientes del ensayo
                                ensayosList.get(0).saveInBackground();
                            } else {
                                // no encuentra cuestionarios
                            }
                        }
                    });
                    // no hace falta hacer setCuestionariosIndex, es un paciente nuevo, por tanto no tiene cuestionarios
                    // no hace falta cargar los arrays ya que el paciente es nuevo (por tanto no tiene datos de sintomas, cuestionarios, respuestas)
                    //datosArraysPaciente(pacList.get(0).getString("nomUsuari"),pac, null);

                    // si encuentra el usuario actualiza el ensayo en la nube


                    // añade el paciente en la lista de pacientes del ensayo

                } else {
                    // No hay paciente
                    registro = false; //
                }
            }
        });
        try{ Thread.sleep(8000);}
        catch (InterruptedException e) {
            Thread.interrupted();

        }
        return registro;// 0-> no encuentra paciente, 1-> si lo encuentra
    }

    //Añade una cita entre un doctor y un paciente para una determinada fecha y hora
    public static void addCita(String fecha, String hora, int ensayo, int paciente) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        Paciente p = e.getPacienteAt(paciente);

        // se añade a la nube
        ParseObject citas = new ParseObject("Citas");
        citas.put("fecha", fecha);
        citas.put("hora", hora);
        citas.put("pacient", p.getNombre()+" "+p.getApellidos()); //nombre+apellidos paciente
        citas.put("doctor", docActual.getNombre()+" "+docActual.getApellidos()); //nombre+apellidos doctor
        citas.put("nomPacient", p.getNomUsuari());
        citas.put("nomDoctor", docActual.getNomUsuari());
        citas.saveInBackground();

        Cita c = new Cita();
        c.setFecha(fecha);
        c.setHora(hora);
        c.setNomDoc(docActual.getNombre()+" "+docActual.getApellidos());
        c.setNomPac(p.getNombre()+" "+p.getApellidos());
        // se añaden las citas en doctor y paciente
        docActual.addCita(c);
        p.addCita(c);
    }

    public static String nextId() {
        return null;
    }//Retorna un nuevo ID disponible para un nuevo usuario






    // ################################### Metodos para llenar las activities

    public static String getStringMedicoPrincipal() {
        String apellidos;
        String dept;
        String hospital;
        if (docActual.getSexo().equals("Hombre")) {
            apellidos = "Dr. ";
        } else {
            apellidos = "Dra. ";
        }
        apellidos = apellidos+docActual.getApellidos();
        dept = docActual.getDepartamento();
        hospital = docActual.getHospital();
        return (apellidos+"\n"+dept+", "+hospital);
    }//Retorna la información del medico para la pagina principal de doctores. (Dr/a. Apellidos , Departamento, Hospital)

    public static ArrayList<String[]> getListaPacientesEnsayo(int ensayo) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        String[] info;
        ArrayList<String[]> listaPacientes = new ArrayList<>();
        // por cada paciente en ensayo crea un array con la info de ese usuario
        // y luego lo añade en el ArrayList listaPacientes
        for (int i=0; i<e.getNumPacientes(); i++) {
            info = new String[4];
            info[0]=e.getPacienteAt(i).getNombre()+" "+
                    e.getPacienteAt(i).getApellidos();
            info[1]=e.getPacienteAt(i).getNomUsuari();
            info[2]=e.getPacienteAt(i).getUltimoReporte();//obtiene ultimo sintoma(nombre+fecha)
            info[3]=Integer.toString(e.getPacienteAt(i).getEdad());
            listaPacientes.add(info);
        }
        return listaPacientes;
    }//Retornar la lista de pacientes que hay en un ensayo

    public static String[] getInfoMedico(int ensayo) {
        String [] info = new String[7];
        Ensayo e;
        String apellidos;
        String dept_hosp;
        String nomEnsayo;
        String fechaCreacion;
        String ultimaVisita;
        String numPacientes;
        String tipoEnsayo;

        if (docActual.getSexo().equals("Hombre")) {
            apellidos = "Dr. ";
        } else {
            apellidos = "Dra. ";
        }
        apellidos = apellidos+docActual.getApellidos();
        dept_hosp = docActual.getDepartamento()+", "+docActual.getHospital();
        e = docActual.getEnsayoAt(ensayo);
        nomEnsayo = e.getName();
        fechaCreacion = e.getFecha();
        numPacientes = Integer.toString(e.getNumPacientes());
        tipoEnsayo = e.getTipo();

        info[0]=apellidos;
        info[1]=dept_hosp;
        info[2]=nomEnsayo;
        info[3]=fechaCreacion;
        info[4]=numPacientes;
        info[5]=tipoEnsayo;

        return info;
    } //Retornar la informacion de un doctor. No se la diferencia con getStringMedicoPrincipal...

    public static String[] getInfoAdmPaciente(int ensayo, int paciente) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        Paciente p = e.getPacienteAt(paciente);
        String [] info = new String[8];

        info[0]=e.getName();
        info[1]=p.getNombre()+" "+p.getApellidos();
        info[2]=p.getSexo();
        info[3]=Integer.toString(p.getEdad());
        info[4]=p.getEstado();
        info[5]=p.getCitaPaciente(); //devuelve fecha y hora de la siguiente cita con ese paciente
        info[6]=p.getTelefono();
        info[7]=p.getCorreo();

        return info;
    }

    public static ArrayList<String[]> getListaSintomasEnsayo(int ensayo, int paciente) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        Paciente p = e.getPacienteAt(paciente);
        String[] info;
        String name;
        String hora;
        String fecha;
        ArrayList<String[]> listaSintomas = new ArrayList<>();

        // por cada sintoma accede a la informacion y la añade en el array info
        // y luego lo añade en el ArrayList listaSintomas
        for (int i=0; i<p.getNumSintomas(); i++) {
            info = new String[3];
            Sintoma s = p.getSintomaAt(i);
            name = s.getName();
            hora = s.getData();
            fecha = s.getFecha();
            info[0]=name;
            info[1]=hora;
            info[2]=fecha;
            listaSintomas.add(info);
        }
        return listaSintomas;
    } //Retornar la lista de Strings con los sintomas que hay para un ensayo

    public static ArrayList<String[]> getInfoCuestionario(int ensayo) {
        Ensayo ens = docActual.getEnsayoAt(ensayo);
        ArrayList<String[]> cuestionarios = new ArrayList<>();
        for(Cuestionario c: ens.getCuestionarios()) { // recorre los cuestionarios de un ensayo
            cuestionarios.add(c.getStringList()); // añade la info de un cuestionario (titulo, fecha+hora)
        }
        return cuestionarios;
    } //Retornar info de los cuestionarios de un ensayo

    //Retornar las respuestas de un cuestionario contestado por un paciente
    public static ArrayList<String> getRespuestas(int ensayo, int paciente, int cuestionario) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        Paciente p = e.getPacienteAt(paciente);
        return p.getRespuestasAt(cuestionario); //devuelve las respuestas del cuestionario (indice cuestionario=indice respuestas)
    }

    //Retornar lista de todas las citas de un doctor
    public static ArrayList<String[]> getListaCitas() {
        return docActual.getListaCitas();
    }

    public static ArrayList<String> getListaSintomas(int ensayo) {
        return docActual.getListaSintomas(ensayo);
    }
    public static ArrayList<String> getListaSintomas() {
        return docActual.getListaSintomas();
    }//Retorna la lista de posibles sintomas para un ensayo
    public static  ArrayList<String> getListaSintomasPac(){
        return ensayoPac.getListaSintomas();
    }
    public static ArrayList<String[]> getStringListEnsayo() {
        return docActual.getStringListEnsayo();
    }

    // solo devuelve una cita (ya que el paciente solo tendra una cita con el doctor)
    public static ArrayList<String[]> getListaCitasPac() {
        ArrayList<String[]> listaCitas = new ArrayList<>();
        String[] citaPaciente = pacActual.getNextCita();// coge la primera
        listaCitas.add(citaPaciente);

        return listaCitas; //Retorna el dia-hora- fecha de la proxima cita de un paciente
    }

    public static ArrayList<String> getPreguntasCuestionario(int ensayo, int cuestionario) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        return e.getPreguntasCuestionarioAt(cuestionario);
    }

    // MODIFICAR
    public static boolean resetearPassword(String email) {
        final boolean[] error = {false};
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    error[0] = true;
                } else {
                    error[0] = true;
                }
            }

        });
        return error[0];
    }



    public static String getCorreoPaciente(int ensayo, int paciente) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        Paciente p = e.getPacienteAt(paciente);
        return p.getCorreo();
    }

    public static String getTelefonoPaciente(int ensayo, int paciente) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        Paciente p = e.getPacienteAt(paciente);
        return p.getTelefono();
    }
    public static ArrayList<String[]> getInfoListCuestPac(int Pac,int ens) {
        return docActual.getInfoListCuestionariosPac(Pac, ens);

    }
    public static ArrayList<String[]> getInfoListCuestPac() {
        return pacActual.getInfoListCuestionarios();

    }

    public void newSintomaDoctor(String text) {
        docActual.newSintoma(text);
        ParseQuery<ParseObject> queryDoctores = ParseQuery.getQuery("Doctores");
        queryDoctores.whereEqualTo("nomUsuari", docActual.getNomUsuari());
        queryDoctores.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                parseObjects.get(0).put("sintomas",docActual.getListaSintomas());
                parseObjects.get(0).saveInBackground();
            }
        } );
    }

    public void setSintomasEnsayo(int en,ArrayList<String> sintomas) {
        this.docActual.setSintomasEnsayo(en, sintomas);
        final ArrayList<String> listToPut = docActual.getListaSintomas(en);
        ParseQuery<ParseObject> queryEnsayo = ParseQuery.getQuery("Ensayos");
        queryEnsayo.whereEqualTo("nombre", docActual.getEnsayos().get(en).getName());
        queryEnsayo.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                parseObjects.get(0).put("sintomas",listToPut);
                parseObjects.get(0).saveInBackground();
            }
        });
    }

    public static ArrayList<String[]> getInfoCuestPaciente(int ensayo, int paciente) {
        Ensayo e = docActual.getEnsayoAt(ensayo);
        Paciente p = e.getPacienteAt(paciente);
        return p.getInfoListCuestionarios();
    }

    public static ArrayList<String> getPreguntasCuestionarioPaciente(int en, int pac, int cuest) {
        if (docActual!=null) { //si ha logueado como doctor utiliza el paciente seleccionado para acceder a los cuestionarios
            Ensayo e = docActual.getEnsayoAt(en);
            Paciente p = e.getPacienteAt(pac);
            return p.getCuestionarioAt(cuest);
        } else { //si ha logueado como paciente utiliza pacActual para acceder a los cuestionarios
            return pacActual.getCuestionarioAt(cuest);
        }
    }

    public static boolean cuestionarioRespondido(int position) {
        return pacActual.cuestionarioRespondido(position);
    }

    public void addSintoma(String s, String fecha, String hora) {
        pacActual.addSintoma(s,fecha,hora);

        // se añade a la nube
        ParseObject sintomas = new ParseObject("Sintomas");
        sintomas.put("fecha", fecha);
        sintomas.put("hora", hora);
        sintomas.put("nomPacient", pacActual.getNomUsuari());
        sintomas.put("sintoma", s);
        sintomas.saveInBackground();


    }

    public static String getStringEnsayo(int en) {
        String nomEnsayo;
        Ensayo e;
        e = docActual.getEnsayoAt(en);
        nomEnsayo = e.getName();
        return nomEnsayo;
    }


    /*INFO*/
    //DOSIS = Numero de tomas al dia//
    //Horarios = Cada x horas//
    //PASTILLAS = Num pastillas por dosis//
    public String[] getDatosDosis(int en) {
        String [] datosDosis = new String[4];
        Ensayo e;
        Dosis d;
        e = docActual.getEnsayoAt(en);
        d = e.getDosis();
        datosDosis[0] = d.getCompuesto(); //Nombre
        datosDosis[1] = d.getHorarios(); // Horarios CUANDO Antes de las comidas por ej
        datosDosis[2] = Integer.toString(d.getDosisxdia()); //Dosis
        datosDosis[3] = Integer.toString(d.getPastillasxdosis()); //Pastillas
        return datosDosis;
    }

    public String[] getDatosDosisPaciente(){
        String [] datosDosis = new String[4];
        Dosis d;
        d = ensayoPac.getDosis();
        datosDosis[0] = d.getCompuesto(); //Nombre
        datosDosis[1] = d.getHorarios(); // Horarios CUANDO Antes de las comidas por ej
        datosDosis[2] = Integer.toString(d.getDosisxdia()); //Dosis NO FUNCIONA??? <---------------
        datosDosis[3] = Integer.toString(d.getPastillasxdosis()); //Pastillas NO FUNCIONA??? <-----
        return datosDosis;
    }


    //FUNCION PARA OBTENER LOS DATOS DE FECHA Y HORA DE LA PROXIMA CITA, NO FUNCIONA
    public int[] getNextCita() {
        int [] proximasCitas = new int[6];
        String[] result;
        result = pacActual.getNextCita();
        if (result[0] != "No hay citas") {
            String fecha = result[1];
            String[] arrayFecha = fecha.split("/");
            proximasCitas[0] = Integer.parseInt(arrayFecha[0]);
            proximasCitas[1] = Integer.parseInt(arrayFecha[1]);
            proximasCitas[2] = Integer.parseInt(arrayFecha[2]);
            String h = result[2];
            String[] arrayHora = h.split(":");
            proximasCitas[3] = Integer.parseInt(arrayHora[0]);
            proximasCitas[4] = Integer.parseInt(arrayHora[1]);


            if (proximasCitas[3] > 11) {
                proximasCitas[5] = Calendar.PM;
            } else {
                proximasCitas[5] = Calendar.AM;
            }
        }
        else{
            proximasCitas[0] = -1;
        }
        return proximasCitas;
    }
}
