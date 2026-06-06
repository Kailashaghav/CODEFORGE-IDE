package com.codeforge;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.*;

public class TerminalController {

    @FXML private TextArea terminalOutput;
    @FXML private TextField terminalInput;

    @FXML
    public void initialize() {
        terminalOutput.setText("CodeForge Terminal Ready.\n$ ");
        terminalOutput.setEditable(false);
    }

    @FXML
    private void handleCommand() {
        String command = terminalInput.getText().trim();
        if (command.isEmpty()) return;
        terminalOutput.appendText(command + "\n");
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                terminalOutput.appendText(line + "\n");
            }
        } catch (Exception e) {
            terminalOutput.appendText("Error: " + e.getMessage() + "\n");
        }
        terminalOutput.appendText("$ ");
        terminalInput.clear();
    }
}
