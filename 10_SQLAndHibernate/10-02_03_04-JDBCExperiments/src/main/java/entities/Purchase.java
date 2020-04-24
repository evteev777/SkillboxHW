package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"pKey", "price", "subscriptionDate"})

@Entity
@Table(name = "PurchaseList")
public class Purchase {

    @EmbeddedId
    private Key pKey;

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

        @Override
        public String toString() {
            return String.format("%-45s\t%-35s", course, student);
        }
    }

    @Override
    public String toString() {
        return String.format("Purchase\t%s\t%8s\t%s", pKey, price, subscriptionDate);
    }
}
