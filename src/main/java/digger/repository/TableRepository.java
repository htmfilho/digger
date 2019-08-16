package digger.repository;

import digger.model.Table;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TableRepository extends Repository<Table, Long> {
    List<Table> findAll();
    Table findById(Long id);
    Table findByName(String name);
}
