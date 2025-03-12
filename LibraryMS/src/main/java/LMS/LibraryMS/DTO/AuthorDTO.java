package LMS.LibraryMS.DTO;



import LMS.LibraryMS.Entity.Book;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    @NotNull(message = "name can't be null")
    private String name;
    private String bio;
    private List<Book> books;
}
