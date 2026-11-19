
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Main extends JFrame {

    private BankOperations operations;

    private JLabel balanceLabel;
    private JTextArea historyArea;
    private JTextField amountField;

    private Color burgundy = new Color(90, 23, 45);
    private Color cream = new Color(255, 248, 240);

    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public Main() {

        BankAccount account =
            new BankAccount("Srishti Malhotra", "1234567890", 5000);

        operations = new BankOperations(account);

        setTitle("MiniBank - Banking System");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(cream);
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                20, 25, 20, 25));

        // Header
        JLabel heading = new JLabel("MiniBank");
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(burgundy);

        JLabel subtitle = new JLabel("Your Personal Banking Dashboard");
        subtitle.setForeground(Color.GRAY);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(cream);
        header.add(heading);
        header.add(subtitle);

        mainPanel.add(header, BorderLayout.NORTH);

        // Balance card
        JPanel balancePanel = new JPanel(new GridLayout(2, 1));
        balancePanel.setBackground(burgundy);
        balancePanel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel balanceTitle = new JLabel("AVAILABLE BALANCE");
        balanceTitle.setForeground(Color.WHITE);
        balanceTitle.setFont(new Font("Arial", Font.PLAIN, 14));

        balanceLabel = new JLabel("Rs. 5,000.00");
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 28));

        balancePanel.add(balanceTitle);
        balancePanel.add(balanceLabel);

        // Operations panel
        JPanel operationPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        operationPanel.setBackground(cream);

        JButton depositButton = createButton("Deposit Money");
        JButton withdrawButton = createButton("Withdraw Money");
        JButton detailsButton = createButton("Account Details");
        JButton historyButton = createButton("Clear History");

        operationPanel.add(depositButton);
        operationPanel.add(withdrawButton);
        operationPanel.add(detailsButton);
        operationPanel.add(historyButton);

        // Amount input
        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(cream);
        inputPanel.add(new JLabel("Enter Amount (Rs.):"),
                BorderLayout.WEST);
        inputPanel.add(amountField, BorderLayout.CENTER);

        // Transaction history
        historyArea = new JTextArea(7, 30);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(historyArea);

        JPanel centerPanel = new JPanel(new BorderLayout(12, 12));
        centerPanel.setBackground(cream);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(cream);
        topPanel.add(balancePanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.SOUTH);

        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(operationPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(cream);
        bottomPanel.add(new JLabel("TRANSACTION HISTORY"),
                BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Button actions
        depositButton.addActionListener(e -> depositMoney());

        withdrawButton.addActionListener(e -> withdrawMoney());

        detailsButton.addActionListener(e -> showDetails());

        historyButton.addActionListener(e -> {
            historyArea.setText("");
            historyArea.append("Transaction history cleared.");
        });

        refreshDisplay();

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(burgundy);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }

    private void depositMoney() {
        try {
            double amount = Double.parseDouble(
                    amountField.getText());

            if (operations.deposit(amount)) {
                JOptionPane.showMessageDialog(this,
                        "Money deposited successfully!");
                refreshDisplay();
                amountField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Enter a valid positive amount!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number!");
        }
    }

    private void withdrawMoney() {
        try {
            double amount = Double.parseDouble(
                    amountField.getText());

            if (operations.withdraw(amount)) {
                JOptionPane.showMessageDialog(this,
                        "Money withdrawn successfully!");
                refreshDisplay();
                amountField.setText("");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid amount or insufficient balance!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number!");
        }
    }

    private void showDetails() {
        BankAccount account = operations.getAccount();

        JOptionPane.showMessageDialog(this,
                "Account Holder: " + account.getName()
                + "\nAccount Number: " + account.getAccountNumber()
                + "\nCurrent Balance: Rs. "
                + df.format(account.getBalance()),
                "Account Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshDisplay() {
        BankAccount account = operations.getAccount();

        balanceLabel.setText(
                "Rs. " + df.format(account.getBalance()));

        historyArea.setText("");

        for (String transaction : operations.getTransactions()) {
            historyArea.append(transaction + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
