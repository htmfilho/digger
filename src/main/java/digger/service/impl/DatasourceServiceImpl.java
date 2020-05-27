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
    private static final Logger logger = LoggerFactory.getLogger(DatasourceService.class);

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
        logger.info("Saved datasource {}", datasource.getName());
    }

    public void delete(Long datasourceId) {
        datasourceRepository.deleteById(datasourceId);
        logger.info("Deleted datasource sith id {}", datasourceId);
    }

    public void updateTotalTables(Datasource datasource, final Integer totalTables) {
        datasource.setTotalTables(totalTables);
        save(datasource);
        logger.info("Updated the total number of tables of the datasource {} to {}", datasource.getName(), totalTables);
    }

    public Boolean testConnection(final Datasource datasource) throws SQLException {
        return (getConnection(datasource) != null);
    }

    public Connection getConnection(final Datasource datasource) throws SQLException {
        if (datasource == null) return null;

        try {
            Class.forName(datasource.getDriver());
            logger.info("Driver '{}' available.", datasource.getDriver());
            Connection connection = DriverManager.getConnection(datasource.getUrl(), datasource.getUsername(), datasource.getPassword());
            logger.info("Connection to '{}' was successful.", datasource.getName());
            return connection;
        } catch (ClassNotFoundException cnfe) {
            logger.warn("Driver '{}' not found.", datasource.getDriver());
        } catch (SQLException se) {
            logger.error("Error connecting to the datasource '{}'. {}", datasource.getName(), se.getMessage());
            throw se;
        }
        return null;
    }
}