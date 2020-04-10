package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

// Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"LinkedPurchaseKey"})

@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchase implements Serializable {

    @EmbeddedId
    private Key LinkedPurchaseKey;

    // Lombok
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = {"studentId", "courseId"})

    @Embeddable
    public static class Key implements Serializable {


        @Column(name = "student_id")
//        @OneToOne(mappedBy = "purchaseKey.student")
//        @JoinColumn(name = "student_id", referencedColumnName="id")
        int studentId;

        @Column(name = "course_id")
//        @OneToOne(mappedBy = "purchaseKey.course")
//        @JoinColumn(name = "course_id", referencedColumnName="id")
        int courseId;
    }
}
