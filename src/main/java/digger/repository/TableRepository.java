package digger.repository;

import digger.model.Datasource;
import digger.model.Table;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TableRepository extends Repository<Table, Long> {
    Table findById(Long id);
    List<Table> findByDatasource(Datasource datasource);
    Table findByName(String name);
    void save(Table datasource);
}
