package corn.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.Introspector;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alvaro Rungue
 * @since 30/11/14.
 */
@RestController
@SuppressWarnings("unchecked")
public abstract class GenericController<T> {

	private Class<T> persistentClass;

    @Autowired
    private GenericService<T> genericService;

    public GenericController() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> index(){
        return new HashMap<String, Object>(){{
	        put(Introspector.decapitalize(persistentClass.getSimpleName() + "s"), genericService.findAll());
        }};
    }//fim index()

    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> put( @Valid @RequestBody T t, BindingResult result ){
        Map<String, Object> map = new HashMap<String, Object>();
        //Verificar se ocorreu algum erro de mapeamento
        if( result.hasErrors() ){
            //Colocar no map a lista de campos que deram erro
            map.put("errors", result.getFieldErrors() );
            //Colocar individualmente cada campo que não foi inserido adequadamente
            result.getFieldErrors().forEach(e -> map.put(e.getField(), e.getRejectedValue()));
        }else{
            //Atualizar no banco o objeto
            this.genericService.update(t);
            //Retornar o objeto que foi atualizado
            map.put(Introspector.decapitalize(this.persistentClass.getSimpleName() + "s"), t);
        }//fim if-else
        return map;
    }//fim put()

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> post( @Valid @RequestBody T t, BindingResult result ){
        Map<String, Object> map = new HashMap<String, Object>();
        //Verificar se ocorreu algum erro de mapeamento
        if( result.hasErrors() ){
            //Colocar no map a lista de campos que deram erro
            map.put("errors", result.getFieldErrors() );
            //Colocar individualmente cada campo que não foi inserido adequadamente
            result.getFieldErrors().forEach(e -> map.put(e.getField(), e.getRejectedValue()));
        }else{
            //Atualizar no banco o objeto
            this.genericService.create(t);
            //Retornar o objeto que foi atualizado
            map.put( this.persistentClass.getSimpleName(), t);
        }//fim if-else
        return map;
    }//fim post()

    @RequestMapping(method = RequestMethod.DELETE)
    public Map<String, Object> delete( @RequestParam String id ){
        Map<String, Object> map = new HashMap<String, Object>();
//        if( result.hasErrors() ){
//            map.put("errors",result.getFieldErrors());
//        }else{
            try{
                this.genericService.delete(genericService.findById(id));
            }catch (AccessDeniedException e) {
                map.put("error", HttpStatus.FORBIDDEN);
            }//fim try-catch
//        }//fim if-else
        return map;
    }//fim delete()

}//fim class GenericController
