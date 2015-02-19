package corn.daos;

import corn.generics.GenericDAOTest;
import corn.models.Question;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Álvaro Rungue
 * @since 10/01/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring.xml"})
@WebAppConfiguration
@ActiveProfiles(profiles = "test")
public class QuestionDAOTest extends GenericDAOTest<Question> {

	@Override
	public Question getObjectToTest() {
        Question question = new Question();
        question.setOptions("Certa;Errada;Errada;Errada");
        question.setAnswer(0);
        question.setQuestion("Pergunta");
		return question;
	}//fim getObjectToTest()

}//fim class StickyNoteDAOTest
