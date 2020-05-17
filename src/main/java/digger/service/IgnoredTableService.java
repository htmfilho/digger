package digger.service;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;

import java.util.List;

public interface IgnoredTableService {

    IgnoredTable findById(Long id);

    int getTotalIgnoredTable(Datasource datasource);

    List<IgnoredTable> findByDatasource(Datasource datasource);
    List<Table> excludeIgnoredTables(Datasource datasource, List<Table> tables);

    void save(IgnoredTable ignoredTable);
    void delete(Long ignoredTableId);
}