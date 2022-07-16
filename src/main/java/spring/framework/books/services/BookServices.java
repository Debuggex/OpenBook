package spring.framework.books.services;

import org.apache.commons.io.FilenameUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.framework.books.classes.Author;
import spring.framework.books.classes.Book;
import spring.framework.books.classes.BookVolume;
import spring.framework.books.repositories.BookVolumeRespository;
import spring.framework.books.repositories.AuthorRespository;
import spring.framework.books.repositories.BookRespository;
import spring.framework.books.requestDTO.AddBookDTO;
import spring.framework.books.responseDTO.AddBookResponse;

import javax.transaction.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class BookServices {


    private final BookRespository bookRespository;
    private final AuthorRespository authorRespository;

    private final BookVolumeRespository bookVolumeRespository;

    private Path foundfile;

    public BookServices(BookRespository bookRespository, AuthorRespository authorRespository, BookVolumeRespository bookVolumeRespository) {
        this.bookRespository = bookRespository;
        this.authorRespository = authorRespository;
        this.bookVolumeRespository = bookVolumeRespository;
    }

    @Transactional
    public AddBookResponse addBook(AddBookDTO addBookDTO, MultipartFile multipartFile) throws ParseException, IOException {

        AddBookResponse addBookResponse=new AddBookResponse();

        Optional<Book> optionalAuthor=authorRespository.findAll().iterator().next().
                getPublishedBooks().stream().filter(
                        book -> book.getBookName().equals(addBookDTO.getBookName())
                ).findFirst();

        if (optionalAuthor.isPresent()){
            Author author=optionalAuthor.get().getAuthor();
            if (!author.getId().equals(addBookDTO.getAuthorId())){
                addBookResponse.setMessage("Book is already being added by different Author." +
                        "Please Change the Book Name.");
                return addBookResponse;
            }
        }



        Optional<Author>author=authorRespository.findById(addBookDTO.getAuthorId());

        //Checking if Author Present
        if (author.isEmpty()){
            addBookResponse.setMessage("Please add a Author first.");
            return addBookResponse;
        }
        Author fetchAuthor=author.get();
        Book book=new Book();
        Optional<Book> bookOptional=author.get().getPublishedBooks().stream().filter(book2 ->
                book2.getBookName().equals(addBookDTO.getBookName())).findFirst();

        //Checking if Book Exists
        Path path = Path.of("Files-Upload");
        if (bookOptional.isPresent()){
            Optional<BookVolume> bookVolume=bookOptional.get().getVolume().stream().filter(
                    bookVolume1 -> bookVolume1.getVolume().equals(addBookDTO.getBookVolume())
            ).findFirst();

            //Checking if Volume Exists
            if (bookVolume.isPresent()){
                addBookResponse.setMessage("The Book with Same Volume is already Presenet." +
                        "Please add a different Book or Volume!");
                return addBookResponse;
            }

            BookVolume bookVolume1=new BookVolume();
            bookVolume1.setVolume(addBookDTO.getBookVolume());

            //Files Upload Start ======================================
            if (!Files.exists(path)) {
                Files.createDirectories(path);

            }

            InputStream inputStream = multipartFile.getInputStream();
            String fileName=bookOptional.get().getBookName()+"_"+addBookDTO.getBookVolume()+"_BY_"+fetchAuthor.getName()+"."+FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            Path filePath= path.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);

            //Files Upload End =========================================

            bookVolume1.setFileUri(fileName);
            BookVolume savedVolume=bookVolumeRespository.save(bookVolume1);


            bookOptional.get().addVolume(savedVolume);
            bookOptional.get().setBookType(addBookDTO.getBookType());





            Book savedBook=bookRespository.save(bookOptional.get());
            addBookResponse.setAuthorName(fetchAuthor.getName());
            addBookResponse.setBookName(savedBook.getBookName());
            addBookResponse.setBookPublishedDate(savedBook.getBookPublishedDate().toString());
            addBookResponse.setSerialNumber(savedBook.getSerialNumber());
            addBookResponse.setId(savedBook.getId());
            addBookResponse.setBookVolume(savedVolume);
            addBookResponse.setBookType(savedBook.getBookType());
            addBookResponse.setFileUri(savedVolume.getFileUri());
            addBookResponse.setMessage("Book Added Successfully");
            return addBookResponse;

        }else{
            book.setBookName(addBookDTO.getBookName());
            book.setSerialNumber(addBookDTO.getSerialNumber());
            book.setBookPublishedDate(new SimpleDateFormat("dd/MM/yyyy").parse(addBookDTO.getBookPublishedDate()));
            book.setAuthor(fetchAuthor);
            book.setBookType(addBookResponse.getBookType());

            BookVolume bookVolume=new BookVolume();
            bookVolume.setVolume(addBookDTO.getBookVolume());

            //Files Upload Start ======================================
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            InputStream inputStream = multipartFile.getInputStream();
            String fileName=addBookDTO.getBookName()+"_"+addBookDTO.getBookVolume()+"_BY_"+fetchAuthor.getName()+"."+FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            Path filePath= path.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);

            //Files Upload End =========================================
            bookVolume.setFileUri(fileName);





            BookVolume savedVolume=bookVolumeRespository.save(bookVolume);
            book.addVolume(savedVolume);
            book.setBookType(addBookDTO.getBookType());

            Book savedBook=bookRespository.save(book);
            Author savedAuthor= fetchAuthor.addBook(savedBook);
            addBookResponse.setAuthorName(savedAuthor.getName());
            addBookResponse.setBookName(savedBook.getBookName());
            addBookResponse.setBookPublishedDate(savedBook.getBookPublishedDate().toString());
            addBookResponse.setSerialNumber(savedBook.getSerialNumber());
            addBookResponse.setId(savedBook.getId());
            addBookResponse.setBookVolume(savedVolume);
            addBookResponse.setBookType(savedBook.getBookType());
            addBookResponse.setFileUri("/downloadFile/"+savedVolume.getFileUri());
            addBookResponse.setMessage("Book Added Successfully");
        }


        return addBookResponse;

    }

    public Path getFileResource(String FileName) throws IOException {

        Path dirPath=Paths.get("D:/Spring Projects/Books//Files-Upload");

        Files.list(dirPath).forEach(
                file->{
                    if(file.getFileName().toString().equals(FileName+".pdf")){
                        foundfile=file;
                    }
                }
        );
        if (foundfile!=null){
            return foundfile;
        }
        return null;
    }

//    private AddBookResponse getAddBookResponse(addBookDTO addBookDTO, AddBookResponse addBookResponse, Author fetchAuthor, Book book, BookVolume bookVolume1) throws ParseException {
//
//        if (book.getId()==null){
//            book.setBookName(addBookDTO.getBookName());
//            book.setSerialNumber(addBookDTO.getSerialNumber());
//            book.setBookPublishedDate(new SimpleDateFormat("dd/MM/yyyy").parse(addBookDTO.getBookPublishedDate()));
//            book.setAuthor(fetchAuthor);
//
//            BookVolume bookVolume=new BookVolume();
//            bookVolume.setVolume(addBookDTO.getBookVolume());
//            BookVolume savedVolume=bookVolumeRespository.save(bookVolume);
//            book.addVolume(savedVolume);
//
//            Book savedBook=bookRespository.save(book);
//            fetchAuthor.addBook(book);
//            Author savedAuthor=authorRespository.save(fetchAuthor);
//
//
//        }else {
//            addBookResponse.setAuthorName(book.getAuthor().getName());
//            addBookResponse.setBookName(book.getBookName());
//            addBookResponse.setBookPublishedDate(book.getBookPublishedDate().toString());
//            addBookResponse.setSerialNumber(book.getSerialNumber());
//            addBookResponse.setId(book.getId());
//            addBookResponse.setBookVolume(bookVolume1);
//            addBookResponse.setMessage("Book Added Successfully");
//        }
//
//
//
//
//        return addBookResponse;
//    }
}
