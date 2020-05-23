package digger.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AsciidocTest {

    @Test
    public void toHtmlTest() {
        Asciidoc asciidoc = new Asciidoc();
        String html = asciidoc.toHtml("== Test");
        assertThat(html).contains("</h2>");
    }
}