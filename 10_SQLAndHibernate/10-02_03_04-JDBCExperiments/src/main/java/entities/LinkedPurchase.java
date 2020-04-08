package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

// Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"LinkedPurchaseListKey"})

@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchase implements Serializable {

    @EmbeddedId
    private Key LinkedPurchaseListKey;

    // Lombok
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = {"student_id", "course_id"})

    @Embeddable
    public static class Key implements Serializable {

        @OneToOne(mappedBy = "purchaseListKey.course")
        @JoinColumn(name = "student_id", referencedColumnName="id")
        int student_id;

        @OneToOne(mappedBy = "purchaseListKey.course")
        @JoinColumn(name = "course_id", referencedColumnName="id")
        int course_id;
    }
}
