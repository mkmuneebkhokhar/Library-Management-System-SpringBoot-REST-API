package LMS.LibraryMS.Controller;

import LMS.LibraryMS.DTO.AuthorDTO;
import LMS.LibraryMS.DTO.BookDTO;
import LMS.LibraryMS.Service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/users")
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }




    @PostMapping("/admin/add")
    public AuthorDTO addAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.addAuthor(authorDTO);
    }


    @PutMapping("/admin/update/{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.updateAuthor(id, authorDTO);
    }


    @DeleteMapping("/admin/delete/{id}")
    public AuthorDTO deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }

    @GetMapping("/admin/users")
    public AuthorDTO findauthorbyid(@PathVariable Long id)
    {
        return authorService.getauthorbyid(id);
    }



}

