package ui;

import model.Priority;
import model.Status;
import model.Task;
import service.TaskManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final TaskManager taskManager = new TaskManager();

    public MainFrame() {

        setTitle("Todo List with Reminder");
        setSize(950, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Font titleFont = new Font("Arial", Font.BOLD, 24);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Font tableFont = new Font("Arial", Font.PLAIN, 14);
        Font labelFont = new Font("Arial", Font.BOLD, 13);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 248, 255));
        topPanel.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel titleLabel = new JLabel("Todo List with Reminder", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(40, 60, 120));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Title", "Description", "Due Date", "Priority", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(tableFont);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(220, 230, 250));
        table.getTableHeader().setForeground(new Color(30, 30, 30));
        table.setSelectionBackground(new Color(210, 225, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                String status = table.getValueAt(row, 4).toString();

                if (isSelected) {
                    c.setBackground(new Color(210, 225, 255));
                    c.setForeground(Color.BLACK);
                } else if (status.equals("OVERDUE")) {
                    c.setBackground(new Color(255, 204, 204));
                    c.setForeground(new Color(120, 0, 0));
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        buttonPanel.setBackground(new Color(245, 248, 255));

        addButton = createStyledButton("Add", new Color(76, 175, 80), buttonFont);
        editButton = createStyledButton("Edit", new Color(33, 150, 243), buttonFont);
        deleteButton = createStyledButton("Delete", new Color(244, 67, 54), buttonFont);
        doneButton = createStyledButton("Done", new Color(139, 128, 0), buttonFont);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(doneButton);

        add(buttonPanel, BorderLayout.SOUTH);

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
                Task task = taskManager.getAllTasks().get(selectedRow);
                taskManager.deleteTask(task);
                refreshTable();
            }
        });

        doneButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a task first");
            } else {
                Task task = taskManager.getAllTasks().get(selectedRow);
                taskManager.updateTaskStatus(task, Status.DONE);
                refreshTable();
            }
        });

        startReminder();

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 38));
        return button;
    }

    private void showTaskDialog(int rowIndex) {
        JDialog dialog = new JDialog(this, rowIndex == -1 ? "Add Task" : "Edit Task", true);
        dialog.setSize(430, 340);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.BOLD, 13);
        Font fieldFont = new Font("Arial", Font.PLAIN, 13);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(15, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField();
        JTextField descriptionField = new JTextField();

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dueDateSpinner = new JSpinner(dateModel);
        dueDateSpinner.setEditor(new JSpinner.DateEditor(dueDateSpinner, "yyyy-MM-dd HH:mm"));

        JComboBox<Priority> priorityBox = new JComboBox<>(Priority.values());
        JComboBox<Status> statusBox = new JComboBox<>(Status.values());

        titleField.setFont(fieldFont);
        descriptionField.setFont(fieldFont);
        dueDateSpinner.setFont(fieldFont);
        priorityBox.setFont(fieldFont);
        statusBox.setFont(fieldFont);

        if (rowIndex != -1) {
            Task task = taskManager.getAllTasks().get(rowIndex);

            titleField.setText(task.getTitle());
            descriptionField.setText(task.getDescription());

            try {
                java.util.Date date = java.util.Date.from(
                        task.getDueDateAndTime().atZone(ZoneId.systemDefault()).toInstant()
                );
                dueDateSpinner.setValue(date);
            } catch (Exception ignored) {
            }

            priorityBox.setSelectedItem(task.getPriority());
            statusBox.setSelectedItem(task.getStatus());
        }

        addFormRow(formPanel, gbc, 0, "Title:", titleField, labelFont);
        addFormRow(formPanel, gbc, 1, "Description:", descriptionField, labelFont);
        addFormRow(formPanel, gbc, 2, "Due Date:", dueDateSpinner, labelFont);
        addFormRow(formPanel, gbc, 3, "Priority:", priorityBox, labelFont);
        addFormRow(formPanel, gbc, 4, "Status:", statusBox, labelFont);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBackground(Color.WHITE);

        JButton saveButton = createStyledButton("Save", new Color(76, 175, 80), new Font("Arial", Font.BOLD, 13));
        JButton cancelButton = createStyledButton("Cancel", new Color(158, 158, 158), new Font("Arial", Font.BOLD, 13));

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

            Priority priority = (Priority) priorityBox.getSelectedItem();
            Status status = (Status) statusBox.getSelectedItem();

            if (rowIndex == -1) {
                Task task = new Task(title, description, dateTime, priority, status);
                taskManager.addTask(task);
            } else {
                Task task = taskManager.getAllTasks().get(rowIndex);
                taskManager.editTask(task, title, description, dateTime, priority, status);
            }

            refreshTable();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        actionPanel.add(saveButton);
        actionPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(actionPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row,
                            String labelText, JComponent field, Font labelFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(new Color(50, 50, 50));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(field, gbc);
    }

    private void startReminder() {
        Timer timer = new Timer(60000, e -> {
            taskManager.checkOverDue();
            refreshTable();
        });
        timer.start();

        taskManager.checkOverDue();
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Task task : taskManager.getAllTasks()) {
            tableModel.addRow(new Object[]{
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDateAndTime().format(formatter),
                    task.getPriority(),
                    task.getStatus()
            });
        }

        table.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}