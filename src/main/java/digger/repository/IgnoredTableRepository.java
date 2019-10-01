package digger.repository;

import digger.model.Datasource;
import digger.model.IgnoredTable;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface IgnoredTableRepository extends Repository<IgnoredTable, Long> {
    IgnoredTable findById(Long id);
    List<IgnoredTable> findByDatasourceOrderByNameAsc(Datasource datasource);
    void save(IgnoredTable ignoredTable);
}