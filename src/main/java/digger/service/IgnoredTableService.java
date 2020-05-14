package digger.service;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;

import java.util.List;

public interface IgnoredTableService {

    void save(IgnoredTable ignoredTable);

    IgnoredTable findById(Long id);

    List<IgnoredTable> findByDatasource(Datasource datasource);

    int getTotalIgnoredTable(Datasource datasource);

    List<Table> excludeIgnoredTables(Datasource datasource, List<Table> tables);

    void delete(Long ignoredTableId);
}