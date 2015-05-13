package corn.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
* @author rungue
* @since 05/12/15
*/
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface SerialAttribute {

    int size();

}//end Annotation SerialAttribute
