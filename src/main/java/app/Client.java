package app;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import model.RestApi;
import model.TransactionFactory;
import model.sepa.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by geoffrey on 26/04/17.
 */
public class Client {

    private JFrame mainFrame;
    private JComboBox comb;
    private JButton resume;
    private JButton stat;
    private JButton home;
    private JButton clear;
    private JButton depot;
    private JButton reset;
    private JButton trxbutton;
    private JButton deleteB;
    private JButton test;
    private JTextArea mntid;
    private JTextArea num;
    private JTextArea ident;
    private JTextArea montant;
    private JTextArea responseTA;
    private JTextArea bic;
    private JTextArea dbT;
    private JTextArea iban;
    private JTextArea comm;
    private JTextArea trx;
    private JTextArea delete;

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
        String[] monnaies = {"EUR","US"};
        comb = new JComboBox(monnaies);
        comb.setPreferredSize(new Dimension(5,20));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        resume = new JButton("Resume");
        stat = new JButton("Stat");
        home = new JButton("Home");
        clear = new JButton("Clear");
        depot = new JButton("Depot");
        reset = new JButton("Reset Database");
        trxbutton = new JButton("Rechercher Transaction :");
        test = new JButton("Add Example");
        deleteB = new JButton("Supprimer Transaction : ");
        delete = new JTextArea("1");
        delete.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        trx = new JTextArea("1");
        trx.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        ident = new JTextArea("Identifiant");
        ident.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        montant = new JTextArea("Montant");
        montant.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        bic = new JTextArea("BIC");
        bic.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        mntid = new JTextArea("Mandate Identifier");
        mntid.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        dbT = new JTextArea("Debtor Type");
        dbT.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        iban = new JTextArea("Iban");
        iban.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        comm = new JTextArea("Commentaires");
        comm.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        responseTA = new JTextArea(20,50);
        responseTA.setEditable(false);
    }

    private void placeComponents() {
        JPanel north = new JPanel(); {
            JPanel nc = new JPanel(); {
                nc.add(home);
                nc.add(resume);
                nc.add(stat);
            }
            JPanel ne = new JPanel(); {
                ne.add(clear, BorderLayout.EAST);
                ne.add(reset, BorderLayout.EAST);
            }
            north.add(nc, BorderLayout.CENTER);
            north.add(ne, BorderLayout.EAST);
        }
        mainFrame.add(north, BorderLayout.NORTH);
        JPanel center = new JPanel(); {
            JScrollPane scrollPane = new JScrollPane(responseTA);
            center.add(scrollPane);
        }
        mainFrame.add(center, BorderLayout.CENTER);
        JPanel west = new JPanel(); {
            west.setLayout(new GridLayout(0,1));
            west.add(comb);
            west.add(ident);
            west.add(montant);
            west.add(mntid);
            west.add(bic);
            west.add(dbT);
            west.add(iban);
            west.add(comm);
            west.add(depot);
        }
        mainFrame.add(west, BorderLayout.WEST);
        JPanel south = new JPanel(); {
            JPanel sw = new JPanel(); {
                sw.add(test);
            }
            JPanel se = new JPanel(); {
                se.add(deleteB);
                se.add(delete);
            }
            JPanel sc = new JPanel(); {
                sc.add(trxbutton);
                sc.add(trx);
            }
            south.add(sw, BorderLayout.WEST);
            south.add(sc);
            south.add(se, BorderLayout.EAST);

        }
        mainFrame.add(south, BorderLayout.SOUTH);
    }

    private void createController() {
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getResume();
                responseTA.setText(response);
            }
        });
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getReset();
                responseTA.setText(response);
            }
        });
        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getHome();
                responseTA.setText(response);
            }
        });
        deleteB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().delete(delete.getText());
                responseTA.setText(response);
            }
        });
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().test();
                responseTA.setText(response);
            }
        });
        trxbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getTransaction(trx.getText());
                responseTA.setText(response);
            }
        });
        stat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String response = new RestApi().getStat();
                responseTA.setText(response);
            }
        });
        depot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                DdtiType transaction = new DdtiType();
                // ID
                transaction.setPmtId(ident.getText());

                InstdAmtType iat = new InstdAmtType();
                iat.setCcy(comb.getSelectedItem().toString());
                try {
                    iat.setValue(new BigDecimal(montant.getText()));
                } catch (NumberFormatException e) {
                    responseTA.setText("<exception>Le montant n'est pas un nombre...</exception>");
                    return;
                }

                transaction.setInstdAmt(iat);

                DdtType ddtt = new DdtType();
                ddtt.setMndtId(mntid.getText());
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(new Date());
                XMLGregorianCalendar xmlDate = null;
                try {
                    xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                ddtt.setDtOfSgntr(xmlDate);
                transaction.setDrctDbtTx(ddtt);

                DAgentType dat = new DAgentType();
                dat.setBIC(bic.getText());
                transaction.setDbtrAgt(dat);

                DebtorType dt = new DebtorType();
                dt.setNm(dbT.getText());
                transaction.setDbtr(dt);

                DAccountType dact = new DAccountType();
                dact.setIBAN(iban.getText());
                transaction.setDbtrAcct(dact);

                transaction.getRmtInf().add(comm.getText());


                RootType transactions = new RootType();
                transactions.getDrctDbtTxInf().add(transaction);

                String response = null;
                try {
                    response = new RestApi().setDepot(transactions);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
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