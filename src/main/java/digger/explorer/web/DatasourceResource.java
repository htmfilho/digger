package digger.explorer.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasourceResource {

    @GetMapping(value = "/datasources", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getResources() {
        return "[{'name': 'Microsoft SQL Server'}," +
                "{'name': 'PostgreSQL'}]";
    }
}