package pis2015.ub.com.clinicaltrialsdiary.Modelo;

import java.util.ArrayList;

/**
 * Created by user on 26/04/2015.
 */
public class Dosis
{
    /* Variables del Usuario */
    private String horarios;//ArrayList<String> horarios;
    private String compuesto;
    private int pastillasxdosis;//float pastillasxdosis;
    private int dosisxdia;

    public Dosis(){
        horarios	= "";
        compuesto	= "";
        //pastillasxdosis	= 0;
        //dosisxdia	= 0;
    }

/* Definiendo las variables */
	public void setHorarios (String e)
	{ horarios = e; }
	public void setCompuesto (String e)
	{ compuesto = e; }
	public void setPastillasDosis (int e)
	{ pastillasxdosis = e; }
	public void setDosisDia (int e)
	{ dosisxdia = e; }

    public String getHorarios(){
        return horarios;
    }

    public String getCompuesto(){
        return compuesto;
    }

    public int getPastillasxdosis(){
        return pastillasxdosis;
    }

    public int getDosisxdia(){
        return dosisxdia;
    }
}

