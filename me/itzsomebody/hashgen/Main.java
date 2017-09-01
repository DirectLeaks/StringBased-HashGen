package me.itzsomebody.hashgen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.swing.*;

public class Main extends JFrame {
	private static String[] options = {"MD5 Hash", "SHA-1 Hash", "SHA-256 Hash"};
	
	JPanel row1 = new JPanel();
	JPanel row2 = new JPanel();
	JPanel row3 = new JPanel();
	
	JComboBox dropMenu = new JComboBox();
	
	JTextField input;
	JTextField output;
	
	int count = 0;
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(
				"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
			);
		} catch (Exception exc) {}
	}
	
    public static void main(String[] args) throws IOException {
    	createGUI();
    }
    
    private static void createGUI() {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception exc) {}
                
                final Main main = new Main();
                main.setTitle("Hash Generator");
                main.setResizable(false);
                main.setSize(500, 135);
                main.setLocationRelativeTo(null);
                main.setDefaultCloseOperation(3);
                main.getContentPane().setLayout(new FlowLayout());
                final JLabel label2 = new JLabel("Input: ");
                final JLabel label3 = new JLabel("Output: ");
                main.input = new JTextField();
                main.input.setEditable(true);
                main.input.setColumns(15);
                main.output = new JTextField();
                main.output.setEditable(true);
                main.output.setColumns(35);
                final JButton genHash = new JButton("Generate Hash");
                for (int i = 0; i < options.length; i++) {
                    main.dropMenu.addItem(options[main.count++]);
                }
                genHash.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        if (!main.input.getText().isEmpty()) {
                        	if (main.dropMenu.getSelectedIndex() == 0) {
                            	main.output.setText(getGeneratedHash("MD5", main.input.getText()));
                            } else if (main.dropMenu.getSelectedIndex() == 1) {
                            	main.output.setText(getGeneratedHash("SHA-1", main.input.getText()));
                            } else if (main.dropMenu.getSelectedIndex() == 2) {
                            	main.output.setText(getGeneratedHash("SHA-256", main.input.getText()));
                            }
                        } else {
                        	main.output.setText("");
                        }
                    }
                });
                final JPanel panel = new JPanel(new FlowLayout());
                final JPanel panel2 = new JPanel(new FlowLayout());
                final JPanel panel3 = new JPanel(new FlowLayout());
                final JPanel border = new JPanel(new BorderLayout());
                
                panel2.add(label2);
                panel2.add(main.input);
                panel2.add(main.dropMenu);
                panel.add(label3);
                panel.add(main.output);
                panel3.add(genHash);
                
                border.add(panel, "Center");
                border.add(panel2, "North");
                border.add(panel3, "South");
                main.getContentPane().add(border);
                main.setVisible(true);
    	};
    });
}
    private static String getGeneratedHash(String algorithm, String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            digest.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] hash = digest.digest();
            StringBuilder hashHex = new StringBuilder();
            for (byte aHash : hash) {
                hashHex.append(Integer.toString((aHash & 255) + 256, 16).substring(1));
            }
            return hashHex.toString();
        }
        catch (Throwable t) {
            t.printStackTrace();
            return content;
        }
    }
}


