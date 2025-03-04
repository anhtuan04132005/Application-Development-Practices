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
        setTitle("Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBackground(Color.DARK_GRAY);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(text.matches("[0-9]") ? Color.LIGHT_GRAY : Color.ORANGE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            if (isOperatorClicked) {
                display.setText(command);
                isOperatorClicked = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("");
            num1 = num2 = 0;
            operator = "";
        } else if (command.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            double result = calculate(num1, num2, operator);
            display.setText(String.valueOf(result));
            operator = "";
        } else {
            num1 = Double.parseDouble(display.getText());
            operator = command;
            isOperatorClicked = true;
        }
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return (b != 0) ? a / b : 0;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}