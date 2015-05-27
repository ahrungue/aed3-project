package corn.daos;

import corn.generics.GenericFileDAO;
import corn.indexes.GenericIndex;
import corn.indexes.QuestionIndex;
import corn.models.Question;
import org.springframework.stereotype.Repository;

/**
 * @author Álvaro Rungue
 * @since 04/01/15.
 */
@Repository
public class QuestionDAO extends GenericFileDAO<Question> {

    @Override
    protected GenericIndex<Question> getGenericIndex() {
        return new QuestionIndex();
    }//end getGenericIndex()

}//fim class StickyNoteDAO