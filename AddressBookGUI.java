import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Contact {
    private String name;
     String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

public class AddressBookGUI extends JFrame implements ActionListener {
    private JTextField nameField, phoneField;
    private JButton addButton, deleteButton, editButton, searchButton;
    private JTextArea displayArea;
    private ArrayList<Contact> contacts;
    private ArrayList<JCheckBox> checkBoxes;

    public AddressBookGUI() {
        contacts = new ArrayList<>();
        checkBoxes = new ArrayList<>();

        setTitle("Address Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Name: "));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Phone: "));
        phoneField = new JTextField();
        panel.add(phoneField);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        panel.add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        editButton = new JButton("Edit");
        editButton.addActionListener(this);
        panel.add(editButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        panel.add(searchButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(checkBoxPanel, BorderLayout.EAST);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            contacts.add(new Contact(name, phone));
            displayContacts();
        } else if (e.getSource() == deleteButton) {
            String nameToDelete = nameField.getText();
            contacts.removeIf(contact -> contact.getName().equals(nameToDelete));
            displayContacts();
        } else if (e.getSource() == editButton) {
            String phone = phoneField.getText();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    contacts.get(i).phoneNumber = phone;
                }
            }
            displayContacts();
        } else if (e.getSource() == searchButton) {
            String name = nameField.getText();
            for (Contact contact : contacts) {
                if (contact.getName().equals(name)) {
                    displayArea.setText("Name: " + contact.getName() + "\nPhone: " + contact.getPhoneNumber());
                    return;
                }
            }
            displayArea.setText("Contact not found.");
        }
    }

    private void displayContacts() {
        StringBuilder sb = new StringBuilder();
        checkBoxes.clear();
        for (Contact contact : contacts) {
            JCheckBox checkBox = new JCheckBox(contact.getName());
            checkBoxes.add(checkBox);
            sb.append("Name: ").append(contact.getName()).append(", Phone: ").append(contact.getPhoneNumber()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddressBookGUI::new);
    }
}
