package digger.web.controller;

import digger.model.Datasource;
import digger.service.DatasourceService;
import digger.service.TableService;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DatasourceController {

    private final DatasourceService datasourceService;
    private final TableService tableService;

    public DatasourceController(DatasourceService datasourceService, TableService tableService) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
    }

    @RequestMapping("/datasources/new")
    public String newDatasource(Model model) {
        model.addAttribute("datasource", new Datasource());
        return "datasource_form";
    }

    @PostMapping("/datasources")
    public String saveDatasource(@ModelAttribute Datasource datasource) {
        if (datasource.getId() != 0L) {
            Datasource existingDatasource = datasourceService.findById(datasource.getId());
            datasource.setTotalTables(existingDatasource.getTotalTables());
        }
        datasourceService.save(datasource);

        return "index";
    }

    @GetMapping("/datasources/{datasourceId}")
    public String openDatasource(Model model, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        try {
            datasource.setStatus(datasourceService.testConnection(datasource));
        } catch (SQLException e) {
            model.addAttribute("exception", e.getMessage());
        }
        model.addAttribute("datasource", datasource);
        model.addAttribute("progress", tableService.calculateProgress(datasource));
        return "datasource";
    }

    @GetMapping("/datasources/{datasourceId}/edit")
    public String editDatasource(Model model, @PathVariable Long datasourceId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        return "datasource_form";
    }
}