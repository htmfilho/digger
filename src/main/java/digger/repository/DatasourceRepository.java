package digger.repository;

import digger.model.Datasource;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DatasourceRepository extends Repository<Datasource, Long> {
    List<Datasource> findAll();
    Datasource findById(Long id);
    void save(Datasource datasource);
    void deleteById(Long id);
}