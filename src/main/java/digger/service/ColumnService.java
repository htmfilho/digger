package digger.service;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;

import java.util.List;

public interface ColumnService {

    Column findById(Long id);
    Column getColumn(Datasource datasource, Table table, Column column);
        
    List<Column> listUndocumentedColumns(Datasource datasource, Table table, String key, Column except);
    List<Column> excludeDocumentedColumns(Table table, List<Column> columns, Column except);
    List<Column> findByTable(Table table);
    List<Column> findByForeignKey(Column foreignKey);
    List<Column> findByForeignKeyIn(List<Column> columns);

    Integer countDocumentedColumns(Table table);
    Integer calculateProgress(Table table);

    void save(Column column);
    void delete(Long columnId);
}