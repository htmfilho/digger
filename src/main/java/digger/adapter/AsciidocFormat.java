package digger.adapter;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;
import org.asciidoctor.Asciidoctor;

import java.nio.file.Path;
import java.util.HashMap;

public class AsciidocFormat implements DocumentationFormat {

    @Override
    public String applyFormatToDatasource(Datasource datasource) {
        StringBuilder content = new StringBuilder();
        content.append("= ");
        content.append(datasource.getName());
        content.append("\nv0.5.0, 2020-03-24\n\n");
        content.append(datasource.getDescription());

        content.append("\n\n[cols=2*,options=header]\n|===\n|Attribute\n|Value\n\n");
        content.append("|Driver\n|");
        content.append(datasource.getDriver());

        content.append("\n\n|Url\n|");
        content.append(datasource.getUrl());

        content.append("\n|===\n\n");

        content.append("== Tables\n\n");

        return content.toString();
    }

    @Override
    public String applyFormatToTable(Table table) {
        StringBuilder content = new StringBuilder();
        content.append("=== ");
        content.append(table.getName());
        content.append("\n\n");
        content.append(table.getDocumentation());
        content.append("\n\n==== Columns");
        content.append("\n\n");
        return content.toString();
    }

    @Override
    public String applyFormatToColumn(Column column) {
        StringBuilder content = new StringBuilder();
        content.append("===== ");
        content.append(column.getName());
        content.append("\n\n");
        content.append(column.getDocumentation());
        content.append("\n\n====== Attributes");
        content.append("\n\n");
        return content.toString();
    }

    @Override
    public String applyFormatToIgnoredTable(IgnoredTable ignoredTable) {
        StringBuilder content = new StringBuilder();
        return content.toString();
    }

    @Override
    public String generateHTMLDocument(String asciidocContent) {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        String httpContent = asciidoctor.convert(asciidocContent, new HashMap<String, Object>());
        httpContent = "<!DOCTYPE html><html lang=\"en\"><body>"+ httpContent +"</body></html>";
        return httpContent;
    }

    @Override
    public Path generatePdfDocument(String asciidocContent) {
        return null;
    }
}
