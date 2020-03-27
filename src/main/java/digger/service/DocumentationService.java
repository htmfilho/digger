package digger.service;

import digger.adapter.SupportedFormat;
import digger.model.Column;
import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Service
public class DocumentationService {

    private TableService tableService;
    private ColumnService columnService;
    private IgnoredTableService ignoredTableService;

    public DocumentationService(TableService tableService, ColumnService columnService, IgnoredTableService ignoredTableService) {
        this.tableService = tableService;
        this.columnService = columnService;
        this.ignoredTableService = ignoredTableService;
    }

    public String generateHTMLDocument(Datasource datasource, SupportedFormat format) {
        String content = generateContent(datasource, format);
        return format.getDocumentationFormat().generateHTMLDocument(content);
    }

    public Path generatePdfDocument(Datasource datasource, SupportedFormat format) {
        String content = generateContent(datasource, format);
        return null;
    }

    public String generateContent(Datasource datasource, SupportedFormat format) {
        StringBuilder document = new StringBuilder();

        document.append(datasource.getFormattedDocumentation(format));

        List<Table> tables = tableService.findByDatasource(datasource);
        for(Table table : tables) {
            document.append(table.getFormattedDocumentation(format));

            List<Column> columns = columnService.findByTable(table);
            for(Column column : columns) {
                document.append(column.getFormattedDocumentation(format));
            }
        }

        List<IgnoredTable> ignoredTables = ignoredTableService.findByDatasource(datasource);
        for(IgnoredTable ignoredTable : ignoredTables) {
            document.append(ignoredTable.getFormattedDocumentation(format));
        }

        return document.toString();
    }
}
