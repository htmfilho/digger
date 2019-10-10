package digger.service;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Relationship;
import digger.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelationshipService {

    private static final Logger log = LoggerFactory.getLogger(RelationshipService.class.getName());

    private static final int TARGET_TABLE_NAME = 3;
    private static final int ORIGIN_TABLE_NAME = 7;
    private static final int FOREIGN_KEY_COLUMN_NAME = 8;
    private static final int RELATIONSHIP_NAME = 12;

    @Autowired
    private DatasourceService datasourceService;

    public List<Relationship> listRelationships(Datasource datasource, Table table) {
        List<Relationship> relationships = new ArrayList<>();
        try (Connection connection = datasourceService.getConnection(datasource)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getImportedKeys(connection.getCatalog(), null, table.getName());
            while (resultSet.next()) {
                Table target = new Table();
                target.setName(resultSet.getString(TARGET_TABLE_NAME));

                Table origin = new Table();
                origin.setName(resultSet.getString(ORIGIN_TABLE_NAME));

                Column foreignKey = new Column();
                foreignKey.setName(resultSet.getString(FOREIGN_KEY_COLUMN_NAME));

                Relationship relationship = new Relationship();
                relationship.setOrigin(origin);
                relationship.setTarget(target);
                relationship.setForeignKey(foreignKey);
                relationship.setName(resultSet.getString(RELATIONSHIP_NAME));
                relationships.add(relationship);
            }
            resultSet.close();
        } catch (SQLException se) {
            log.warn("Error: {}", se.getMessage());
        }
        return relationships;
    }
}