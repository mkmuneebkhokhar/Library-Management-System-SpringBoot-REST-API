package LMS.LibraryMS.Controller;


import LMS.LibraryMS.DTO.BookDTO;
import LMS.LibraryMS.DTO.BookSearchRequest;
import LMS.LibraryMS.Entity.Book;
import LMS.LibraryMS.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/admin/add")
    public Book addBook(@RequestBody BookDTO bookDTO) {
        return bookService.addBook(bookDTO);
    }

    @PutMapping("/admin/update/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/admin/delete/{id}")
    public BookDTO deleteBook(@PathVariable Long id) {
       return bookService.deleteBook(id);
    }

    @GetMapping("/search/title/{title}")
    public List<BookDTO> searchBooksByTitle(@PathVariable String title) {
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping("/search/author/{author}")
    public List<BookDTO> searchBooksByAuthor(@PathVariable String author) {
        return bookService.searchBooksByAuthor(author);
    }

    @GetMapping("/search/isbn/{isbn}")
    public BookDTO searchBookByISBN(@PathVariable String isbn) {
        return bookService.searchBookByISBN(isbn);
    }

    @PostMapping("/search")
    public Page<BookDTO> getBooks(@RequestBody @Valid BookSearchRequest request) {
        return bookService.getBooks(request);
    }
}
