package spring.framework.books.requestDTO;


import lombok.Getter;
import lombok.Setter;
import spring.framework.books.classes.BookVolume;


@Getter
@Setter
public class addBookDTO {

    private String bookName;

    private String serialNumber;

    private String bookPublishedDate;

    private Byte[] file;


    private Long authorId;

    private String bookVolume;

    private String bookType;
}
