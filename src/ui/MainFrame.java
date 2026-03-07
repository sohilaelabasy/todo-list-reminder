package ui;

import model.Priority;
import model.Status;
import model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton doneButton;

    public MainFrame() {

        setTitle("Todo List with Reminder");
        setSize(950, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = {"Title", "Description", "Due Date", "Priority", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();

        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        doneButton = new JButton("Done");

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(doneButton);

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> showTaskDialog(-1));

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a task first");
            } else {
                showTaskDialog(selectedRow);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a task first");
            } else {
                tableModel.removeRow(selectedRow);
            }
        });

        doneButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a task first");
            } else {
                tableModel.setValueAt(Status.DONE, selectedRow, 4);
            }
        });

        setVisible(true);
    }

    private void showTaskDialog(int rowIndex) {
        JDialog dialog = new JDialog(this, rowIndex == -1 ? "Add Task" : "Edit Task", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2, 5, 5));
        dialog.setLocationRelativeTo(this);

        JTextField titleField = new JTextField();
        JTextField descriptionField = new JTextField();

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dueDateSpinner = new JSpinner(dateModel);
        dueDateSpinner.setEditor(new JSpinner.DateEditor(dueDateSpinner, "yyyy-MM-dd HH:mm"));

        JComboBox<Priority> priorityBox = new JComboBox<>(Priority.values());
        JComboBox<Status> statusBox = new JComboBox<>(Status.values());

        if (rowIndex != -1) {
            titleField.setText(tableModel.getValueAt(rowIndex, 0).toString());
            descriptionField.setText(tableModel.getValueAt(rowIndex, 1).toString());

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(tableModel.getValueAt(rowIndex, 2).toString(), formatter);
                java.util.Date date = java.util.Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                dueDateSpinner.setValue(date);
            } catch (Exception ignored) {
            }

            priorityBox.setSelectedItem(tableModel.getValueAt(rowIndex, 3));
            statusBox.setSelectedItem(tableModel.getValueAt(rowIndex, 4));
        }

        dialog.add(new JLabel("Title:"));
        dialog.add(titleField);

        dialog.add(new JLabel("Description:"));
        dialog.add(descriptionField);

        dialog.add(new JLabel("Due Date:"));
        dialog.add(dueDateSpinner);

        dialog.add(new JLabel("Priority:"));
        dialog.add(priorityBox);

        dialog.add(new JLabel("Status:"));
        dialog.add(statusBox);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descriptionField.getText().trim();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Title is required");
                return;
            }

            LocalDateTime dateTime = LocalDateTime.ofInstant(
                    ((java.util.Date) dueDateSpinner.getValue()).toInstant(),
                    ZoneId.systemDefault()
            );

            String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            Priority priority = (Priority) priorityBox.getSelectedItem();
            Status status = (Status) statusBox.getSelectedItem();

            if (rowIndex == -1) {
                tableModel.addRow(new Object[]{
                        title,
                        description,
                        formattedDate,
                        priority,
                        status
                });
            } else {
                tableModel.setValueAt(title, rowIndex, 0);
                tableModel.setValueAt(description, rowIndex, 1);
                tableModel.setValueAt(formattedDate, rowIndex, 2);
                tableModel.setValueAt(priority, rowIndex, 3);
                tableModel.setValueAt(status, rowIndex, 4);
            }

            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(saveButton);
        dialog.add(cancelButton);

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}