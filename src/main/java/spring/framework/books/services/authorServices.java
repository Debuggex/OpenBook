package spring.framework.books.services;

import org.springframework.stereotype.Service;
import spring.framework.books.classes.Author;
import spring.framework.books.classes.Book;
import spring.framework.books.repositories.authorRespository;
import spring.framework.books.requestDTO.loginDTO;
import spring.framework.books.requestDTO.signUpDTO;
import spring.framework.books.responseDTO.loginResponse;
import spring.framework.books.responseDTO.profileResponse;
import spring.framework.books.responseDTO.responseConstants;
import spring.framework.books.responseDTO.signUpResponse;
import spring.framework.books.security.loginSecurity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class authorServices {


    private final authorRespository AUTHOR_RESPOSITORY;

    public authorServices(authorRespository authorRespository) {

        AUTHOR_RESPOSITORY=authorRespository;

    }

    public signUpResponse newUser(signUpDTO signUpDTO) throws ParseException {

       signUpResponse signUpResponse=new signUpResponse();
       Optional<spring.framework.books.classes.Author> author= AUTHOR_RESPOSITORY.findAll().stream().filter(author1 -> author1.getEmail().equals(signUpDTO.getEmail())).findFirst();
       if (author.isEmpty()){

           Author author1=new Author();
           author1.setName(signUpDTO.getName());
           author1.setAccountCreationDate(new SimpleDateFormat("dd/MM/yyyy").parse(signUpDTO.getAccountCreationDate()));
           author1.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(signUpDTO.getDateOfBirth()));
           author1.setEmail(signUpDTO.getEmail());
           loginDTO loginDTO=new loginDTO();
           loginDTO.setEmail(signUpDTO.getEmail());
           loginDTO.setPassword(signUpDTO.getPassword());
           loginSecurity loginSecurity=new loginSecurity();
           author1.setPassword(loginSecurity.generateToken(loginDTO));
           Author author2= AUTHOR_RESPOSITORY.save(author1);
           signUpDTO.setPassword(author2.getPassword());

           signUpResponse.setId(author2.getId());
           signUpResponse.setName(author2.getName());
           signUpResponse.setAccountCreationDate(author2.getAccountCreationDate().toString());
           signUpResponse.setDateOfBirth(author2.getDateOfBirth().toString());
           signUpResponse.setEmail(author2.getEmail());
           return signUpResponse;
       }

        return null;
    }



    public profileResponse findByEmail(String email){
        Optional<Author> author=AUTHOR_RESPOSITORY.findAll().stream().filter(author1 -> author1.getEmail().equals(email)).findFirst();
        profileResponse profileResponse=new profileResponse();

        profileResponse.setId(author.get().getId());
        profileResponse.setName(author.get().getName());
        profileResponse.setDateOfBirth(author.get().getDateOfBirth().toString());
        profileResponse.setEmail(author.get().getEmail());
        HashSet<Book> books= new HashSet<>();
        books.addAll(author.get().getPublishedBooks());
        profileResponse.setPublishedBooks(books);
        profileResponse.setAccountCreationDate(author.get().getAccountCreationDate().toString());
        profileResponse.setPassword(new loginSecurity().verifyToken(author.get().getPassword()));


        return profileResponse;

    }

    public loginResponse loginAuthor(loginDTO loginDTO){
        Optional<Author> author=AUTHOR_RESPOSITORY.findAll().stream().filter(author1 -> author1.getEmail().equals(loginDTO.getEmail())).findFirst();
        loginResponse loginResponse=new loginResponse();
        loginSecurity loginSecurity=new loginSecurity();
        if (author.isPresent()){
            if (loginSecurity.verifyToken(author.get().getPassword()).equals(loginDTO.getPassword())){
                loginResponse.setToken(loginSecurity.generateToken(loginDTO));
            }
        }
        loginResponse.setLoginTime(new Date().toString());
        return loginResponse;
    }

    public profileResponse deleteAuthor(loginDTO loginDTO){
        Optional<Author> author=AUTHOR_RESPOSITORY.findAll().stream().filter(
                author1 -> author1.getEmail().equals(loginDTO.getEmail())
        ).findFirst();

        if (author.isPresent()){
            AUTHOR_RESPOSITORY.deleteById(author.get().getId());
            profileResponse profileResponse=new profileResponse();
            profileResponse.setEmail(author.get().getEmail());
            profileResponse.setName(author.get().getName());
            profileResponse.setPublishedBooks(author.get().getPublishedBooks());
            return profileResponse;
        }
        return null;
    }
}
