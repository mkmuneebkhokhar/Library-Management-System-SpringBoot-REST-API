package LMS.LibraryMS.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    @NotNull(message = "name can't be null")
    private String title;
    @NotNull(message = "ISBN can't be null")
    private String isbn;
    private LocalDate publicationDate;
    private Long authorId;
}

