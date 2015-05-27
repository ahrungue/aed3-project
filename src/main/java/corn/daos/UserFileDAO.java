package corn.daos;

import corn.generics.GenericFileDAO;
import corn.indexes.GenericIndex;
import corn.indexes.UserIndex;
import corn.models.User;
import org.springframework.stereotype.Repository;

/**
 * @author rungue
 * @since 05/11/15
 */
@Repository
public class UserFileDAO extends GenericFileDAO<User> {

    @Override
    protected GenericIndex<User> getGenericIndex() {
        return new UserIndex();
    }//end getGenericIndex()

}//end class UserFileDAO
