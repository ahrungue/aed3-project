package corn.controllers;

import corn.generics.GenericController;
import corn.models.Question;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alvaro on 16/02/15.
 */
@RestController
@RequestMapping(value = "/rest/question")
public class QuestionController extends GenericController<Question> {

}//fim class QuestionController
