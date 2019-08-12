package digger.web.resource;

import digger.model.Datasource;
import digger.model.Table;
import digger.service.DatasourceService;
import digger.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TableResource {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private TableService tableService;

    @GetMapping(value = "/api/datasources/{id}/tables", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Table> getTables(@PathVariable Long id) {
        Datasource datasource = datasourceService.findById(id);
        return tableService.listAllTables(datasource);
    }
}
