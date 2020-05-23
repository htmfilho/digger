package digger.utils;

import org.asciidoctor.Asciidoctor.Factory;
import org.asciidoctor.Asciidoctor;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Asciidoc {

    Asciidoctor asciidoctor;
    HashMap<String, Object> options;

    public Asciidoc() {
        this.asciidoctor = Factory.create();
        this.options = new HashMap<String, Object>();
    }

    public String toHtml(final String asciidoc) {
        return this.asciidoctor.convert(asciidoc, this.options);
    }
}