package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"subscriptionKey", "subscriptionDate"})

@Entity
@Table(name = "Subscriptions")
public class Subscription implements Serializable {

    @EmbeddedId
    private Key subscriptionKey;

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
        protected Student student;

        @ManyToOne(cascade = CascadeType.ALL)
        protected Course course;
    }
}
