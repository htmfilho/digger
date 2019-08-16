package digger.web.resource;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Relationship;
import digger.model.Table;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RelationshipResource {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private RelationshipService relationshipService;

    @GetMapping(value = "/api/datasources/{datasourceId}/tables/{tableName}/relationships", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Relationship> getColumns(@PathVariable Long datasourceId, @PathVariable String tableName) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = new Table(tableName);
        return relationshipService.listRelationships(datasource, table);
    }
}