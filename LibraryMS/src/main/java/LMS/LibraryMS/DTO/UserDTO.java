package LMS.LibraryMS.DTO;


import LMS.LibraryMS.Entity.BorrowingRecord;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull(message = "Username can't be null")
    private String username;
    @NotNull(message = "Password must be provided")
    private String password;
    private String role;

}

