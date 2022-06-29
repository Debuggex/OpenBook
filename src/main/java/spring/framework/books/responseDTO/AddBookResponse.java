package spring.framework.books.responseDTO;


import lombok.Getter;
import lombok.Setter;
import spring.framework.books.classes.Author;
import spring.framework.books.classes.BookVolume;

@Getter
@Setter
public class AddBookResponse {

    private Long Id;

    private String bookName;

    private String serialNumber;

    private String bookPublishedDate;

    private String fileUri;

    private String authorName;

    private BookVolume bookVolume;

    private String message;

    private String bookType;
}
