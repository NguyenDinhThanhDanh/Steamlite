package modeleDAO;

import Entities.Catalogue;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Service
public class CatalogueDAO implements ICatalogueDao {

    private final String PERSISTENCE_UNIT_NAME = "DatabaseCatalogue";
    private EntityManager entityManager;

    public CatalogueDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(this.PERSISTENCE_UNIT_NAME);
        this.entityManager =  emf.createEntityManager();
    }

    @Override
    public Catalogue get(long id) {
        return this.entityManager.find(Catalogue.class, id);
    }

    @Override
    public List<Catalogue> getAll() {
        CriteriaQuery<Catalogue> cq = this.entityManager.getCriteriaBuilder().createQuery(Catalogue.class);
        cq.select(cq.from(Catalogue.class));
        return this.entityManager.createQuery(cq).getResultList();
    }

    @Override
    public boolean save(Catalogue c) {
        var createCata = this.entityManager.getTransaction();

        try {
            createCata.begin();
            this.entityManager.persist(c);
            createCata.commit();
            return true;
        } catch (Exception e) {
            createCata.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Catalogue c, String[] params) {
        var updateCata = this.entityManager.getTransaction();

        try {
            updateCata.begin();
            this.entityManager.merge(c);
            updateCata.commit();
            return true;
        } catch (Exception e) {
            updateCata.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Catalogue c) {
        var deleteCata = this.entityManager.getTransaction();

        try {
            deleteCata.begin();
            this.entityManager.remove(this.entityManager.contains(c) ? c : this.entityManager.merge(c));
            deleteCata.commit();
            return true;
        } catch (Exception e) {
            deleteCata.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
