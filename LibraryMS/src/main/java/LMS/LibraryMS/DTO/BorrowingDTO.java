package LMS.LibraryMS.DTO;


import LMS.LibraryMS.Entity.Book;
import LMS.LibraryMS.Entity.BorrowingRecord;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

public class BorrowingDTO {
    private Long bookId;
    @NotNull(message = "name can't be null")
    private String username;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Book book;


    public BorrowingDTO(Long bookId, String username, LocalDate borrowDate, LocalDate returnDate, Book book) {
        this.bookId = bookId;
        this.username = username;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book = book;
    }
}

