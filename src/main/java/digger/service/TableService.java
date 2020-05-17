package digger.service;

import digger.model.Datasource;
import digger.model.Table;

import java.util.List;

public interface TableService {

    Table findById(Long id);
    Table getTable(Datasource datasource, Table table);

    List<Table> listTables(Datasource datasource, String key, Table except);
    List<Table> excludeDocumentedTables(Datasource datasource, List<Table> tables, Table except);
    List<Table> findByDatasource(Datasource datasource);

    Integer countDocumentedTables(Datasource datasource);
    Integer calculateProgress(Datasource datasource);

    void save(Table table);
    void delete(Long tableId);
    void updateTotalColumns(Table table, Integer totalColumns);
}
