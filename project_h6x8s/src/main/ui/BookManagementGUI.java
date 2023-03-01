/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ui;

import model.Book;
import model.BookSystem;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/* This class presents a graphical user interface of Book Management App,
 * user can add books, remove books, load system from file and save system to file.
 * citation:
 * I wrote this class after looking at the provided program (ListDemo.java).
 * ListDemo.java: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
 * */
public class BookManagementGUI extends JPanel implements ListSelectionListener {
    private static final String JSON_STORE = "./data/bookSystem.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private BookSystem bookSystem;

    private JList<String> list;
    private DefaultListModel<String> listModel;

    private static final String addBookString = "Add Book";
    private static final String removeBookString = "Remove Book";
    private static final String loadString = "Load From File";
    private static final String saveString = "Save To File";

    private JButton addBookButton;
    AddBookListener addBookListener;
    private JButton removeBookButton;
    RemoveBookListener removeBookListener;

    private JTextField bookName;
    private JTextField bookPrice;
    private JTextField bookStock;
    private JTextField numOfRemovedStock;

    private JButton loadButton;
    private JButton saveButton;

    // MODIFIES: this
    // EFFECTS: construct frame with buttons and panels.
    public BookManagementGUI() {
        // set layout for this panel.
        super(new GridLayout(4, 1));

        bookSystem = new BookSystem();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // create a list, and it used to display items in listScrollPane.
        createList();
        // set a JScrollPanel to display, add the list in this panel.
        JScrollPane listScrollPane = new JScrollPane(list);

        // set AddBookButton.
        setAddBookButton();

        // setRemoveBookButton.
        setRemoveBookButton();

        // set LoadButton.
        setLoadButton();

        // set SaveButton.
        setSaveButton();

        // add listScrollPane to this panel.
        this.add(listScrollPane);

        // add first button panel to this panel.
        setButtonPanel1();

        // add second button panel to this panel.
        setButtonPanel2();

        // add third button panel to this panel.
        setButtonPanel3();
    }


    // MODIFIES: list
    // EFFECTS: create list to display, single selection model.
    public void createList() {
        listModel = new DefaultListModel<>();

        //Create the list.
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(20);
    }

    // MODIFIES: addBookButton, bookName, bookPrice, bookStock
    // EFFECTS: create add book button and corresponding text field.
    public void setAddBookButton() {
        // create add book button.
        addBookButton = new JButton(addBookString);
        // button matches listener.
        addBookListener = new AddBookListener(addBookButton);
        // button matches command.
        addBookButton.setActionCommand(addBookString);
        // button matches override addBookListener.
        addBookButton.addActionListener(addBookListener);
        addBookButton.setEnabled(false);

        // create a text field to take new book name.
        bookName = new JTextField(20);
        // bookName text field matches addBookListener.
        bookName.addActionListener(addBookListener);
        // update display after got new book name.
        bookName.getDocument().addDocumentListener(addBookListener);

        bookPrice = new JTextField(20);
        bookPrice.addActionListener(addBookListener);
        bookPrice.getDocument().addDocumentListener(addBookListener);

        bookStock = new JTextField(20);
        bookStock.addActionListener(addBookListener);
        bookStock.getDocument().addDocumentListener(addBookListener);
    }

    // MODIFIES: removeBookButton, numOfRemovedStock
    // EFFECTS: create remove book button and corresponding text field.
    public void setRemoveBookButton() {
        removeBookButton = new JButton(removeBookString);
        removeBookListener = new RemoveBookListener(removeBookButton);
        removeBookButton.setActionCommand(removeBookString);
        removeBookButton.addActionListener(new RemoveBookListener(removeBookButton));

        // to take the number of selected book stock user want to remove.
        numOfRemovedStock = new JTextField(20);
        numOfRemovedStock.addActionListener(removeBookListener);
        numOfRemovedStock.getDocument().addDocumentListener(removeBookListener);
    }

    // MODIFIES: loadButton
    // EFFECTS: set loading button.
    public void setLoadButton() {
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
    }

    // MODIFIES: saveButton
    // EFFECTS: set saving button.
    public void setSaveButton() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // MODIFIES: buttonPanel1, nameLabel, priceLabel, stockLabel, this
    // EFFECTS: arrange the first button panel, this panel are able to
    //          add new book to system.
    public void setButtonPanel1() {
        //Create a panel that uses BoxLayout.
        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new BoxLayout(buttonPanel1,
                BoxLayout.LINE_AXIS));

        // create a label to mention user enter new book name.
        JLabel nameLabel = new JLabel("New book name: ");
        // add the label to buttonPanel1.
        buttonPanel1.add(nameLabel);
        // add corresponding text field.
        buttonPanel1.add(bookName);
        // separating line
        buttonPanel1.add(new JSeparator(SwingConstants.VERTICAL));

        JLabel priceLabel = new JLabel("New book price: ");
        buttonPanel1.add(priceLabel);
        buttonPanel1.add(bookPrice);
        buttonPanel1.add(new JSeparator(SwingConstants.VERTICAL));

        JLabel stockLabel = new JLabel("New book stock: ");
        buttonPanel1.add(stockLabel);
        buttonPanel1.add(bookStock);
        buttonPanel1.add(new JSeparator(SwingConstants.VERTICAL));

        // blank space
        buttonPanel1.add(Box.createHorizontalStrut(5));
        // add addBookButton to buttonPanel1
        buttonPanel1.add(addBookButton);
        buttonPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // add buttonPanel1 to this panel.
        add(buttonPanel1);
    }

    // MODIFIES: buttonPanel2, removedStockLabel, this
    // EFFECTS: arrange the second button panel, this panel are able to
    //          remove book from system.
    public void setButtonPanel2() {
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new BoxLayout(buttonPanel2,
                BoxLayout.LINE_AXIS));

        buttonPanel2.add(Box.createHorizontalStrut(10));
        // the label can remind user to enter the amount of selected book they want to remove.
        JLabel removedStockLabel = new JLabel("Enter the number of selected book you want to remove: ");
        buttonPanel2.add(removedStockLabel);
        buttonPanel2.add(numOfRemovedStock);

        buttonPanel2.add(Box.createHorizontalStrut(10));
        buttonPanel2.add(removeBookButton);

        add(buttonPanel2);
        buttonPanel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // MODIFIES: buttonPanel3, this
    // EFFECTS: arrange the second button panel, this panel are able to
    //          load system from file, save system to file.
    //          Display an image in this panel.
    public void setButtonPanel3() {
        JPanel buttonPanel3 = new JPanel();
        buttonPanel3.setLayout(new BoxLayout(buttonPanel3,
                BoxLayout.LINE_AXIS));

        try {
            // create image icon.
            // Image resource: https://www.svgrepo.com/svg/181860/library-book
            ImageIcon icon = new ImageIcon("./data/bookImage.png");
            // get image from above image icon.
            Image image = icon.getImage();
            // resize the image.
            Image newImage = image.getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH);
            // reset trimmed image.
            ImageIcon newIcon = new ImageIcon(newImage);
            JLabel imageLabel = new JLabel(newIcon);
            buttonPanel3.add(imageLabel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        buttonPanel3.add(Box.createHorizontalStrut(200));
        buttonPanel3.add(loadButton);

        buttonPanel3.add(Box.createHorizontalStrut(200));
        buttonPanel3.add(saveButton);

        add(buttonPanel3);
        buttonPanel3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // This listener is shared by the text field and the remove book button.
    class RemoveBookListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = true;
        private JButton button;

        // MODIFIES: this
        // EFFECTS: pass in button.
        public RemoveBookListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: numOfRemovedStock, bookSystem, listModel
        // EFFECTS: If the entered number <= 0 or entered number > oldStock,
        //          do not change anything.
        //          If entered number > 0 and entered number < old stock,
        //          selected book remove entered number stock in book system,
        //          reset selected book stock in print list.
        //          If enter number == old stock,
        //          remove book in book system,
        //          remove book from print list.
        //          Nobody is left in the print list and book system, remove button is not enabled.
        //          Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            // get user input.
            String removedStock = numOfRemovedStock.getText();
            // convert string to int.
            int convertNum = Integer.parseInt(removedStock);

            // save the index of selected item.
            int index = list.getSelectedIndex();
            // save old stock of selected item.
            int oldStock = bookSystem.getSystemList().get(index).getStock();

            if (convertNum <= 0 || convertNum > oldStock) {
                numOfRemovedStock.requestFocusInWindow();
                numOfRemovedStock.selectAll();
                return;
            } else if (convertNum < oldStock) {
                listModel.setElementAt(" -> " + bookSystem.getSystemList().get(index).getName()
                        + "  $" + bookSystem.getSystemList().get(index).getPrice()
                        + "  current stock: " + (oldStock - convertNum), index);
                bookSystem.removeFromSystem(index, convertNum);
            } else {
                listModel.remove(index);
                bookSystem.removeFromSystem(index, convertNum);
            }

            disableButtonAndResetTextField(index);
        }

        // MODIFIES: removeBookButton, numOfRemovedStock, list
        // EFFECTS: Nobody is left in the print list and book system, remove button is not enabled.
        //          Reset the text field.
        public void disableButtonAndResetTextField(int index) {
            // get list size.
            int size = listModel.getSize();
            //Nobody's left, disable Remove Book.
            if (size == 0) {
                removeBookButton.setEnabled(false);
            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                //Reset the text field.
                numOfRemovedStock.requestFocusInWindow();
                numOfRemovedStock.setText("");

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: button
        // EFFECTS: check the text field is empty or not.
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: list, removeBookButton
    // EFFECTS: change button state.
    //          This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove book button.
                removeBookButton.setEnabled(false);

            } else {
                //Selection, enable the remove book button.
                removeBookButton.setEnabled(true);
            }
        }
    }

    // This listener is processing load event.
    class LoadListener implements ActionListener {
        // MODIFIES: bookSystem, list
        // EFFECTS: load book system from file and print list in the panel.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // first, load book system from file.
                BookSystem bookSystemExistedInFile = jsonReader.read();
                // append existed book system list which existed in file on the book system list exist in app.
                bookSystem.getSystemList().addAll(bookSystemExistedInFile.getSystemList());

                // then print list in the GUI scrollPanel.
                printInPanel();

                // after loading, assume an item is selected.
                list.setSelectedIndex(bookSystemExistedInFile.getSystemList().size() - 1);

            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // EFFECTS: add books from book system to print list.
    public void printInPanel() {
        // clear print list before print.
        listModel.clear();
        // print all items from book system list.
        for (int i = 0; i < bookSystem.getSystemList().size(); i++) {
            listModel.addElement(" -> " + bookSystem.getSystemList().get(i).getName()
                    + "  $" + bookSystem.getSystemList().get(i).getPrice()
                    + "  current stock: " + bookSystem.getSystemList().get(i).getStock());
        }
    }

    // This listener is processing save event.
    class SaveListener implements ActionListener {
        // MODIFIES: jsonWriter, listModel
        // EFFECTS: Save the book system to file.
        //          clean the print list for next loading.
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(bookSystem);
                jsonWriter.close();
                listModel.clear();
                // clear book system list when save to file.
                bookSystem = new BookSystem();

            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //This listener is shared by the text field and the add book button.
    class AddBookListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // MODIFIES: this
        // EFFECTS: pass button in.
        public AddBookListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: listModel, bookSystem
        // EFFECTS: If the new book exist in the book system(same book name is same book),
        //           update the item in the print list,
        //           add new book to book system.
        //          If the new book exist in the book system,
        //           add new book in print list,
        //           add the new book in the book system.
        //          Reset test field.
        public void actionPerformed(ActionEvent e) {
            // get new name, new price, new stock.
            String newName = bookName.getText();
            String newPrice = bookPrice.getText();
            String newStock = bookStock.getText();
            // save the index of selected book.
            int index = 0;
            // convert stock from string to int.
            int convertedStock = Integer.parseInt(newStock);

            // build a new book.
            Book newBook = new Book(newName, Double.parseDouble(newPrice), convertedStock);
            if (bookSystem.isBookExistInSystem(newBook)) {
                // reassign selected book position.
                index = bookSystem.searchBook(newBook);
                bookSystem.addNewBook(newBook);
                listModel.setElementAt(" -> " + bookSystem.getSystemList().get(index).getName()
                        + "  $" + bookSystem.getSystemList().get(index).getPrice()
                        + "  current stock: " + bookSystem.getSystemList().get(index).getStock(), index);
            } else if (!bookSystem.isBookExistInSystem(newBook)) {
                listModel.addElement(" -> " + newName + " $" + newPrice + " current stock: " + newStock);
                bookSystem.addNewBook(newBook);
                // reassign selected book position.
                index = bookSystem.searchBook(newBook);
            }

            // reset text field.
            resetTextField(index);
        }

        // MODIFIES: bookName, bookPrice, bookStock, list
        // EFFECTS: reset text field.
        public void resetTextField(int index) {

            //Reset the text field.
            bookName.requestFocusInWindow();
            bookName.setText("");

            //Reset the text field.
            bookPrice.requestFocusInWindow();
            bookPrice.setText("");

            //Reset the text field.
            bookStock.requestFocusInWindow();
            bookStock.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        //          Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        //          Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        //          Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: button
        // EFFECTS: update button state.
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: button
        // EFFECTS: check the text field is empty or not.
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


    /**
     * MODIFIES: frame, newContentPane
     * EFFECTS:
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * print all log events to the console when the user quits this app.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("BookManagementGUI");
        // just close this frame.
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setLocation(200, 200);

        //Create and set up the content pane.
        JComponent newContentPane = new ui.BookManagementGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        // I wrote this method after looking at the following website.
        // https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            // EFFECTS: print all events that have been logged since the app started
            //          when the frame is closed.
            public void windowClosing(java.awt.event.WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
                // close window.
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
