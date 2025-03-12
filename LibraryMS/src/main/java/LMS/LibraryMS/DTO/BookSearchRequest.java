package LMS.LibraryMS.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchRequest {
    private int page = 0; // Default page 0
    private int size = 10; // Default page size
    private String sortBy = "title"; // Default sorting field
    private String sortDirection = "asc"; // Default sorting order
    private String query = "";
}

