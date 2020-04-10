package entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import enums.CourseType;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "type"})

@Entity
@Table(name = "Courses")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int duration;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private CourseType type;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Teacher teacher;

    @Column(name = "students_count")
    private Integer studentsCount;

    private int price;

    @Column(name = "price_per_hour")
    private float pricePerHour;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Subscriptions",
            joinColumns = @JoinColumn(name="course_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName="id"))
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "subscriptionKey.course", fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "purchaseKey.course", fetch = FetchType.LAZY)
    private List<Purchase> purchases;
}
