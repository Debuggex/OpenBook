package spring.framework.books.services;

import org.springframework.stereotype.Service;
import spring.framework.books.classes.Book;
import spring.framework.books.repositories.BookVolumeRespository;
import spring.framework.books.repositories.AuthorRespository;
import spring.framework.books.repositories.BookRespository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DashboardServices {


    private final BookRespository bookRespository;
    private final AuthorRespository authorRespository;
    private final BookVolumeRespository bookVolumeRespository;

    public DashboardServices(BookRespository bookRespository, AuthorRespository authorRespository, BookVolumeRespository bookVolumeRespository) {
        this.bookRespository = bookRespository;
        this.authorRespository = authorRespository;
        this.bookVolumeRespository = bookVolumeRespository;
    }

    public List<Set<Book>> getAllBooks(){
        List<Set<Book>> bookList=new ArrayList<>();
        authorRespository.findAll().stream().filter(
                author -> bookList.add(author.getPublishedBooks())
        );

        return bookList;
    }

    
}
