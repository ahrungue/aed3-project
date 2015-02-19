package corn.generics;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Classe abstrata com as funcionalidades necessárias
 * para para realizar operações de CRUD e listagem de objetos.
 * @author Álvaro Rungue
 * @since 11/04/14.
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDAO<T>{

    private Class<T> persistentClass;

    @Autowired
    private SessionFactory customSessionFactory;


    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Session getCurrentSession() {
        return this.customSessionFactory.getCurrentSession();
    }

    public T byId(String id){
        try{
            return (T) this.getCurrentSession().get(this.persistentClass, id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }//fim byId()

    public T save(T t){
        try{
            this.getCurrentSession().save(t);
            return t;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }//fim save()

    public T update(T t){
        try{
            this.getCurrentSession().update(t);
        }catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }//fim update()

    public void delete(T t){
        try{
            this.getCurrentSession().delete(t);
        }catch(Exception e){
            e.printStackTrace();
        }
    }//fim delete()

    public List<T> findAll(){
        try{
            return this.getCurrentSession().createCriteria(this.persistentClass).list();
        }catch( Exception e ){
            e.printStackTrace();
        }
        return null;
    }//fim findAll()

    public List<T> eqExpressions(SimpleExpression ...simpleExpressions ){
        try{
            Criteria criteria = this.getCurrentSession().createCriteria(this.persistentClass);

            for (SimpleExpression simpleExpression : simpleExpressions) {
                criteria.add(simpleExpression);
            }

            return criteria.list();
        }catch( Exception e ){
            e.printStackTrace();
        }
        return null;
    }//fim findAll()

    public T byUniqueAttribute( final String attributeName, final String attributeValue ){
        try{
            return (T) this.getCurrentSession().createCriteria(this.persistentClass)
                    .add(Restrictions.eq(attributeName,attributeValue))
                    //Retornar apenas um Objeto ou null, pois as restrições são UNIQUE
                    .list().stream().findAny().orElse(null);
        }catch( Exception e ){
            e.printStackTrace();
        }
        return null;
    }//fim byUniqueAttribute()

}//Fim class GenericDAO<T>
