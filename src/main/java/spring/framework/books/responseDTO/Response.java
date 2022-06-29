package spring.framework.books.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<ResponseType> {

    private int responseCode;
    private String responseMessage;
    private ResponseType responseBody;

}
