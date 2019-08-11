package digger.web;

import digger.model.Datasource;
import digger.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatasourceResource {

    @Autowired
    private DatasourceService datasourceService;

    @GetMapping(value = "/api/datasources", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Datasource> getResources() {
        return this.datasourceService.findAll();
    }
}