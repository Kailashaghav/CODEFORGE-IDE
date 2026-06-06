package com.codeforge.highlighters;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.*;

public class SyntaxHighlighter {

    private static final String[] KEYWORDS = {
        "abstract","assert","boolean","break","byte","case","catch","char",
        "class","const","continue","default","do","double","else","enum",
        "extends","final","finally","float","for","goto","if","implements",
        "import","instanceof","int","interface","long","native","new",
        "package","private","protected","public","return","short","static",
        "strictfp","super","switch","synchronized","this","throw","throws",
        "transient","try","void","volatile","while","var","record","sealed"
    };

    private static final String KEYWORD_PATTERN   = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String STRING_PATTERN    = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN   = "//[^\n]*|/\\*(.|\\R)*?\\*/";
    private static final String NUMBER_PATTERN    = "\\b\\d+(\\.\\d+)?\\b";
    private static final String ANNOTATION_PATTERN= "@\\w+";

    private static final Pattern PATTERN = Pattern.compile(
        "(?<KEYWORD>"    + KEYWORD_PATTERN    + ")" +
        "|(?<STRING>"    + STRING_PATTERN     + ")" +
        "|(?<COMMENT>"   + COMMENT_PATTERN    + ")" +
        "|(?<NUMBER>"    + NUMBER_PATTERN     + ")" +
        "|(?<ANNOTATION>"+ ANNOTATION_PATTERN + ")"
    );

    public static void highlight(CodeArea codeArea, String text) {
        codeArea.setStyleSpans(0, computeHighlighting(text));
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        int lastEnd = 0;
        while (matcher.find()) {
            String styleClass =
                matcher.group("KEYWORD")    != null ? "keyword"    :
                matcher.group("STRING")     != null ? "string"     :
                matcher.group("COMMENT")    != null ? "comment"    :
                matcher.group("NUMBER")     != null ? "number"     :
                matcher.group("ANNOTATION") != null ? "annotation" : null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastEnd);
        return spansBuilder.create();
    }
}
