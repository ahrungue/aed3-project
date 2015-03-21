package corn.controllers;

import corn.models.Score;
import corn.generics.GenericController;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * @author rungue
 * @since 03/21/15.
 */
@RestController
@RequestMapping(value = "/rest/score")
public class ScoreController extends GenericController<Score> {

    @Override
    public Map<String, Object> post(@Valid @RequestBody Score score, BindingResult result) {
        score.setCreated(new Date());
        return super.post(score, result);
    }//end post()

}//end class Score
