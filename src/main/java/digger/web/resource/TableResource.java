package digger.web.resource;

import digger.model.Datasource;
import digger.model.Table;
import digger.service.DatasourceService;
import digger.service.TableService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TableResource {

    private final DatasourceService datasourceService;
    private final TableService tableService;

    public TableResource(DatasourceService datasourceService, TableService tableService) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getTables(@PathVariable Long datasourceId, 
                                 @RequestParam(value = "key", defaultValue = "") String key,
                                 @RequestParam(value = "except", defaultValue = "") Long tableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        return tableService.listTables(datasource, key, new Table(tableId));
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/documented", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getDocumentedTables(@PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        return tableService.findByDatasource(datasource);
    }

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Table getTable(@PathVariable Long datasourceId, @PathVariable Long tableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = new Table(tableId);
        return tableService.getTable(datasource, table);
    }

    @DeleteMapping("/api/datasources/{datasourceId}/tables/{tableId}")
    public void deleteTable(@PathVariable Long datasourceId, @PathVariable Long tableId) {
        tableService.delete(tableId);
    }
}
