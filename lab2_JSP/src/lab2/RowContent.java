package lab2;

public class RowContent implements java.io.Serializable{
    private double x;
    private int y;
    private int r;
    private String areaReach;

    public RowContent(double x, int y, int r, String str){
        this.x=x;
        this.y=y;
        this.r=r;
        this.areaReach=str;
    }

    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public String getAreaReach() {
        return areaReach;
    }
}
