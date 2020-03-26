package digger.adapter;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;

public class AsciidocFormat implements DocumentationFormat {
    @Override
    public String applyFormatToColumn(Column column) {
        StringBuilder content = new StringBuilder();
        return content.toString();
    }

    @Override
    public String applyFormatToTable(Table table) {
        StringBuilder content = new StringBuilder();
        return content.toString();
    }

    @Override
    public String applyFormatToDatasource(Datasource datasource) {
        StringBuilder content = new StringBuilder();
        content.append("== ");
        content.append(datasource.getName());
        content.append("\n\n");
        content.append(datasource.getDescription());

        content.append("[cols=2*,options=header]\n|===\n|Attribute\n|Value\n\n");
        content.append("|Driver\n|");
        content.append(datasource.getDriver());

        content.append("\n\n|Url\n|");
        content.append(datasource.getUrl());

        content.append("\n|===\n\n");

        content.append("=== Tables\n\n");

        return content.toString();
    }

    @Override
    public String applyFormatToIgnoredTable(IgnoredTable ignoredTable) {
        StringBuilder content = new StringBuilder();
        return content.toString();
    }
}
