package com.task.mydemo.controller;

import com.task.mydemo.DTO.BookDTO;
import com.task.mydemo.entity.Book;
import com.task.mydemo.repository.BookRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "main_methods")
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final BookRepo bookRepo;
   @Operation(
           summary = "кладёт новую книгу в базу",
           description = "Получает DTO книги и билдером собирает и сохраняет сущность в базу"
   )
    @PostMapping("/api/add")
    public void addBook(@RequestBody BookDTO bookDTO){
      log.info( "New row: " + bookRepo.save(
              Book.builder()
                      .name(bookDTO.getName())
                      .author(bookDTO.getAuthor())
                      .year(bookDTO.getYear())
                      .build())
      );
    }
    @SneakyThrows
    @Operation(
            summary = "получает все книги из базы"
    )
    @GetMapping("/api/all")
    public List<Book> getAll(){
        return bookRepo.findAll();
    }
    @Operation(
            summary = "получает определенную книгу по id из базы"
    )
    @GetMapping("/api")
    public Book getBook(@RequestParam int id){
        return bookRepo.findById(id).orElseThrow();
    }
    @Operation(
            summary = "удаляет определенную книгу по id из базы"
    )
    @DeleteMapping("/api")
    public void deleteBook(@RequestParam int id){
        bookRepo.deleteById(id);
    }
    @Operation(
            summary = "изменяет определенную книгу по id из базы"
    )
    @PutMapping("/api/change")
    public String changeBook(@RequestBody Book book){
        if (!bookRepo.existsById(book.getId())){
            return "No such row";
        }
       return bookRepo.save(book).toString();
    }
}
