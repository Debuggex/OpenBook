package spring.framework.books.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Types_Of_Users")
public class TypesOfUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;
}
