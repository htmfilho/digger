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