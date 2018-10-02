package pis2015.ub.com.clinicaltrialsdiary;

/**
 * Created by user on 01/04/2015.
 */
public class listTrialsPaciente {
    public listTrialsPaciente(String name, String reporte, String edad, String id) {
        super();
        this.name = name;
        this.reporte = reporte;
        this.id = id;
        this.edad = edad;
        this.checked = false;
    }
    private String name;
    private String reporte;
    private String edad;
    private String id;
    private boolean checked;

    public String getName() {
        return name;
    }
    public void check(){
        this.checked = !this.checked;
    }
    public boolean getCheck(){
        return  this.checked;
    }
    public void setName(String nameText) {
        name = nameText;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String data){
        reporte= data;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String num){
        edad = num;
    }
    public String getid() {
        return id;
    }

    public void setid(String num){
        id = num;
    }


}
