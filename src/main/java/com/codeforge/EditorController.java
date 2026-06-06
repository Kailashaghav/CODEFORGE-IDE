package com.codeforge;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import com.codeforge.highlighters.SyntaxHighlighter;

public class EditorController {

    @FXML private CodeArea codeArea;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            SyntaxHighlighter.highlight(codeArea, newText);
            int lines = codeArea.getParagraphs().size();
            int chars = newText.length();
            statusLabel.setText("Lines: " + lines + "  |  Characters: " + chars);
        });
        codeArea.replaceText("""
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Welcome to CodeForge IDE!");
    }
}
""");
    }

    @FXML
    private void handleNew() {
        codeArea.clear();
    }
}
