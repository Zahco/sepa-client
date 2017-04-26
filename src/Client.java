import Model.RestApi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by geoffrey on 26/04/17.
 */
public class Client {

    private JFrame mainFrame;
    private JButton resume;
    private JButton echo;
    private JButton home;
    private JButton clear;
    private JTextArea responseTA;

    public Client() {
        createModel();
        createView();
        placeComponents();
        createController();
    }

    private void createModel() {
    }

    private void createView() {
        mainFrame = new JFrame("sepa-client");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resume = new JButton("Resume");
        echo = new JButton("Echo");
        home = new JButton("Home");
        clear = new JButton("Clear");
        responseTA = new JTextArea(5, 20);
        responseTA.setEditable(false);
    }

    private void placeComponents() {
        JPanel north = new JPanel(); {
            north.add(resume);
            north.add(echo);
            north.add(home);
            north.add(clear);
        }
        mainFrame.add(north, BorderLayout.NORTH);
        JPanel center = new JPanel(); {
            JScrollPane scrollPane = new JScrollPane(responseTA);
            center.add(scrollPane);
        }
        mainFrame.add(center, BorderLayout.CENTER);
    }

    private void createController() {
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getResume();
                responseTA.setText(response);
            }
        });
        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getHome();
                responseTA.setText(response);
            }
        });
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                responseTA.setText("");
            }
        });
    }

    public void display() {
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Client().display();
            }
        });
    }
}
