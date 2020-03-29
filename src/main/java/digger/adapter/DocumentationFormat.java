package digger.adapter;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;

import java.nio.file.Path;

public interface DocumentationFormat {
    String applyFormatToDatasource(Datasource datasource);

    String applyFormatToTable(Table table);

    String applyFormatToColumn(Column column);

    String applyFormatToIgnoredTable(IgnoredTable ignoredTable);

    String generateHTMLDocument(String content);

    Path generatePdfDocument(String content);
}
