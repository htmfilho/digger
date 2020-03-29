package digger.web.controller;

import digger.adapter.SupportedFormat;
import digger.model.Datasource;
import digger.service.DatasourceService;
import digger.service.DocumentationService;
import digger.service.TableService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DatasourceController {

    private final DatasourceService datasourceService;
    private final TableService tableService;
    private final DocumentationService documentationService;

    @Value("${user.guide.url}")
    private String userGuideUrl;

    public DatasourceController(DatasourceService datasourceService, TableService tableService, DocumentationService documentationService) {
        this.datasourceService = datasourceService;
        this.tableService = tableService;
        this.documentationService = documentationService;
    }

    @GetMapping("/datasources/new")
    public String newDatasource(Model model) {
        model.addAttribute("datasource", new Datasource());
        model.addAttribute("userGuideUrl", userGuideUrl + "#new_datasource");
        return "datasource_form";
    }

    @PostMapping("/datasources")
    public String saveDatasource(@ModelAttribute Datasource datasource) {
        if (datasource.getId() != null) {
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
        model.addAttribute("userGuideUrl", userGuideUrl + "#datasource");
        return "datasource";
    }

    @GetMapping("/datasources/{datasourceId}/edit")
    public String editDatasource(Model model, @PathVariable Long datasourceId) {
        model.addAttribute("datasource", datasourceService.findById(datasourceId));
        model.addAttribute("userGuideUrl", userGuideUrl + "#edit_datasource");
        return "datasource_form";
    }

    @GetMapping("/datasources/{datasourceId}/documentation/html")
    public void getHtmlDocument(HttpServletRequest request, HttpServletResponse response, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        String htmlDocument = documentationService.generateHTMLDocument(datasource, SupportedFormat.ASCIIDOC);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getOutputStream().write(htmlDocument.getBytes());
            response.getOutputStream().flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @GetMapping("/datasources/{datasourceId}/documentation/pdf")
    public void getPdfDocument(HttpServletRequest request, HttpServletResponse response, @PathVariable Long datasourceId) {
        Datasource datasource = datasourceService.findById(datasourceId);
        Path pdfDocument = documentationService.generatePdfDocument(datasource, SupportedFormat.ASCIIDOC);
        //Path file = //Paths.get(dataDirectory, fileName);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        //response.addHeader("Content-Disposition", "attachment; filename="+ datasource.getName() +".pdf");
        try {
            Files.copy(pdfDocument, response.getOutputStream());
            response.getOutputStream().flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}