package spring.framework.books.classes;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
//@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    private Date dateOfBirth;

    private Date accountCreationDate;

    private String email;

    private String password;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<Book> publishedBooks=new HashSet<>();

    private String fileUri;

    public Author addBook(Book book){
        book.setAuthor(this);
        this.publishedBooks.add(book);
        return this;
    }

}
