import model.RestApi;
import model.TransactionFactory;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by geoffrey on 26/04/17.
 */
public class Client {

    private JFrame mainFrame;
    private JButton resume;
    private JButton stat;
    private JButton home;
    private JButton clear;
    private JButton depot;
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
        stat = new JButton("Stat");
        home = new JButton("Home");
        clear = new JButton("Clear");
        depot = new JButton("Depot");
        responseTA = new JTextArea(10, 20);
        responseTA.setEditable(false);
    }

    private void placeComponents() {
        JPanel north = new JPanel(); {
            north.add(resume);
            north.add(stat);
            north.add(home);
            north.add(clear);
            north.add(depot);
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
        stat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getStat();
                System.out.println(response);
                responseTA.setText(response);
            }
        });
        depot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = null;
                try {
                    response = new RestApi().setDepot(TransactionFactory.exampleRootType());
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
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
