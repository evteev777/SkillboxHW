package entities;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

//Lombok
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"student", "course", "price", "subscriptionDate"})

@Entity
@Table(name = "PurchaseList")
public class Purchase {

    @Id
    @Column(name = "student_name")
    private Student student;

    @Id
    @Column(name = "course_name")
    private Course course;

    private int price;

    @Column(name = "subscription_date")
    @Temporal(TemporalType.DATE)
    Date subscriptionDate;
}
