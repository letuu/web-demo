package pro.sky.webdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.webdemo.model.BookCover;

import java.util.Optional;

public interface BookCoverRepository extends JpaRepository<BookCover, Long> {

    Optional<BookCover> findByBookId(Long bookId);  //ищем обложку по идентификатору книги
}
