package digger.service;

import digger.model.Datasource;
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
public class TableService {

    private static final Logger log = LoggerFactory.getLogger(TableService.class.getName());

    @Autowired
    private DatasourceService datasourceService;

    public List<Table> listAllTables(Datasource datasource, String key) {
        List<Table> tables = new ArrayList<>();
        try {
            Connection connection = datasourceService.getConnection(datasource);
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, key + "%", null);
            while (resultSet.next()) {
                Table table = new Table();
                table.setName(resultSet.getString(3));
                tables.add(table);
            }
        } catch (SQLException se) {
            log.warn("Connection not available.");
        }
        return tables;
    }
}
