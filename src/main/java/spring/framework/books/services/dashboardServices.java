package spring.framework.books.services;

import org.springframework.stereotype.Service;
import spring.framework.books.repositories.BookVolumeRespository;
import spring.framework.books.repositories.authorRespository;
import spring.framework.books.repositories.bookRespository;

@Service
public class dashboardServices {


    private final bookRespository bookRespository;
    private final authorRespository authorRespository;
    private final BookVolumeRespository bookVolumeRespository;

    public dashboardServices(spring.framework.books.repositories.bookRespository bookRespository, spring.framework.books.repositories.authorRespository authorRespository, BookVolumeRespository bookVolumeRespository) {
        this.bookRespository = bookRespository;
        this.authorRespository = authorRespository;
        this.bookVolumeRespository = bookVolumeRespository;
    }

    
}
