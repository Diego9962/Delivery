package sv.edu.ues.delivery.control.dao;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class DaoGenerico<T, PK> {
    
    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> tipoEntidad;

    public DaoGenerico(Class<T> tipoEntidad){
        this.tipoEntidad = tipoEntidad;
    }

    public List<T> listar(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(tipoEntidad);
        Root<T> root = query.from(tipoEntidad);
        query.select(root); 
        TypedQuery<T> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    public Optional<T> obtenerPorId(PK id){
        return Optional.ofNullable(em.find(tipoEntidad, id));
    }

    public T insertar(T entidad){
        em.persist(entidad);
        return entidad;
    }

    public T actualizar(T entidad){
        return em.merge(entidad);
    }

    public T eliminar(T entidad){
        em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
        return entidad;
    }


}
