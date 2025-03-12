package LMS.LibraryMS.Service;
import LMS.LibraryMS.DTO.BorrowingDTO;
import LMS.LibraryMS.DTO.UserDTO;
import LMS.LibraryMS.Entity.Book;
import LMS.LibraryMS.Entity.BookStatus;
import LMS.LibraryMS.Entity.BorrowingRecord;
import LMS.LibraryMS.Entity.User;
import LMS.LibraryMS.Repositories.BookRepository;
import LMS.LibraryMS.Repositories.BorrowingRecordRepository;
import LMS.LibraryMS.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BorrowingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public String borrowBook(BorrowingDTO borrowingDTO) {
        User user = userRepository.findByUsername(borrowingDTO.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(borrowingDTO.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStatus() == BookStatus.BORROWED) {
            throw new RuntimeException("Book is already borrowed");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());

        book.setStatus(BookStatus.BORROWED);
        bookRepository.save(book);
        borrowingRecordRepository.save(record);

        return "Book borrowed successfully!";
    }

    public String returnBook(Long recordId) {
        BorrowingRecord record = borrowingRecordRepository.findById(recordId).orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        Book book = record.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        record.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(record);

        return "Book returned successfully!";
    }

    public List<BorrowingDTO> getBorrowingRecordsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        List<BorrowingRecord> records = borrowingRecordRepository.findByUser(user);

        return records.stream()
                .map(record -> modelMapper.map(record, BorrowingDTO.class))
                .collect(Collectors.toList());
    }
}



