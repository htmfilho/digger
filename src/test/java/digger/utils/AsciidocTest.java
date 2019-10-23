package digger.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AsciidocTest {

    @Test
    public void toHtml() {
        Asciidoc asciidoc = new Asciidoc();
        String html = asciidoc.toHtml("== Test");
        assertThat(html).contains("</h2>");
    }
}