import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator = "";
    private double num1 = 0, num2 = 0;
    private boolean isOperatorClicked = false;

    public Calculator() {
        setTitle("Calc"); 
        setSize(200, 300); 
        setDefaultCloseOperation(HIDE_ON_CLOSE); 
        setLayout(new FlowLayout()); 
        setResizable(true); 
        
        display = new JTextField(5); 
        display.setEditable(true);
        display.setHorizontalAlignment(JTextField.LEFT); 
        display.setFont(new Font("Arial", Font.PLAIN, 10)); 
        add(display);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3)); 
        
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=" 
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 10)); 
            button.setBackground(Color.GRAY); 
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]") || command.equals(".")) { 
            display.setText(display.getText() + command);
        } else if (command.equals("C")) {
            display.setText("0"); // 
        } else if (command.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            double result = calculate(num1, num2, operator);
            display.setText("Kết quả: " + result); // 
        } else {
            num1 = Double.parseDouble(display.getText());
            operator = command;
        }
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b; 
            default: return 0;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
