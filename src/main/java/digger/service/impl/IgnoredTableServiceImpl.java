package digger.service.impl;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;
import digger.repository.IgnoredTableRepository;
import digger.service.IgnoredTableService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IgnoredTableServiceImpl implements IgnoredTableService {

    private final IgnoredTableRepository ignoredTableRepository;

    public IgnoredTableServiceImpl(IgnoredTableRepository ignoredTableRepository) {
        this.ignoredTableRepository = ignoredTableRepository;
    }

    public void save(IgnoredTable ignoredTable) {
        ignoredTableRepository.save(ignoredTable);
    }

    public IgnoredTable findById(final Long id) {
        return ignoredTableRepository.findById(id);
    }

    public List<IgnoredTable> findByDatasource(final Datasource datasource) {
        return ignoredTableRepository.findByDatasourceOrderByNameAsc(datasource);
    }

    public int getTotalIgnoredTable(final Datasource datasource) {
        return findByDatasource(datasource).size();
    }

    public List<Table> excludeIgnoredTables(final Datasource datasource, List<Table> tables) {
        List<IgnoredTable> existingIgnoredTables = findByDatasource(datasource);
        tables.removeAll(toTableList(existingIgnoredTables));
        return tables;
    }

    public void delete(final Long ignoredTableId) {
        ignoredTableRepository.deleteById(ignoredTableId);
    }

    private List<Table> toTableList(final List<IgnoredTable> ignoredTables) {
        List<Table> tables = new ArrayList<>();
        for (IgnoredTable ignoredTable: ignoredTables) {
            tables.add(ignoredTable.toTable());
        }
        return tables;
    }
}