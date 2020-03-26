package digger.adapter;

import digger.model.Column;
import digger.model.Datasource;
import digger.model.IgnoredTable;
import digger.model.Table;

public interface DocumentationFormat {
    String applyFormatToColumn(Column column);

    String applyFormatToTable(Table table);

    String applyFormatToDatasource(Datasource datasource);

    String applyFormatToIgnoredTable(IgnoredTable ignoredTable);
}
