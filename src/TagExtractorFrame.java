import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class TagExtractorFrame extends JFrame {
    Set<String> stopWords = new TreeSet<>(); //cant have duplicates, stores noise words, sorts automatically
    Map<String, Integer> tagMap = new TreeMap<>();// stores key and value, sorts automatically

    //gui
    JTextArea displayArea;
    JLabel fileNameLbl;
    Path bookPath;

    public TagExtractorFrame() {

    }

    public void loadStopWords () {

    }

    private void extractTags() {

    }

    private void saveTags() {

    }
}
