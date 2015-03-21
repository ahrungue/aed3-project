package corn.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author rungue
 * @since 03/21/15.
 */
@Entity
@Table( name = "scores")
public class Score implements Serializable {

    private static final long serialVersionUID = -1951852702144257201L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "score_id")
    private String id;

    @NotNull
    @Column(name = "prize_value", columnDefinition = "DOUBLE")
    private Double prizeValue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "score_created")
    private Date created;

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

}//end class Score
