/*
 * Digger
 * Copyright (C) 2019-2020 Hildeberto Mendonca
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