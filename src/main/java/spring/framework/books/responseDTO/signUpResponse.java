package spring.framework.books.responseDTO;

import lombok.Getter;
import lombok.Setter;
import spring.framework.books.requestDTO.signUpDTO;

@Getter
@Setter
public class signUpResponse {

    private Long Id;

    private String name;

    private String dateOfBirth;

    private String accountCreationDate;

    private String email;

    private String password;
}
