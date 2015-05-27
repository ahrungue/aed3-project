package corn.models;

import corn.annotations.SerialAttribute;
import corn.annotations.SerialClass;
import corn.interfaces.BytesConvertable;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;

/**
 * @author rungue
 * @since 03/21/15.
 */
@SerialClass(size = 52)
public class Score implements Serializable, BytesConvertable<Score> {

    private static final long serialVersionUID = -1951852702144257201L;

    @SerialAttribute(size = 36)
    private String id;

    @SerialAttribute(size = 8)
    private Double prizeValue;

    @SerialAttribute(size = 8)
    private Date created;

    public Score() {
        this(null, null, null);
    }

    public Score(String id, Double prizeValue, Date created) {
        this.id = id;
        this.prizeValue = prizeValue;
        this.created = created;
    }

    public Score( byte[] bytes ){
        this.id         = new String(ArrayUtils.subarray(bytes, 0, 36)).trim();
        this.prizeValue = ByteBuffer.wrap( ArrayUtils.subarray(bytes, 36, 44) ).getDouble();
        this.created    = new Date(ByteBuffer.wrap( ArrayUtils.subarray(bytes, 44, 52) ).getLong());
    }//end Score()

    @Override
    public byte[] toBytes() {
        byte[] objectBytes = ArrayUtils.EMPTY_BYTE_ARRAY;

        try {
            int idByteSize          = Score.class.getDeclaredField("id").getAnnotation(SerialAttribute.class).size();
            int prizeValueByteSize  = Score.class.getDeclaredField("prizeValue").getAnnotation(SerialAttribute.class).size();
            int createdByteSize     = Score.class.getDeclaredField("created").getAnnotation(SerialAttribute.class).size();

            byte[] idByte           = Arrays.copyOf(id.getBytes(), idByteSize);

            byte[] prizeValueBytes  = new byte[prizeValueByteSize];
            ByteBuffer.wrap(prizeValueBytes).putDouble(prizeValue);

            byte[] createdBytes     = new byte[createdByteSize];
            ByteBuffer.wrap(createdBytes).putLong(created.getTime());

            objectBytes = ArrayUtils.addAll(objectBytes, idByte);
            objectBytes = ArrayUtils.addAll(objectBytes, prizeValueBytes);
            objectBytes = ArrayUtils.addAll(objectBytes, createdBytes);

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

    public Double getPrizeValue() {
        return prizeValue;
    }

    public void setPrizeValue(Double prizeValue) {
        this.prizeValue = prizeValue;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score)) return false;

        Score score = (Score) o;

        if (id != null ? !id.equals(score.id) : score.id != null) return false;
        if (prizeValue != null ? !prizeValue.equals(score.prizeValue) : score.prizeValue != null) return false;
        return !(created != null ? !created.equals(score.created) : score.created != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (prizeValue != null ? prizeValue.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

}//end class Score
