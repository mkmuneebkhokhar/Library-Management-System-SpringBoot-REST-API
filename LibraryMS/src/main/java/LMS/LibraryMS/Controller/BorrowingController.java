package LMS.LibraryMS.Controller;


import LMS.LibraryMS.DTO.BorrowingDTO;
import LMS.LibraryMS.Service.BorrowingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/user/borrow")
    public String borrowBook(@RequestBody @Valid BorrowingDTO borrowingDTO) {
        return borrowingService.borrowBook(borrowingDTO);
    }

    @PostMapping("/user/return/{recordId}")
    public String returnBook(@PathVariable Long recordId) {
        return borrowingService.returnBook(recordId);

    }
    @GetMapping("/user/{name}")
    public List<BorrowingDTO> getuserdetails(@PathVariable String name)
    {
        return borrowingService.getBorrowingRecordsByUser(name);
    }
}

