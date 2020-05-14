package digger.service;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;

import java.util.List;

public interface ColumnService {

    List<Column> listUndocumentedColumns(Datasource datasource, Table table, String key, Column except);

    List<Column> excludeDocumentedColumns(Table table, List<Column> columns, Column except);

    Column getColumn(Datasource datasource, Table table, Column column);

    void save(Column column);

    void delete(Long columnId);

    Column findById(Long id);

    List<Column> findByTable(Table table);

    List<Column> findByForeignKey(Column foreignKey);

    Integer countDocumentedColumns(Table table);

    Integer calculateProgress(Table table);
}
