package digger.web.utils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class Markdown {

    Parser parser;
    HtmlRenderer renderer;

    public Markdown() {
        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();
    }

    public String toHtml(String markdown) {
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}