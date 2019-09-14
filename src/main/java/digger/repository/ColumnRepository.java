package digger.repository;

import digger.model.Column;
import digger.model.Table;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ColumnRepository extends Repository<Column, Long> {
    Column findById(Long id);
    List<Column> findByTable(Table table);
    void save(Column column);
}
