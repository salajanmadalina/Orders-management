package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @Author: Salajan Madalina-Maria
 * @Since: Apr 13, 2022
 */

public class SecondView extends JFrame{

    private static JFrame frame;
    private JLabel lblNewLabel;
    private JButton btnInsert;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnShow;
    private JButton btnBack;

    public SecondView(String name){

        MainView.getFrame().setVisible(false);
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(204, 153, 153));
        frame.getContentPane().setForeground(new Color(210, 180, 140));
        frame.setBounds(100, 100, 628, 381);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        lblNewLabel = new JLabel(name);
        lblNewLabel.setFont(new Font("Algerian", Font.PLAIN, 30));
        lblNewLabel.setBounds(235, 34, 170, 40);
        frame.getContentPane().add(lblNewLabel);

        btnInsert = new JButton("Insert");
        btnInsert.setBackground(new Color(255, 204, 204));
        btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnInsert.setBounds(64, 133, 130, 55);
        frame.getContentPane().add(btnInsert);

        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnDelete.setBackground(new Color(255, 204, 204));
        btnDelete.setBounds(235, 133, 130, 55);
        frame.getContentPane().add(btnDelete);

        btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnUpdate.setBackground(new Color(255, 204, 204));
        btnUpdate.setBounds(411, 133, 130, 55);
        frame.getContentPane().add(btnUpdate);

        btnShow = new JButton("Show table");
        btnShow.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnShow.setBackground(new Color(255, 204, 204));
        btnShow.setBounds(106, 235, 170, 55);
        frame.getContentPane().add(btnShow);

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnBack.setBackground(new Color(255, 204, 204));
        btnBack.setBounds(317, 235, 170, 55);
        frame.getContentPane().add(btnBack);

        frame.setVisible(true);
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnShow() {
        return btnShow;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public void addInsertListener(ActionListener mal){btnInsert.addActionListener(mal);}
    public void addDeleteListener(ActionListener mal){btnDelete.addActionListener(mal);}
    public void addUpdateListener(ActionListener mal){btnUpdate.addActionListener(mal);}
    public void addShowListener(ActionListener mal){btnShow.addActionListener(mal);}
    public void addBackListener(ActionListener mal){btnBack.addActionListener(mal);}

}
