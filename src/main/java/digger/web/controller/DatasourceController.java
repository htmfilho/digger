package digger.web.controller;

import digger.model.Datasource;
import digger.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DatasourceController {

    @Autowired
    private DatasourceService datasourceService;

    @RequestMapping("/datasources/new")
    public String newDatasource() {
        return "datasource_form";
    }

    @PostMapping("/datasources")
    public String saveDatasource(@ModelAttribute Datasource datasource) {
        datasourceService.save(datasource);
        return "index";
    }

    @GetMapping("/datasources/{datasourceId}")
    public String openResource(Model model, @PathVariable Long datasourceId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        return "datasource";
    }
}