package LMS.LibraryMS.Service;


import LMS.LibraryMS.DTO.BookDTO;
import LMS.LibraryMS.DTO.BookSearchRequest;
import LMS.LibraryMS.Entity.Author;
import LMS.LibraryMS.Entity.Book;
import LMS.LibraryMS.Entity.BookStatus;
import LMS.LibraryMS.Repositories.AuthorRepository;
import LMS.LibraryMS.Repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private AuthorRepository authorRepository;

    public BookService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book ->modelMapper.map(book,BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found!"));
       return modelMapper.map(book, BookDTO.class);
    }

    public Book addBook(BookDTO bookDTO) {
        Author author = authorRepository
                .findById(bookDTO.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found"));
        Book book = new Book(null, bookDTO.getTitle(), bookDTO.getIsbn(), bookDTO.getPublicationDate(), BookStatus.AVAILABLE, author);
        return bookRepository.save(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository
                .findById(bookDTO.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found"));

        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setAuthor(author);

        return modelMapper.map(bookRepository.save(book),BookDTO.class);
    }

    public BookDTO deleteBook(Long id) {
        Book book= bookRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("ID Not Found!"));
        bookRepository.deleteById(id);

        return modelMapper.map(book, BookDTO.class);

    }


    public List<BookDTO> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }


    public List<BookDTO> searchBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor_NameContainingIgnoreCase(author);
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }


    public BookDTO searchBookByISBN(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return modelMapper.map(book, BookDTO.class);
    }

    public Page<BookDTO> getBooks(BookSearchRequest request) {
        Sort sort = request.getSortDirection().equalsIgnoreCase("asc") ?
                Sort.by(request.getSortBy()).ascending() :
                Sort.by(request.getSortBy()).descending();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Page<Book> bookPage = request.getQuery() != null && !request.getQuery().isEmpty()
                ? bookRepository.findByTitleContainingIgnoreCase(request.getQuery(), pageable)
                : bookRepository.findAll(pageable);

        if (bookPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found for the given criteria.");
        }

        return bookPage.map(book -> modelMapper.map(book, BookDTO.class));
    }


}


