package entities;

import enums.CourseType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Lombok
@Data
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "type"})

@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    @NonNull
    private CourseType type;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    @Column(name = "students_count")
    private Integer studentsCount;

    private int price;

    @Column(name = "price_per_hour")
    private float pricePerHour;

    @ManyToMany
    @JoinTable(
            name = "Subscriptions",
            joinColumns = @JoinColumn(name="course_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName="id")
    )
    private List<Student> students = new ArrayList<>();
}
