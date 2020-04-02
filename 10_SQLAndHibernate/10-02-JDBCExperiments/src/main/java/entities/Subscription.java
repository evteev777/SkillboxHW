package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// Lombok
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"student", "course", "subscriptionDate"})

@Entity
@Table(name = "Subscriptions")
public class Subscription implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    @Column(name = "subscription_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date subscriptionDate;
}
