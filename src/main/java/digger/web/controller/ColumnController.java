package digger.web.controller;

import digger.model.Datasource;
import digger.model.Table;
import digger.service.DatasourceService;
import digger.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ColumnController {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private TableService tableService;

    @RequestMapping("/datasources/{datasourceId}/tables/{tableId}/columns/new")
    public String newColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Table table = tableService.findById(tableId);
        model.addAttribute("datasource", datasource);
        model.addAttribute("table", table);
        return "column_form";
    }
}