# 📝 Todo List with Reminder

![Java](https://img.shields.io/badge/Java-17-orange)
![GUI](https://img.shields.io/badge/GUI-Java%20Swing-blue)
![Project](https://img.shields.io/badge/Project-Desktop%20Application-green)
![Status](https://img.shields.io/badge/Status-Completed-success)

A simple **Java Swing desktop application** that allows users to manage their daily tasks by adding, editing, deleting, and tracking tasks with priorities and due dates.

---

# 🚀 Features

✔ Add new tasks with title, description, due date, priority, and status  
✔ Edit existing tasks  
✔ Delete tasks  
✔ Mark tasks as **Done**  
✔ Automatic detection of **Overdue tasks**  
✔ Visual highlighting for overdue tasks  
✔ Simple and user-friendly desktop interface  

---

# 🛠 Technologies Used

- **Java**
- **Java Swing**
- **Object-Oriented Programming (OOP)**

---

# 📂 Project Structure

```
src
│
├── model
│   ├── Task.java
│   ├── Priority.java
│   └── Status.java
│
├── service
│   └── TaskManager.java
│
└── ui
    └── MainFrame.java
```

---

# 🧠 Application Architecture

The project is divided into three main layers:

## Model Layer

Contains the data structures used in the application.

### Task
Represents a task containing:

- title
- description
- due date
- priority
- status

### Priority (Enum)

```
HIGH
MEDIUM
LOW
```

Defines the allowed priority levels.

### Status (Enum)

```
PENDING
IN_PROGRESS
DONE
OVERDUE
```

Defines the allowed task states.

Enums ensure **type safety** and prevent invalid values.

---

## Service Layer

### TaskManager

Handles all task operations such as:

- adding tasks
- editing tasks
- deleting tasks
- updating task status
- detecting overdue tasks

### Overdue Detection

The method `checkOverDue()` checks if:

```
task.dueDate < currentTime
```

If the task is not completed, the status becomes **OVERDUE** automatically.

---

## UI Layer

The graphical interface is implemented using **Java Swing**.

### Main Components

**JTable**

Displays tasks in a table format.

**DefaultTableModel**

Stores and manages the table data.

**JDialog**

Used to open forms for adding and editing tasks.

**JSpinner**

Allows selecting due date and time.

**JComboBox**

Used for selecting:

- priority
- status

**Custom Table Renderer**

Highlights overdue tasks by changing row color.

**Timer Reminder System**

A background timer runs every minute to:

- check overdue tasks
- refresh the table

---

# ⚙️ How the Application Works

1️⃣ The application starts and displays the main window.

2️⃣ Tasks appear inside a **JTable**.

3️⃣ The user clicks **Add** to open a dialog form.

4️⃣ The task details are entered.

5️⃣ The `TaskManager` stores and manages tasks.

6️⃣ The table refreshes automatically.

7️⃣ A timer continuously checks if tasks are overdue.

8️⃣ Overdue tasks are highlighted.

---

# 💡 OOP Concepts Used

### Encapsulation
Using private variables with getters and setters.

### Enums
Used to define fixed values for priority and status.

### Separation of Concerns
The project separates:

- Data (Model)
- Logic (Service)
- Interface (UI)

---

# 🔧 Future Improvements

Possible improvements:

- Save tasks in a **database**
- Add **search functionality**
- Add **task filtering**
- Add **notifications**
- Improve UI design
- Add **persistent storage**

---

# ▶️ Getting Started

## Prerequisites

- Java JDK
- Java IDE (IntelliJ / Eclipse / NetBeans)

---

## Run the Project

### Clone the repository

```
git clone https://github.com/sohilaelabasy/todo-list-reminder.git
```

### Open the project in your IDE

### Run

```
MainFrame.java
```

---

# 👩‍💻 Author

**Sohaila Elabasy**

GitHub  
https://github.com/sohilaelabasy

---

⭐ If you like this project, feel free to give it a star!
