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

import org.springframework.stereotype.Component;

@Component
public class Text {

    public String toFirstLetterUppercase(final String text) {
        if(text == null || text.isEmpty())
            return text;
        
        String lowerText = text.toLowerCase();
        String[] words = lowerText.split("\\s+");
        StringBuilder output = new StringBuilder(words.length);
        String separator = "";
        for(String word : words) {
            String firstChar = Character.toString(word.charAt(0));
            output.append(separator);
            output.append(word.replaceFirst(firstChar, firstChar.toUpperCase()));
            separator = " ";
        }
        return output.toString();
    }

    public String emptyIfNull(String value) {
        return value == null ? "" : value;
    }
}