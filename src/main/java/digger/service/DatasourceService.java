package digger.service;

import digger.model.Datasource;
import digger.repository.DatasourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Service
public class DatasourceService {

    private static final Logger log = LoggerFactory.getLogger(DatasourceService.class.getName());

    @Autowired
    private DatasourceRepository datasourceRepository;

    public List<Datasource> findAll() {
        return datasourceRepository.findAll();
    }

    public Datasource findById(Long id) {
        return datasourceRepository.findById(id);
    }

    public void save(Datasource datasource) {
        datasourceRepository.save(datasource);
    }

    public void updateTotalTables(Datasource datasource, Integer totalTables) {
        datasource.setTotalTables(totalTables);
        save(datasource);
    }

    public Boolean testConnection(Datasource datasource) throws SQLException {
        return (getConnection(datasource) != null);
    }

    Connection getConnection(Datasource datasource) throws SQLException {
        if (datasource == null) return null;

        try {
            Class.forName(datasource.getDriver());
            log.info("Driver '{}' available.", datasource.getDriver());
            Connection connection = DriverManager.getConnection(datasource.getUrl(), datasource.getUsername(), datasource.getPassword());
            log.info("Connection to '{}' is successful.", datasource.getName());
            return connection;
        } catch (ClassNotFoundException cnfe) {
            log.warn("Driver '{}' not found.", datasource.getDriver());
        } catch (SQLException se) {
            log.error("Error connecting to the datasource '{}'. {}", datasource.getName(), se.getMessage());
            throw se;
        }
        return null;
    }
}