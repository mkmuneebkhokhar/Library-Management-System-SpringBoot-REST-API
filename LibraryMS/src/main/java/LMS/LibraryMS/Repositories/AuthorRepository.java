package LMS.LibraryMS.Repositories;


import LMS.LibraryMS.Entity.Author;
import LMS.LibraryMS.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {


}

