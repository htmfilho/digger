package digger.repository;

import digger.model.Datasource;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DatasourceRepository extends Repository<Datasource, Long> {
    Datasource findById(Long id);
    
    List<Datasource> findAll();
    
    void save(Datasource datasource);
    void deleteById(Long id);
}