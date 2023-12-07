package use_cases.data_visualization;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserGraphPanel extends JPanel {
    private JLabel graphLabel;

    private static final String BASE_PATH = "Habit Builder Project/src/use_cases/data_visualization/graphs/";
    private List<JLabel> userGraphLabels;

    public UserGraphPanel(List<String> usernames) {
        this.userGraphLabels = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loadUserGraphs(usernames);
        graphLabel = new JLabel();
        add(graphLabel);
    }
    public void updateGraphImage(String imagePath) {
        // verify that the file exists before trying to update the image
        File file = new File(imagePath);
        if (file.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            graphLabel.setIcon(icon);
            graphLabel.revalidate();
            graphLabel.repaint();
        } else {
            System.err.println("File does not exist: " + imagePath);
        }
    }


    private void loadUserGraphs(List<String> usernames) {
        for (String username : usernames) {
            ImageIcon imageIcon = createUserGraphIcon(username);
            if (imageIcon != null) {
                JLabel label = new JLabel(username + "'s Graph", imageIcon, JLabel.CENTER);
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                this.userGraphLabels.add(label);
                this.add(label);
            }
        }
    }




    private ImageIcon createUserGraphIcon(String username) {
        String path = BASE_PATH + username + "_chart.png";
        File file = new File(path);
        if (file.exists()) {
            ImageIcon imageIcon = new ImageIcon(path);
            Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } else {
            System.err.println("File not found: " + path);
            return null;
        }
    }
}

