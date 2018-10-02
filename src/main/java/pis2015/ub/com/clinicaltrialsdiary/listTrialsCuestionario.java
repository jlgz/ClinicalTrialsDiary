package pis2015.ub.com.clinicaltrialsdiary;

/**
 * Created by alfred on 31/03/2015.
 */
public class listTrialsCuestionario {
    public listTrialsCuestionario(String name, String creacion) {
        super();
        this.name = name;
        this.creacion = creacion;
    }

    private String name;
    private String creacion;

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
}
