import javax.swing.JFrame;

public class TagExtractorRunner {
    public static void main(String[] args) {
        TagExtractorFrame frame = new TagExtractorFrame();

        frame.setTitle("Keyword Extractor");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}