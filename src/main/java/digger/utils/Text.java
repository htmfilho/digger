package digger.utils;

import org.springframework.stereotype.Component;

@Component
public class Text {

    public String toFirstLetterUppercase(String text) {
        if(text == null || text.isEmpty())
            return text;
        
        text = text.toLowerCase();
        String[] words = text.split("\\s+");
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
}