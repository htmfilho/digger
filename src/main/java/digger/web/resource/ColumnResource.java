package digger.web.resource;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.TableService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ColumnResource {

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final ColumnService columnService;

    public ColumnResource(DatasourceService datasourceService, TableService tableService, ColumnService columnService) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
        this.columnService = columnService;
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableId}/columns", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Column> getColumns(@PathVariable Long datasourceId, @PathVariable Long tableId, 
                                   @RequestParam(value = "key", defaultValue = "") String key, 
                                   @RequestParam(value = "except", defaultValue = "") Long except) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = tableService.findById(tableId);
        Column exceptColumn = columnService.findById(except);
        return columnService.listUndocumentedColumns(datasource, table, key, exceptColumn);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableId}/columns/documented", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Column> getDocumentedTables(@PathVariable Long datasourceId, 
                                            @PathVariable Long tableId) {
        Table table = tableService.findById(tableId);
        return columnService.findByTable(table);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableName}/columns/{columnName}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Column getColumn(@PathVariable Long datasourceId, 
                            @PathVariable String tableName, 
                            @PathVariable String columnName) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = new Table(tableName);
        Column column = new Column(columnName);
        return columnService.getColumn(datasource, table, column);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}/foreignkeys", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Column> getForeignKeys(@PathVariable Long datasourceId, 
                                       @PathVariable Long tableId, 
                                       @PathVariable Long columnId) {
        return columnService.findByForeignKey(new Column(columnId));
    }

    @DeleteMapping("/api/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}")
    public void deleteDatasource(@PathVariable Long columnId) {
        columnService.delete(columnId);
    }
}