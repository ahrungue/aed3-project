package corn.daos;

import corn.generics.GenericFileDAO;
import corn.indexes.GenericIndex;
import corn.indexes.ScoreIndex;
import corn.models.Score;
import org.springframework.stereotype.Repository;

/**
 * @author rungue
 * @since 03/21/15.
 */
@Repository
public class ScoreDAO extends GenericFileDAO<Score> {

    @Override
    protected GenericIndex<Score> getGenericIndex() {
        return new ScoreIndex();
    }//end getGenericIndex()

}//end class Score
