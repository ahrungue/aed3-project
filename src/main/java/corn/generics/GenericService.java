package corn.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alvaro
 * @since 29/11/14.
 */
@Service
public abstract class GenericService<T>{

    @Autowired
    private GenericDAO<T> genericDAO;

    @Transactional
    public T findById(String id){
        return this.genericDAO.byId(id);
    }//fim findById()

    @Transactional
    public T create(T entity){
        this.genericDAO.save(entity);
        return entity;
    }//fim create() - CRUD

    @Transactional
    public T update(T entity){
        this.genericDAO.update(entity);
        return entity;
    }//fim update() - CRUD

    @Transactional
    public T delete(T entity){
        this.genericDAO.delete(entity);
        return entity;
    }//fim delete() - CRUD

    @Transactional
    public List<T> findAll(){
        return this.genericDAO.findAll();
    }//fim findAll()

}//fim GenericService<T>
