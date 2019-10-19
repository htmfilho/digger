package digger.web.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class AsciidocTest {

    @Test
    public void toHtml() {
        Asciidoc asciidoc = new Asciidoc();
        String html = asciidoc.toHtml("== Test");
        assertThat(html).contains("</h2>");
    }
}