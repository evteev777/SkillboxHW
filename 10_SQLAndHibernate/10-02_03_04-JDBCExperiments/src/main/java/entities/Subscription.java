package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"sbsKey", "subscriptionDate"})

@Entity
@Table(name = "Subscriptions")
public class Subscription implements Serializable {

    @EmbeddedId
    private Key sbsKey;

    @Column(name = "subscription_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscriptionDate;

    // Lombok
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = {"student", "course"})

    @Embeddable
    public static class Key implements Serializable {

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "student_id", referencedColumnName="id")
        protected Student student;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "course_id", referencedColumnName="id")
        protected Course course;
    }
}
