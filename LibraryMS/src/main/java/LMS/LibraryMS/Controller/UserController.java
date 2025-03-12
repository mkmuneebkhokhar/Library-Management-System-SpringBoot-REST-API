package LMS.LibraryMS.Controller;

import LMS.LibraryMS.DTO.BorrowingDTO;
import LMS.LibraryMS.DTO.UserDTO;
import LMS.LibraryMS.Entity.BorrowingRecord;
import LMS.LibraryMS.Security.AuthRequest;
import LMS.LibraryMS.Security.AuthResponse;
import LMS.LibraryMS.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse response = userService.authenticate(authRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/{id}")
    public List<BorrowingDTO> gethistory(@PathVariable Long id)
    {
        return userService.getUserBorrowingHistory(id);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/admin/username/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }


    @PutMapping("/admin/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
       return userService.updateUser(id,userDTO);
    }


    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable Long id)
    {
       return userService.deleteUser(id);
    }

}
