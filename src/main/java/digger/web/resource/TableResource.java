package digger.web.resource;

import digger.model.Datasource;
import digger.model.Table;
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
public class TableResource {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private TableService tableService;

    @GetMapping(value = "/api/datasources/{datasourceId}/tables", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getTables(@PathVariable Long datasourceId, @RequestParam(value = "key", defaultValue = "") String key) {
        Datasource datasource = datasourceService.findById(datasourceId);
        return tableService.listTables(datasource, key);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/documented", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getDocumentedTables(@PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        return tableService.findByDatasource(datasource);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Table getTable(@PathVariable Long datasourceId, @PathVariable String tableName) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = new Table(tableName);
        return tableService.getTable(datasource, table);
    }
}
