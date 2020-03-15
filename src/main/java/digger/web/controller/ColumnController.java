package digger.web.controller;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.Table;
import digger.service.ColumnService;
import digger.service.DatasourceService;
import digger.service.TableService;
import digger.utils.Asciidoc;
import digger.utils.Text;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ColumnController {

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final ColumnService columnService;
    private final Asciidoc asciidoc;
    private final Text text;

    public ColumnController(DatasourceService datasourceService, TableService tableService, ColumnService columnService, Asciidoc asciidoc, Text text) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
        this.columnService = columnService;
        this.asciidoc = asciidoc;
        this.text = text;
    }

    @RequestMapping("/datasources/{datasourceId}/tables/{tableId}/columns/new")
    public String newColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("table", tableService.findById(tableId));
        model.addAttribute("column", new Column());
        return "column_form";
    }

    @PostMapping("/datasources/{datasourceId}/tables/{tableId}/columns")
    public String saveColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId, @ModelAttribute Column column) {
        boolean newOne = column.getId() == null;
        Table table = tableService.findById(tableId);
        column.setTable(table);
        column.setFriendlyName(text.toFirstLetterUppercase(column.getFriendlyName()));
        columnService.save(column);

        if (newOne)
            return "redirect:/datasources/{datasourceId}/tables/{tableId}";
        else
            return "redirect:/datasources/{datasourceId}/tables/{tableId}/columns/" + column.getId();
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}")
    public String openColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId, @PathVariable Long columnId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        if(datasource == null) return "redirect:/";
        model.addAttribute("datasource", datasource);

        Table table = tableService.findById(tableId);
        if(table == null) return "redirect:/datasources/{datasourceId}";
        model.addAttribute("table", table);

        Column column = columnService.findById(columnId);
        if(column == null) return "redirect:/datasources/{datasourceId}/tables/{tableId}";
        
        column.setDocumentation(asciidoc.toHtml(column.getDocumentation()));

        model.addAttribute("column", column);

        return "column";
    }

    @GetMapping("/datasources/{datasourceId}/tables/{tableId}/columns/{columnId}/edit")
    public String editColumn(Model model, @PathVariable Long datasourceId, @PathVariable Long tableId, @PathVariable Long columnId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("table", tableService.findById(tableId));
        model.addAttribute("column", columnService.findById(columnId));
        return "column_form";
    }
}