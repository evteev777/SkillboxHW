package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "Subscriptions")
public class Subscription implements Serializable {

    @EmbeddedId
    private SubscriptionKey key;

    @Column(name = "subscription_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscriptionDate;

    // Lombok
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode

    @Embeddable
    public static class SubscriptionKey implements Serializable{

        @ManyToOne(cascade = CascadeType.ALL)
        protected Student student;

        @ManyToOne(cascade = CascadeType.ALL)
        protected Course course;
    }
}
