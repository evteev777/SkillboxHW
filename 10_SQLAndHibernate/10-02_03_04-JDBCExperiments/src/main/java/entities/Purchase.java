package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"purchaseListKey", "price", "subscriptionDate"})

@Entity
@Table(name = "PurchaseList")
public class Purchase {

    @EmbeddedId
    private Key purchaseListKey;

    private int price;

    @Column(name = "subscription_date")
    Date subscriptionDate;

    // Lombok
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = {"student", "course"})

    @Embeddable
    public static class Key implements Serializable {

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "student_name", referencedColumnName="name")
        protected Student student;

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "course_name", referencedColumnName="name")
        protected Course course;
    }
}
