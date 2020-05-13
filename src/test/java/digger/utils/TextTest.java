package digger.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TextTest {

    @Test
    public void toFirstLetterUppercaseTest() {
        Text text = new Text();

        assertThat(text.toFirstLetterUppercase("")).isEqualTo("");
        assertThat(text.toFirstLetterUppercase(null)).isEqualTo(null);
        assertThat(text.toFirstLetterUppercase("digger  is    great!")).isEqualTo("Digger Is Great!");
        assertThat(text.toFirstLetterUppercase("Digger Is Great!")).isEqualTo("Digger Is Great!");
    }

    @Test
    public void emptyIfNullTest() {
        Text text = new Text();

        assertThat(text.emptyIfNull(null)).isEqualTo("");
        assertThat(text.emptyIfNull("Digger")).isEqualTo("Digger");
    }
}