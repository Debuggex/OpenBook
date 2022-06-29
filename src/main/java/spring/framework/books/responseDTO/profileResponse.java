package spring.framework.books.responseDTO;

import lombok.Getter;
import lombok.Setter;
import spring.framework.books.classes.Author;
import spring.framework.books.classes.Book;

import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class profileResponse {

    private Long Id;

    private String name;

    private String dateOfBirth;

    private String accountCreationDate;

    private String email;

    private String password;


    private Set<Book> publishedBooks=new HashSet<>();


    private Byte[] profileImage;


}
