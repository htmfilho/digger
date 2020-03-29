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
        String content = "= " +
                datasource.getName() +
                "\nv0.5.0, 2020-03-24\n\n" +
                datasource.getDescription() +
                "\n\n[cols=2*,options=header]\n|===\n|Attribute\n|Value\n\n" +
                "|Driver\n|" +
                datasource.getDriver() +
                "\n\n|Url\n|" +
                datasource.getUrl() +
                "\n|===\n\n" +
                "== Tables\n\n";
        return content;
    }

    @Override
    public String applyFormatToTable(Table table) {
        String content = "=== " +
                table.getName() +
                "\n\n" +
                table.getDocumentation() +
                "\n\n==== Columns" +
                "\n\n";
        return content;
    }

    @Override
    public String applyFormatToColumn(Column column) {
        String content = "===== " +
                column.getName() +
                "\n\n" +
                column.getDocumentation() +
                "\n\n";
        return content;
    }

    @Override
    public String applyFormatToIgnoredTable(IgnoredTable ignoredTable) {
        return "";
    }

    @Override
    public String generateHTMLDocument(String asciidocContent) {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        String httpContent = asciidoctor.convert(asciidocContent, new HashMap<>());
        httpContent = "<!DOCTYPE html><html lang=\"en\"><body>"+ httpContent +"</body></html>";
        return httpContent;
    }

    @Override
    public Path generatePdfDocument(String asciidocContent) {
        return null;
    }
}
