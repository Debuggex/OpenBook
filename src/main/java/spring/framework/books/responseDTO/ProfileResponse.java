package spring.framework.books.responseDTO;

import lombok.Getter;
import lombok.Setter;
import spring.framework.books.classes.Book;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProfileResponse {

    private Long Id;

    private String name;

    private String dateOfBirth;

    private String accountCreationDate;

    private String email;

    private String password;


    private Set<Book> publishedBooks=new HashSet<>();


    private Byte[] profileImage;


}
