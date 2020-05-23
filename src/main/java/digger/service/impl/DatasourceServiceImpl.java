package digger.service.impl;

import digger.model.Datasource;
import digger.repository.DatasourceRepository;
import digger.service.DatasourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Service
public class DatasourceServiceImpl implements DatasourceService {

    private static final Logger log = LoggerFactory.getLogger(DatasourceServiceImpl.class.getName());

    private final DatasourceRepository datasourceRepository;

    public DatasourceServiceImpl(DatasourceRepository datasourceRepository) {
        this.datasourceRepository = datasourceRepository;
    }

    public List<Datasource> findAll() {
        return datasourceRepository.findAllByOrderByName();
    }

    public Datasource findById(final Long id) {
        return datasourceRepository.findById(id);
    }

    public void save(Datasource datasource) {
        datasourceRepository.save(datasource);
    }

    public void delete(Long datasourceId) {
        datasourceRepository.deleteById(datasourceId);
    }

    public void updateTotalTables(Datasource datasource, final Integer totalTables) {
        datasource.setTotalTables(totalTables);
        save(datasource);
    }

    public Boolean testConnection(final Datasource datasource) throws SQLException {
        return (getConnection(datasource) != null);
    }

    public Connection getConnection(final Datasource datasource) throws SQLException {
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