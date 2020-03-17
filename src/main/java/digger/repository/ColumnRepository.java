package digger.repository;

import digger.model.Column;
import digger.model.Table;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ColumnRepository extends Repository<Column, Long> {
    Column findById(Long id);
    List<Column> findByTableOrderByNameAsc(Table table);
    List<Column> findByForeignKey(Column foreignKey);
    void save(Column column);
    void deleteById(Long id);
}
