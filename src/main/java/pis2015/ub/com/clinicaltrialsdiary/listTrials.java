package pis2015.ub.com.clinicaltrialsdiary;


public class listTrials {
    public listTrials(String name, String creacion, String pacientes) {
        this.name = name;
        this.creacion = creacion;
        this.pacientes = pacientes;

    }

    private String name;
    private String creacion;
    private String pacientes;

    public String getName() {
        return name;
    }

    public void setName(String nameText) {
        name = nameText;
    }

    public String getData() {
        return creacion;
    }

    public void setData(String data){
        creacion = data;
    }

    public String getPacientes() {
        return pacientes;
    }

    public void setPacientes(String num){
        pacientes = num;
    }
}

