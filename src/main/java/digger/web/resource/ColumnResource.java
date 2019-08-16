package digger.web.resource;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ColumnResource {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private ColumnService columnService;

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableName}/columns", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Column> getColumns(@PathVariable Long datasourceId, @PathVariable String tableName, @RequestParam(value = "key", defaultValue = "") String key) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = new Table(tableName);
        return columnService.listColumns(datasource, table, key);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableName}/columns/{columnName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Column getTable(@PathVariable Long datasourceId, @PathVariable String tableName, @PathVariable String columnName) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = new Table(tableName);
        Column column = new Column(columnName);
        return columnService.getColumn(datasource, table, column);
    }
}