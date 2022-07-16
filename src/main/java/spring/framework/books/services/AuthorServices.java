package spring.framework.books.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.framework.books.classes.Author;
import spring.framework.books.classes.Book;
import spring.framework.books.classes.TypesOfUsers;
import spring.framework.books.repositories.RoleRepository;
import spring.framework.books.repositories.AuthorRespository;
import spring.framework.books.requestDTO.LoginDTO;
import spring.framework.books.requestDTO.SignUpDTO;
import spring.framework.books.responseDTO.LoginResponse;
import spring.framework.books.responseDTO.ProfileResponse;
import spring.framework.books.responseDTO.SignUpResponse;
import spring.framework.books.security.loginSecurity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class AuthorServices implements UserDetailsService {


    private final AuthorRespository AUTHOR_RESPOSITORY;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public AuthorServices(AuthorRespository author_respository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        AUTHOR_RESPOSITORY = author_respository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public SignUpResponse newUser(SignUpDTO signUpDTO) throws ParseException {

       SignUpResponse signUpResponse=new SignUpResponse();
       Optional<spring.framework.books.classes.Author> author= AUTHOR_RESPOSITORY.findAll().stream().filter(author1 -> author1.getEmail().equals(signUpDTO.getEmail())).findFirst();
       if (author.isEmpty()){

           Author author1=new Author();
           author1.setName(signUpDTO.getName());
           author1.setAccountCreationDate(new SimpleDateFormat("dd/MM/yyyy").parse(signUpDTO.getAccountCreationDate()));
           author1.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(signUpDTO.getDateOfBirth()));
           author1.setEmail(signUpDTO.getEmail());
           author1.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

           if (signUpDTO.getTypeOfUser().equals("Both")){
               TypesOfUsers typesOfUsers1=new TypesOfUsers();
               typesOfUsers1.setUserType("Reader");
               TypesOfUsers savedType= roleRepository.save(typesOfUsers1);


               TypesOfUsers typesOfUsers2=new TypesOfUsers();
               typesOfUsers2.setUserType("Author");
               TypesOfUsers savedType1=roleRepository.save(typesOfUsers2);
               author1.addType(savedType);
               author1.addType(savedType1);
           }else{
               TypesOfUsers typesOfUsers1=new TypesOfUsers();
               typesOfUsers1.setUserType(signUpDTO.getTypeOfUser());
               TypesOfUsers savedType= roleRepository.save(typesOfUsers1);
               author1.addType(savedType);
           }
           Author author2= AUTHOR_RESPOSITORY.save(author1);

           signUpResponse.setPassword(author2.getPassword());
           signUpResponse.setId(author2.getId());
           signUpResponse.setName(author2.getName());
           signUpResponse.setAccountCreationDate(author2.getAccountCreationDate().toString());
           signUpResponse.setDateOfBirth(author2.getDateOfBirth().toString());
           signUpResponse.setEmail(author2.getEmail());
           signUpResponse.setTypeOfUser(author2.getTypesOfUsers());
           return signUpResponse;
       }

        return null;
    }



    public ProfileResponse findByEmail(String email){
        Optional<Author> author=AUTHOR_RESPOSITORY.findAll().stream().filter(author1 -> author1.getEmail().equals(email)).findFirst();
        ProfileResponse profileResponse=new ProfileResponse();

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

    public LoginResponse loginAuthor(LoginDTO loginDTO){
        Optional<Author> author=AUTHOR_RESPOSITORY.findAll().stream().filter(author1 -> author1.getEmail().equals(loginDTO.getEmail())).findFirst();
        LoginResponse loginResponse=new LoginResponse();
        loginSecurity loginSecurity=new loginSecurity();
        if (author.isPresent()){
            if (loginSecurity.verifyToken(author.get().getPassword()).equals(loginDTO.getPassword())){
                loginResponse.setToken(loginSecurity.generateToken(loginDTO));
            }
        }
        loginResponse.setLoginTime(new Date().toString());
        return loginResponse;
    }

    public ProfileResponse deleteAuthor(LoginDTO loginDTO){
        Optional<Author> author=AUTHOR_RESPOSITORY.findAll().stream().filter(
                author1 -> author1.getEmail().equals(loginDTO.getEmail())
        ).findFirst();

        if (author.isPresent()){
            AUTHOR_RESPOSITORY.deleteById(author.get().getId());
            ProfileResponse profileResponse=new ProfileResponse();
            profileResponse.setEmail(author.get().getEmail());
            profileResponse.setName(author.get().getName());
            profileResponse.setPublishedBooks(author.get().getPublishedBooks());
            return profileResponse;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<Author> author=AUTHOR_RESPOSITORY.findById(AUTHOR_RESPOSITORY.findAll().stream().filter(
                author1 -> author1.getEmail().equals(username)
        ).findFirst().get().getId());

        if (author.isEmpty()){
            throw new UsernameNotFoundException("Author with this name does not exist");
        }else {
            Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
            author.get().getTypesOfUsers().forEach(
                    user -> authorities.add(new SimpleGrantedAuthority(user.getUserType()))
            );

            return new org.springframework.security.core.userdetails.User(author.get().getEmail(),author.get().getPassword(),authorities);
        }

    }
}
