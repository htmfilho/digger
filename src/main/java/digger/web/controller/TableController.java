package digger.web.controller;

import digger.model.Datasource;
import digger.model.Table;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.TableService;
import digger.utils.Asciidoc;
import digger.utils.Text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TableController {

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final ColumnService columnService;
    private final Asciidoc asciidoc;
    private final Text text;

    @Value("${user.guide.url}")
    private String userGuideUrl;

    public TableController(DatasourceService datasourceService, TableService tableService, ColumnService columnService, Asciidoc asciidoc, Text text) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
        this.columnService = columnService;
        this.asciidoc = asciidoc;
        this.text = text;
    }

    @GetMapping("/datasources/{datasourceId}/tables/new")
    public String newTable(Model model, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        model.addAttribute("datasource", datasource);
        model.addAttribute("table", new Table());
        model.addAttribute("userGuideUrl", userGuideUrl + "#table-form");
        return "table_form";
    }

    @PostMapping("/datasources/{datasourceId}/tables")
    public String saveTable(@PathVariable Long datasourceId, @ModelAttribute Table table) {
        Datasource datasource = datasourceService.findById(datasourceId);

        if(table.getId() != null) {
            Table existingTable = tableService.findById(table.getId());
            table.setTotalColumns(existingTable.getTotalColumns());
        }

        table.setDatasource(datasource);
        table.setFriendlyName(text.toFirstLetterUppercase(table.getFriendlyName()));
        tableService.save(table);

        return "redirect:/datasources/{datasourceId}/tables/" + table.getId();
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}")
    public String openTable(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        if(datasource == null) return "redirect:/";
        model.addAttribute("datasource", datasource);

        Table table = tableService.findById(tableId);
        if(table == null) return "redirect:/datasources/{datasourceId}";
        model.addAttribute("table", table);
        
        model.addAttribute("progress", columnService.calculateProgress(table));
        model.addAttribute("userGuideUrl", userGuideUrl + "#table");

        table.setDocumentation(asciidoc.toHtml(table.getDocumentation()));

        return "table";
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/edit")
    public String editTable(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("table", tableService.findById(tableId));
        model.addAttribute("userGuideUrl", userGuideUrl + "#table-form");
        return "table_form";
    }
}