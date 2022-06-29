package spring.framework.books.responseDTO;

import lombok.Getter;
import lombok.Setter;
import spring.framework.books.requestDTO.loginDTO;

@Getter
@Setter
public class loginResponse {

    private String token;
    private String loginTime;
}
