package corn.models;


import corn.annotations.SerialAttribute;
import corn.annotations.SerialClass;
import corn.interfaces.BytesConvertable;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Álvaro Rungue
 * @since 04/01/15.
 */
@SerialClass(size = 640 )
public class Question implements Serializable, BytesConvertable<Question> {

	private static final long serialVersionUID = 5544025103039112307L;

    @SerialAttribute(size = 36)
	private String id;

    @SerialAttribute(size = 400)
	private String question;

    @SerialAttribute(size = 200)
	private String options;

    @SerialAttribute(size = 4)
	private Integer answer;

    public Question(){
        this(null, null, null, null);
    }//end Question()

    public Question(String id, String question, String options, Integer answer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.answer = answer;
    }//end Question()

    public Question( byte[] bytes ){
        this.id         = new String(ArrayUtils.subarray(bytes, 0, 36)).trim();
        this.question   = new String(ArrayUtils.subarray(bytes, 36, 436)).trim();
        this.options    = new String(ArrayUtils.subarray(bytes, 436, 636)).trim();
        this.answer     = ByteBuffer.wrap(ArrayUtils.subarray(bytes, 636, 640)).getInt();

    }//end Question()

    @Override
    public byte[] toBytes() {
        byte[] objectBytes = ArrayUtils.EMPTY_BYTE_ARRAY;

        try {
            int idByteSize       = Question.class.getDeclaredField("id").getAnnotation(SerialAttribute.class).size();
            int questionByteSize = Question.class.getDeclaredField("question").getAnnotation(SerialAttribute.class).size();
            int optionsByteSize  = Question.class.getDeclaredField("options").getAnnotation(SerialAttribute.class).size();
            int answerByteSize   = Question.class.getDeclaredField("answer").getAnnotation(SerialAttribute.class).size();


            byte[] idByte        = Arrays.copyOf(id.getBytes(), idByteSize);
            byte[] questionByte  = Arrays.copyOf(question.getBytes(), questionByteSize);
            byte[] optionsByte   = Arrays.copyOf(options.getBytes(), optionsByteSize);

            byte[] answerByte    = new byte[answerByteSize];
            ByteBuffer.wrap(answerByte).putInt(answer);

            objectBytes = ArrayUtils.addAll(objectBytes, idByte);
            objectBytes = ArrayUtils.addAll(objectBytes, questionByte);
            objectBytes = ArrayUtils.addAll(objectBytes, optionsByte);
            objectBytes = ArrayUtils.addAll(objectBytes, answerByte);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return objectBytes;
    }//end toBytes()

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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question1 = (Question) o;

        if (id != null ? !id.equals(question1.id) : question1.id != null) return false;
        if (question != null ? !question.equals(question1.question) : question1.question != null) return false;
        if (options != null ? !options.equals(question1.options) : question1.options != null) return false;
        return !(answer != null ? !answer.equals(question1.answer) : question1.answer != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

}//fim class StickyNote

