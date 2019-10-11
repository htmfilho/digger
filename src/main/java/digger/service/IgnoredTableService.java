package digger.service;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;
import digger.repository.IgnoredTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public int getTotalIgnoredTable(Datasource datasource) {
        return findByDatasource(datasource).size();
    }

    public List<Table> excludeIgnoredTables(Datasource datasource, List<Table> tables) {
        List<IgnoredTable> existingIgnoredTables = findByDatasource(datasource);
        tables.removeAll(toTableList(existingIgnoredTables));
        return tables;
    }

    public void deleteIgnoredTable(Long ignoredTableId) {
        ignoredTableRepository.deleteById(ignoredTableId);
    }

    private List<Table> toTableList(List<IgnoredTable> ignoredTables) {
        List<Table> tables = new ArrayList<>();
        for (IgnoredTable ignoredTable: ignoredTables) {
            tables.add(ignoredTable.toTable());
        }
        return tables;
    }
}