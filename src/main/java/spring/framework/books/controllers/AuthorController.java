package spring.framework.books.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.framework.books.filter.RequestFilter;
import spring.framework.books.requestDTO.LoginDTO;
import spring.framework.books.requestDTO.SignUpDTO;
import spring.framework.books.requestDTO.ProfileDTO;
import spring.framework.books.responseDTO.*;
import spring.framework.books.services.AuthorServices;

import java.text.ParseException;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorServices AuthorService;

    private final RequestFilter requestFilter=new RequestFilter();

    public AuthorController(AuthorServices authorService) {
        AuthorService = authorService;
    }

    @PostMapping("/register")
    public @ResponseBody Response<SignUpResponse> addAuthor(@RequestBody @Validated SignUpDTO signUpDTO) throws ParseException {

        Response<SignUpResponse> response=new Response<>();

        SignUpResponse save=AuthorService.newUser(signUpDTO);
        if (save!=null){
            response.setResponseCode(1);
            response.setResponseMessage("Your Email has been Registered Successfully!!");
            response.setResponseBody(save);
        }else {

            response.setResponseCode(ResponseConstants.ResponseCodes.SIGNUP_FAILED);
            response.setResponseMessage("Your Email is already Registered!");
            response.setResponseBody(null);
        }
        return response;

    }

    @GetMapping("/profile")
    public @ResponseBody Response<ProfileResponse> getProfileDate(@RequestBody @Validated ProfileDTO email,@RequestHeader(value="Authorization") String auth){


        Response<ProfileResponse> response=new Response<>();
        if (!requestFilter.getAuthorize(email.getEmail(), auth)){
            response.setResponseCode(0);
            response.setResponseMessage("Non-Authorize Access!");
            return response;
        }
        ProfileResponse profileResponse=AuthorService.findByEmail(email.getEmail());
        if (profileResponse!=null){
            response.setResponseCode(1);
            response.setResponseMessage("Data Fetched Successfully!!!");
            response.setResponseBody(profileResponse);
        }else {
            response.setResponseCode(ResponseConstants.ResponseCodes.PROFILE_DATA_FAILED);
            response.setResponseBody(null);
            response.setResponseMessage("No Data Found with provided Credentials");
        }
        return response;

    }

//    @PostMapping("/login")
//    public @ResponseBody Response<loginResponse> logIn(@RequestBody @Validated loginDTO loginDTO){
//
//        Response<loginResponse> response=new Response<>();
//        loginResponse loginResponse=AuthorService.loginAuthor(loginDTO);
//        if (loginResponse.getToken()!=null){
//            response.setResponseCode(1);
//            response.setResponseMessage("User Login Successfully");
//            response.setResponseBody(loginResponse);
//        }else {
//            response.setResponseCode(responseConstants.ResponseCodes.LOGIN_FAILED);
//            response.setResponseMessage("provided Credentials did not match to any record");
//            response.setResponseBody(null);
//        }
//        return response;
//
//    }

    @DeleteMapping("/delete")
    public @ResponseBody Response<ProfileResponse> deleteAuthor(@RequestBody @Validated LoginDTO loginDTO, @RequestHeader(value="Authorization") String auth){

        Response<ProfileResponse> response=new Response<>();
        if (!requestFilter.getAuthorize(loginDTO.getEmail(), auth)){
            response.setResponseCode(0);
            response.setResponseMessage("Non-Authorize Access!");
            return response;
        }
        ProfileResponse profileResponse= AuthorService.deleteAuthor(loginDTO);
        if (profileResponse!=null){
            response.setResponseCode(1);
            response.setResponseBody(profileResponse);
            response.setResponseMessage("Author has been deleted Successfully!!");
        }else {
            response.setResponseCode(ResponseConstants.ResponseCodes.AUTHOR_DELETE_FAILED);
            response.setResponseMessage("We couldn't find any User with Provided Email");
        }
        return response;
    }


}
