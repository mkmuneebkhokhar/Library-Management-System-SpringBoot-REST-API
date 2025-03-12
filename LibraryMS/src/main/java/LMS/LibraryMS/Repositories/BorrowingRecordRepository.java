package LMS.LibraryMS.Repositories;
import LMS.LibraryMS.Entity.BorrowingRecord;
import LMS.LibraryMS.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long>
{
    List<BorrowingRecord> findByUser(User user);

}
