package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

// Lombok
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"lpKey"})

@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchase implements Serializable {

    @EmbeddedId
    private Key lpKey;



    public LinkedPurchase(int courseId, int studentId) {
        this.lpKey = new Key(courseId, studentId);
    }

    @Override
    public String toString() {
        return lpKey.toString();
    }

    // Lombok
    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = {"courseId", "studentId"})

    @Embeddable
    public static class Key implements Serializable {

        @Column(name = "course_id")
        int courseId;

        @Column(name = "student_id")
        int studentId;

        public Key(int courseId, int studentId) {
            this.courseId = courseId;
            this.studentId = studentId;
        }

        @Override
        public String toString() {
            return "Course Id = " + courseId + "\t\tStudent Id = " + studentId;
        }
    }
}
