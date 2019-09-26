package digger.web.controller;

import digger.model.Datasource;
import digger.service.DatasourceService;
import digger.service.TableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DatasourceController {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private TableService tableService;

    @RequestMapping("/datasources/new")
    public String newDatasource(Model model) {
        model.addAttribute("datasource", new Datasource());
        return "datasource_form";
    }

    @PostMapping("/datasources")
    public String saveDatasource(@ModelAttribute Datasource datasource) {
        datasourceService.save(datasource);
        return "index";
    }

    @GetMapping("/datasources/{datasourceId}")
    public String openDatasource(Model model, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        model.addAttribute("datasource", datasource);
        if(datasource.getTotalTables() != null && datasource.getTotalTables() > 0) {
            model.addAttribute("progress", Math.round(tableService.countDocumentedTables(datasource) / (datasource.getTotalTables() * 1.0) * 100));
        } else {
            model.addAttribute("progress", 0);
        }
        return "datasource";
    }

    @GetMapping("/datasources/{datasourceId}/edit")
    public String editDatasource(Model model, @PathVariable Long datasourceId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        return "datasource_form";
    }
}