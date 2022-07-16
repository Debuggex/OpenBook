package spring.framework.books.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.framework.books.classes.Book;
import spring.framework.books.services.DashboardServices;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/dashboard")
@RestController
public class DashboardController {

   private final DashboardServices dashboardServices;

    @GetMapping("/getAllBooks")
    public List<Set<Book>> getAllBooks(){

        return dashboardServices.getAllBooks();

    }

}
