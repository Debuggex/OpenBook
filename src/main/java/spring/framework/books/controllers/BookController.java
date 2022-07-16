package spring.framework.books.controllers;


import com.google.gson.Gson;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.framework.books.requestDTO.DownloadBookDTO;
import spring.framework.books.requestDTO.AddBookDTO;
import spring.framework.books.responseDTO.AddBookResponse;
import spring.framework.books.responseDTO.Response;
import spring.framework.books.services.BookServices;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookServices bookServices;


    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @PostMapping(value = "/add",consumes =  "multipart/form-data" )
    public Response<AddBookResponse> addBook(@RequestParam ("bookData") String bookData, @RequestParam("file")MultipartFile multipartFile) throws ParseException, IOException, JSONException {


        Gson gson=new Gson();
        AddBookDTO addBookDTO=gson.fromJson(bookData, AddBookDTO.class);
        Response<AddBookResponse> response=new Response<>();

        AddBookResponse addBookResponse = bookServices.addBook(addBookDTO, multipartFile);
        response.setResponseCode(1);
        response.setResponseBody(addBookResponse);
        response.setResponseMessage(addBookResponse.getMessage());
        return response;

    }

    @GetMapping(value = "/downloadBook",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Resource downloadBook(@RequestBody DownloadBookDTO downloadBookDTO) throws IOException {

        Path file=bookServices.getFileResource(downloadBookDTO.getFileName());

        InputStreamResource resource=new InputStreamResource(new FileInputStream(String.valueOf(bookServices.getFileResource(String.valueOf(file)))));
        return resource;

    }

}
