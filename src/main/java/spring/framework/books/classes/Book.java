package spring.framework.books.classes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookName;

    private String serialNumber;

    private Date bookPublishedDate;

    private Long numberOfDownloads;




    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="authorId")
    private Author author;



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bookId")
    private Set<BookVolume> volume=new HashSet<>();


    private String BookType;



    public Book addVolume(BookVolume bookVolume){
        bookVolume.setBookId(this);
        this.volume.add(bookVolume);
        return this;
    }
}
