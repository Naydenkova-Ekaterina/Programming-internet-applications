package model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "point", schema = "s225036", catalog = "studs")
//@Table(name = "points")
public class PointEntity implements Serializable {
    private int id;
    private float x;
    private float y;
    private float r;
    private boolean areareach;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public PointEntity(float x, float y,float r) {
        this.x = x;
        this.y = y;
        this.r=r;
        check();
    }

    public PointEntity() {
    }
    @Basic
    @Column(name = "x")
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Basic
    @Column(name = "r")
    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    @Basic
    @Column(name = "areareach")
    public boolean isAreareach() {
        return areareach;
    }

    public void setAreareach(boolean areareach) {
        this.areareach = areareach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointEntity that = (PointEntity) o;

        if (id != that.id) return false;
        if (Float.compare(that.x, x) != 0) return false;
        if (Float.compare(that.y, y) != 0) return false;
        if (Float.compare(that.r, r) != 0) return false;
        if (areareach != that.areareach) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (r != +0.0f ? Float.floatToIntBits(r) : 0);
        result = 31 * result + (areareach ? 1 : 0);
        return result;
    }

    public void check(){

        if((x>=-r/2 && x<=0 && y>=-r && y<=0) ||
                (x>=0 && x<=r && y<=0 && y>=-r/2 && y>=(x-r)/2) ||
                (x>=-r/2 && x<=0 && y>=0 && y<=r/2 && x*x+y*y<=r*r)){
            this.areareach=true;
        }else{
            this.areareach=false;
        }

    }



}
