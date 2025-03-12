package LMS.LibraryMS.Service;


import LMS.LibraryMS.DTO.AuthorDTO;
import LMS.LibraryMS.DTO.BookDTO;
import LMS.LibraryMS.Entity.Author;
import LMS.LibraryMS.Repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    private final ModelMapper mapper;

    public AuthorService(ModelMapper mapper)
    {
        this.mapper = mapper;
    }

    public List<AuthorDTO> getAllAuthors()
    {
        return authorRepository
                .findAll()
                .stream()
                .map(Author->mapper.map(Author, AuthorDTO.class))
                .collect(Collectors.toList());
    }

    public AuthorDTO addAuthor(AuthorDTO authorDTO)
    {
     Author aut= mapper.map(authorDTO, Author.class);
     return mapper
             .map(authorRepository.save(aut), AuthorDTO.class);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO)
    {
        Author author = authorRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author
                .setName(authorDTO.getName());
        author
                .setBio(authorDTO.getBio());
        return mapper
                .map(authorRepository.save(author),AuthorDTO.class);
    }

    public AuthorDTO deleteAuthor(Long id)
    {
        Author auth= authorRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Id not Found!"));

        authorRepository.deleteById(id);
        return mapper
                .map(auth,AuthorDTO.class);

    }

    public AuthorDTO getauthorbyid(Long id)
    {
        Author auth= authorRepository.getById(id);
        return mapper.map(auth,AuthorDTO.class);
    }



}
