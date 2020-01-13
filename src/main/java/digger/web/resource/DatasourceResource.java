package digger.web.resource;

import digger.model.Datasource;
import digger.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DatasourceResource {

    private final DatasourceService datasourceService;

    public DatasourceResource(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    @GetMapping(value = "/api/datasources", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Datasource> getDatasources() {
        return this.datasourceService.findAll();
    }

    @GetMapping(value = "/api/datasources/{datasourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Datasource getDatasource(@PathVariable Long datasourceId) {
        return this.datasourceService.findById(datasourceId);
    }
}