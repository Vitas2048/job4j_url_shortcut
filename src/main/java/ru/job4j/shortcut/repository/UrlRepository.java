package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.shortcut.model.Url;

import java.util.List;

public interface UrlRepository extends CrudRepository<Url, Integer> {

    List<Url> findAll();

    Url findByCode(String code);

    @Modifying
    @Query(value = """
            UPDATE Url u
            SET u.count = u.count + 1
            WHERE u.code = ?1
            """)
    void incrementByCode(String code);
}
