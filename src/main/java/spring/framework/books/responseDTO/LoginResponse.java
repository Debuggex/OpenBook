package spring.framework.books.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;
    private String loginTime;
}
