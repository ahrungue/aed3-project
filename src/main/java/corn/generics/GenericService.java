package corn.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author alvaro
 * @since 29/11/14.
 */
@Service
public abstract class GenericService<T extends Serializable>{

    @Autowired
    private GenericFileDAO<T> genericFileDAO;

    @Transactional
    public T findById(String id){
        return this.genericFileDAO.byId(id);
    }//fim findById()

    @Transactional
    public T create(T entity){
        this.genericFileDAO.save(entity);
        return entity;
    }//fim create() - CRUD

    @Transactional
    public T update(T entity){
        this.genericFileDAO.update(entity);
        return entity;
    }//fim update() - CRUD

    @Transactional
    public T delete(T entity){

        try {
            Field id = entity.getClass().getField("id");
            id.setAccessible(true);
            this.genericFileDAO.delete(id.get(entity).toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return entity;
    }//fim delete() - CRUD

    @Transactional
    public List<T> findAll(){
        return this.genericFileDAO.findAll();
    }//fim findAll()

}//fim GenericService<T>
