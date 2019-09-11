package digger.web.resource;

import digger.model.Datasource;
import digger.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DatasourceResource {

    @Autowired
    private DatasourceService datasourceService;

    @GetMapping(value = "/api/datasources", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Datasource> getDatasources() {
        return this.datasourceService.findAll();
    }

    @GetMapping(value = "/api/datasources/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Datasource getDatasource(@PathVariable Long id) {
        return this.datasourceService.findById(id);
    }
}