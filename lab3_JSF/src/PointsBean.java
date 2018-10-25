import model.PointEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "pointsbean")
@SessionScoped
public class PointsBean {

    private final PointsAndDB pointsAndDB = new PointsAndDB();
    private float x, y, r ;
   private boolean areareach;
   private List<PointEntity> points;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r=r;
    }

    public List<PointEntity> getPoints() {
        points = pointsAndDB.getAll();
        return points;
    }

    public void setPoints(List<PointEntity> points) {
        this.points = points;
    }

    public boolean isAreareach() {
        return areareach;
    }

    public void addNewPoint(){
        PointEntity point = new PointEntity(this.x, this.y,this.r);
        pointsAndDB.add(point);
    }

    public void setAreareach(boolean areareach) {
        this.areareach = areareach;
    }
}
