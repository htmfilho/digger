/*
 * Digger
 * Copyright (C) 2019-2022 Hildeberto Mendonca
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A full copy of the GNU General Public License is available at:
 * https://github.com/htmfilho/digger/blob/master/LICENSE
 */

package digger.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

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