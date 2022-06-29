package spring.framework.books.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.framework.books.classes.Book;
import spring.framework.books.requestDTO.DownloadBookDTO;
import spring.framework.books.requestDTO.addBookDTO;
import spring.framework.books.responseDTO.AddBookResponse;
import spring.framework.books.responseDTO.Response;
import spring.framework.books.responseDTO.responseConstants;
import spring.framework.books.services.bookServices;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

@RestController
@RequestMapping("/book")
public class bookController {

    private final bookServices bookServices;


    public bookController(spring.framework.books.services.bookServices bookServices) {
        this.bookServices = bookServices;
    }

    @PostMapping(value = "/add",consumes =  "multipart/form-data" )
    public Response<AddBookResponse> addBook(@RequestParam ("bookData") String bookData, @RequestParam("file")MultipartFile multipartFile) throws ParseException, IOException, JSONException {


        Gson gson=new Gson();
        addBookDTO addBookDTO=gson.fromJson(bookData, spring.framework.books.requestDTO.addBookDTO.class);
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
