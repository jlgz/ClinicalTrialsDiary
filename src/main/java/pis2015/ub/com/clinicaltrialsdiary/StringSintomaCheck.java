package pis2015.ub.com.clinicaltrialsdiary;

/**
 * Created by user on 30/05/2015.
 */
public class StringSintomaCheck {
    private String st;
    private boolean check=false;
    public StringSintomaCheck(String s){
        st = s;
    }
    public String  getString(){
        return st;
    }
    public boolean getCheck(){
        return check;
    }
    public void setCheck(){
        check = !check;
    }

    public void isChecked(String s) {
        if(s.equals(this.st)){
            check = true;
        }
    }
}
