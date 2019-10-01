package digger.service;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.repository.IgnoredTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IgnoredTableService {

    @Autowired
    private IgnoredTableRepository ignoredTableRepository;

    public void save(IgnoredTable ignoredTable) {
        ignoredTableRepository.save(ignoredTable);
    }

    public IgnoredTable findById(Long id) {
        return ignoredTableRepository.findById(id);
    }

    public List<IgnoredTable> findByDatasource(Datasource datasource) {
        return ignoredTableRepository.findByDatasourceOrderByNameAsc(datasource);
    }
}