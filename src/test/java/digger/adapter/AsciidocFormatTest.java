package digger.adapter;

import digger.model.Datasource;
import org.junit.Test;

public class AsciidocFormatTest {

    @Test
    public void applyFormatToDatasource() {
        Datasource datasource = new Datasource();
        datasource.setName("Datasource");
        datasource.setDescription("Description");

        String content = datasource.getFormattedDocumentation(SupportedFormat.ASCIIDOC);
        System.out.println(content);
    }

    @Test
    public void applyFormatToTable() {
    }

    @Test
    public void applyFormatToColumn() {
    }

    @Test
    public void applyFormatToIgnoredTable() {
    }
}