package kz.bailanysta.main.module.auth.confirmation_code;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Beksultan
 */
@Getter
@Setter
@Entity
@Table(name = "confirmation_code")
@Accessors(chain = true)
public class ConfirmationCode {

    public static final int EXPIRATION_MINUTES = 15; // minutes
    public static final int MAX_ATTEMPTS_COUNT = 3;
    public static final int MAX_DESTINATION_LENGTH = 255;


    public enum Type {
        EMAIL, PHONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "attempts")
    private int attempts;

    @Column(name = "create_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "codes")
    private String codes;

    @Column(name = "last_sent_time", nullable = false)
    private LocalDateTime lastSentTime;

    /*
     * Helpers
     */
    public List<Integer> getCodesList() {
        return new Gson().fromJson(this.codes, new TypeToken<List<Integer>>() { }.getType());
    }

    public void addNewCode(Integer code) {
        // user can enter the code 3 times
        this.attempts = 0;

        // save code in list
        List<Integer> codes = getCodesList();
        codes.add(code);
        this.codes = codes.toString();
    }

    /*
     * Entity Basics
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConfirmationCode that = (ConfirmationCode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
