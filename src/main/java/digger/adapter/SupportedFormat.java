package digger.adapter;

public enum SupportedFormat {

    ASCIIDOC(new AsciidocFormat());

    private DocumentationFormat documentationFormat;

    SupportedFormat(DocumentationFormat documentationFormat) {
        this.documentationFormat = documentationFormat;
    }

    public DocumentationFormat getDocumentationFormat() {
        return documentationFormat;
    }
}