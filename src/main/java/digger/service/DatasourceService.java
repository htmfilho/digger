package digger.service;

import digger.model.Datasource;
import digger.repository.DatasourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasourceService {

    @Autowired
    private DatasourceRepository datasourceRepository;

    public List<Datasource> findAll() {
        return datasourceRepository.findAll();
    }

    public Datasource findById(Long id) {
        Datasource datasource = datasourceRepository.findById(id);
        return datasource;
    }
}