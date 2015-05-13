package corn.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author rungue
 * @since 05/13/15
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface SerialClass {

    int size();

}//end Annotation SerialClass