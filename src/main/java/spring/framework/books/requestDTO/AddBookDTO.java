package spring.framework.books.requestDTO;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddBookDTO {

    private String bookName;

    private String serialNumber;

    private String bookPublishedDate;

    private Byte[] file;


    private Long authorId;

    private String bookVolume;

    private String bookType;
}
