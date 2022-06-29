package spring.framework.books.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.framework.books.classes.Book;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class signUpDTO {


    private String name;

    private String dateOfBirth;

    private String accountCreationDate;

    private String email;

    private String password;


}
