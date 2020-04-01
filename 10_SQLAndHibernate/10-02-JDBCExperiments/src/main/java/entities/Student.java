package entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Lombok
@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name", "registrationDate"})

@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;

    private int age;

    @Column(name = "registration_date")
    @NonNull
    Date registrationDate;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses = new ArrayList<>();
}
