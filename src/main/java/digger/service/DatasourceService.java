package digger.service;

import digger.model.Datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DatasourceService {

    List<Datasource> findAll();

    Datasource findById(Long id);

    void save(Datasource datasource);

    void delete(Long datasourceId);

    void updateTotalTables(Datasource datasource, Integer totalTables);

    Boolean testConnection(Datasource datasource) throws SQLException;

    Connection getConnection(Datasource datasource) throws SQLException;
}