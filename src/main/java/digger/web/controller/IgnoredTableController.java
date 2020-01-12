package digger.web.controller;

import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.service.DatasourceService;
import digger.service.IgnoredTableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IgnoredTableController {

    private final DatasourceService datasourceService;
    private final IgnoredTableService ignoredTableService;

    public IgnoredTableController(DatasourceService datasourceService, IgnoredTableService ignoredTableService) {
        this.datasourceService = datasourceService;
        this.ignoredTableService = ignoredTableService;
    }

    @RequestMapping("/datasources/{datasourceId}/tables/ignored/new")
    public String newIgnoredTable(Model model, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        model.addAttribute("datasource", datasource);
        model.addAttribute("ignoredTable", new IgnoredTable());
        return "ignored_table_form";
    }

    @PostMapping("/datasources/{datasourceId}/tables/ignored")
    public String saveIgnoredTable(Model model, @PathVariable Long datasourceId, @ModelAttribute IgnoredTable ignoredTable) {
        Datasource datasource = datasourceService.findById(datasourceId);
        ignoredTable.setDatasource(datasource);
        ignoredTableService.save(ignoredTable);

        return "redirect:/datasources/{datasourceId}?tab=ignored";
    }

    @GetMapping("/datasources/{datasourceId}/tables/ignored/{ignoredTableId}")
    public String openIgnoredTable(Model model, @PathVariable Long datasourceId, @PathVariable Long ignoredTableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        if(datasource == null) return "redirect:/";
        model.addAttribute("datasource", datasource);

        IgnoredTable ignoredTable = ignoredTableService.findById(ignoredTableId);
        if(ignoredTable == null) return "redirect:/datasources/{datasourceId}";
        model.addAttribute("ignoredTable", ignoredTable);

        return "ignored_table";
    }

    @GetMapping("/datasources/{datasourceId}/tables/ignored/{ignoredTableId}/edit")
    public String editIgnoredTable(Model model, @PathVariable Long datasourceId, @PathVariable Long ignoredTableId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("ignoredTable", ignoredTableService.findById(ignoredTableId));
        return "ignored_table_form";
    }
}