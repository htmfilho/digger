package digger.web.controller;

import digger.model.Datasource;
import digger.model.Table;
import digger.service.DatasourceService;
import digger.service.TableService;
import digger.web.utils.Markdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TableController {

    @Autowired
    private DatasourceService datasourceService;

    @Autowired
    private TableService tableService;

    @RequestMapping("/datasources/{datasourceId}/tables/new")
    public String newTable(Model model, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        model.addAttribute("datasource", datasource);
        model.addAttribute("table", new Table());
        return "table_form";
    }

    @PostMapping("/datasources/{datasourceId}/tables")
    public String saveTable(Model model, @PathVariable Long datasourceId, @ModelAttribute Table table) {
        Datasource datasource = datasourceService.findById(datasourceId);
        table.setDatasource(datasource);
        tableService.save(table);
        model.addAttribute("datasource", datasource);
        return "datasource";
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}")
    public String openTable(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        if(datasource == null) return "redirect:/";
        model.addAttribute("datasource", datasource);

        Table table = tableService.findById(tableId);
        if(table == null) return "redirect:/datasources/{datasourceId}";
        model.addAttribute("table", table);

        table.setDocumentation(Markdown.toHtml(table.getDocumentation()));

        return "table";
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/edit")
    public String editTable(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("table", tableService.findById(tableId));
        return "table_form";
    }
}