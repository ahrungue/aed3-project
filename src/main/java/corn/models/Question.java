package corn.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Álvaro Rungue
 * @since 04/01/15.
 */
@Entity
@Table(name = "questions")
public class Question implements Serializable {

	private static final long serialVersionUID = 5544025103039112307L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "question_id")
	private String id;

	@Column(name = "question")
	private String question;

	@Column(name = "answer")
	private Integer answer;

	@Column(name = "options")
	private String options;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

}//fim class StickyNote

