//package entities;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import javax.persistence.Embeddable;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.OneToOne;
//import java.io.Serializable;
//
//// Lombok
//@Data
//@NoArgsConstructor
//@EqualsAndHashCode(of = {"key"})
//
//@Entity
//public class LinkedPurchaseList implements Serializable {
//
//    @EmbeddedId
//    private Key key;
//
//    // Lombok
//    @Data
//    @NoArgsConstructor
//    @EqualsAndHashCode(of = {"student_id", "course_id"})
//
//    @Embeddable
//    public static class Key implements Serializable {
//
//        @OneToOne
//        int student_id;
//
//        @OneToOne
//        int course_id;
//    }
//
//
//}
