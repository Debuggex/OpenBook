package spring.framework.books.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class signUpDTO {


    private String name;

    private String dateOfBirth;

    private String accountCreationDate;

    private String email;

    private String password;

    private String typeOfUser;


}
