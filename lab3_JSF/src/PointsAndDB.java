
import model.PointEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


public class PointsAndDB {

    private static final String PERSISTENCE_UNIT_NAME = "NewPersistenceUnit";
    private static EntityManagerFactory factory;

    private List<PointEntity> list;

    public void add(PointEntity point){
      //  go();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.persist(point);
        em.getTransaction().commit();
        em.close();



    }
    public List<PointEntity> getAll(){
      //  go();
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        List<PointEntity> list = null;
        Query q = em.createQuery("select t from PointEntity t");
        list = q.getResultList();

        em.close();
        return  list;
    }
    public void setList(List<PointEntity> list) {
        this.list = list;
    }



}
