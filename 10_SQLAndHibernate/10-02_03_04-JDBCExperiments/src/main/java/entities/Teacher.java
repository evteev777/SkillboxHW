package entities;

import lombok.*;
import javax.persistence.*;
import java.util.List;

// Lombok
@Data
@NoArgsConstructor
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

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Course> courses;

    @Override
    public String toString() {
        return "Teacher " + id + ". " + name;
    }
}
