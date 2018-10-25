package lab2;

import java.util.ArrayList;
import java.util.List;

public class ResultBean implements java.io.Serializable {

    private List<RowContent> result=new ArrayList<RowContent>();

    public void addResult(double x,int y,int r, String str){
        result.add(new RowContent(x,y,r,str));
    }

    public List<RowContent> getResult() {
        return result;
    }

    public void setResult(ArrayList<RowContent> result) {
        this.result = result;
    }
}
