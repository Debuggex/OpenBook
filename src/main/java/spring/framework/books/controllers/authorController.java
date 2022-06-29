package spring.framework.books.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.framework.books.classes.Author;
import spring.framework.books.requestDTO.loginDTO;
import spring.framework.books.requestDTO.signUpDTO;
import spring.framework.books.requestDTO.profileDTO;
import spring.framework.books.responseDTO.*;
import spring.framework.books.security.loginSecurity;
import spring.framework.books.services.authorServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/author")
public class authorController {

    private final authorServices AuthorService;

    public authorController(authorServices authorService) {
        AuthorService = authorService;
    }

    @PostMapping("/register")
    public @ResponseBody Response<signUpResponse> addAuthor(@RequestBody @Validated signUpDTO signUpDTO) throws ParseException {

        Response<signUpResponse> response=new Response<>();

        signUpResponse save=AuthorService.newUser(signUpDTO);
        if (save!=null){
            response.setResponseCode(1);
            response.setResponseMessage("Your Email has been Registered Successfully!!");
            response.setResponseBody(save);
        }else {

            response.setResponseCode(responseConstants.ResponseCodes.SIGNUP_FAILED);
            response.setResponseMessage("Your Email is already Registered!");
            response.setResponseBody(null);
        }
        return response;

    }

    @GetMapping("/profile")
    public @ResponseBody Response<profileResponse> getProfileDate(@RequestBody @Validated profileDTO email){

        Response<profileResponse> response=new Response<>();
        profileResponse profileResponse=AuthorService.findByEmail(email.getEmail());
        if (profileResponse!=null){
            response.setResponseCode(1);
            response.setResponseMessage("Data Fetched Successfully!!!");
            response.setResponseBody(profileResponse);
        }else {
            response.setResponseCode(responseConstants.ResponseCodes.PROFILE_DATA_FAILED);
            response.setResponseBody(null);
            response.setResponseMessage("No Data Found with provided Credentials");
        }
        return response;

    }

    @PostMapping("/login")
    public @ResponseBody Response<loginResponse> logIn(@RequestBody @Validated loginDTO loginDTO){

        Response<loginResponse> response=new Response<>();
        loginResponse loginResponse=AuthorService.loginAuthor(loginDTO);
        if (loginResponse.getToken()!=null){
            response.setResponseCode(1);
            response.setResponseMessage("User Login Successfully");
            response.setResponseBody(loginResponse);
        }else {
            response.setResponseCode(responseConstants.ResponseCodes.LOGIN_FAILED);
            response.setResponseMessage("provided Credentials did not match to any record");
            response.setResponseBody(null);
        }
        return response;

    }


}
