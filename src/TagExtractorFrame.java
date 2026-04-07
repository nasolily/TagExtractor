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
        setLayout(new BorderLayout());

        //north
        fileNameLbl = new JLabel("No book loaded", SwingConstants.CENTER);
        fileNameLbl.setFont(new Font("Arial", Font.BOLD, 20));
        add(fileNameLbl, BorderLayout.NORTH);

        //mid
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        displayArea.setFont(new Font("Arial", Font.BOLD, 20));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Word Frequencies"));
        add(scrollPane, BorderLayout.CENTER);

        //south
        JPanel buttonPnl = new JPanel();
        JButton stopBtn = new JButton("Load stop words");
        stopBtn.addActionListener(e -> loadStopWords());

        JButton extractBtn = new JButton("Extract tags");
        extractBtn.addActionListener(e -> extractTags());

        JButton saveBtn = new JButton("Save tags");
        saveBtn.addActionListener(e -> saveTags());

        JButton quitBtn = new JButton("Quit");
        quitBtn.addActionListener(e -> System.exit(0));

        //add btns
        buttonPnl.add(stopBtn);
        buttonPnl.add(extractBtn);
        buttonPnl.add(saveBtn);
        buttonPnl.add(quitBtn);
        add(buttonPnl, BorderLayout.SOUTH);

    }

    public void loadStopWords () {
        JFileChooser chooser = new JFileChooser();
        if  (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (Stream<String> lines = Files.lines(chooser.getSelectedFile().toPath())) {
                stopWords.clear();
                lines.map(String::toLowerCase).forEach(stopWords::add);
                JOptionPane.showMessageDialog(this, "Stop words loaded");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading stop words");
            }
        }
    }

    private void extractTags() {
        if (stopWords.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please load stop words");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            bookPath = chooser.getSelectedFile().toPath();
            fileNameLbl.setText("Book title: " + bookPath.getFileName());
            tagMap.clear();
            displayArea.setText("");

            try (Stream<String> lines = Files.lines(bookPath)) {
                lines.forEach(line -> {
                    String[] words = line.toLowerCase().split("[^a-z]+");
                    for (String w : words) {
                        if (!w.isEmpty() && !stopWords.contains(w)) {
                            tagMap.put(w, tagMap.getOrDefault(w, 0) + 1);
                        }
                    }
                });

                tagMap.forEach((word, count) -> displayArea.append(word + ": " + count + "\n"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading the file.");
            }
        }
    }

    private void saveTags() {
        if (tagMap.isEmpty()) {
            JFileChooser saver =  new JFileChooser();
            if (saver.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (PrintWriter out = new PrintWriter(saver.getSelectedFile())) {
                    tagMap.forEach((word, count) -> out.println(word + ": " + count + "\n"));
                    JOptionPane.showMessageDialog(this, "Tags saved!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save tags.");
                }
            }
        }
    }
}
