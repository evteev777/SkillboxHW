package entities;

import lombok.*;
import javax.persistence.*;
import java.util.List;

// Lombok
@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})

@Entity
@Table(name = "Teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;

    private int salary;

    private int age;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
}
