# Books Management System

### What will the application do? & Who will use it?

A bookstore management system is developed to organize books on the computer <br>
in a library or bookstore. It provides multiple functions for a manager to arrange books.<br>
The main ability of this application : <br>
***Organize the Books***: this function is allowed manager to add and remove books, <br>
to modify the books' detailed information.

### Why is this project of interest to you?

The idea of designing this Bookstore management system is based on libraries <br>
or bookstores in our real life, build distinct book instances in the computer by <br>
using the unique reference of books. The implementation of this program can <br>
significantly reduce the time cost.

## User Stories

A *user stories* list:

- As a user, I want to add books to the system.
- As a user, I want to remove books from the system.
- As a user, I want to see a list of books in the system.
- As a user, I want to modify the information of books.
- As a user, I want to be able to save the book list to file.
- As a user, I want to be able to load the book list from file.

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y <br>
  by filling New book name, New book price, New book stock and then click the button labelled "Add Book".
- You can generate the second required event related to Xs and Y <br>
  by clicking the book you want to remove, enter a valid number to remove and then click the button labelled "Remove<br>
  Book".
- You can locate my visual component by looking at the left bottom corner of frame. <br>
  The image "bookImage.png" is in data file.
- You can save the state of my application by clicking the button labelled "Save To File".
- You can reload the state of my application by clicking the button labelled "Load From File".

# Phase 4: Task 2

Tue Nov 22 02:51:07 PST 2022<br>
Added new books to system.<br>
Tue Nov 22 02:51:15 PST 2022<br>
Added stock to existed book.<br>
Tue Nov 22 02:51:19 PST 2022<br>
Load from system.<br>
Tue Nov 22 02:51:26 PST 2022<br>
Added new books to system.<br>
Tue Nov 22 02:51:49 PST 2022<br>
Removed some selected books from system.<br>
Tue Nov 22 02:51:55 PST 2022<br>
Removed all selected books from system.<br>
Tue Nov 22 02:51:58 PST 2022<br>
Save to system.<br>
<br>
Process finished with exit code 0<br>

# Phase 4: Task 3

- In order to reduce duplicate code, extracting print functionality out from "GUI" class and "App" class, make a new<br>
  class and print method. So printing format is always consistent, improve the semantic coupling in application. <br>
  For example: <br>
  ```
  public class ListPrinter() {
      private BookSystem bookSystem;
      public ListPrinter(BookSystem bs) {
          this.bookSystem = bs;
      }
      public String printInFormat(int index) {
          return (" -> " + bookSystem.getSystemList().get(index).getName()
                        + "  $" + bookSystem.getSystemList().get(index).getPrice()
                        + "  current stock: " + bookSystem.getSystemList().get(index).getStock(), index);
      }
  }
  ```
- In BookManagementGUI class, there are a lot of duplicate statements about requestFocusInWindow() and setText().<br>
  These statements can be extracted as a helper method inside this class. The new method may accept JTextField field
  to<br>
  reset the text field, in the meanwhile reduce the duplicate code. For example: <br>
  ```
  public void resetField(JTextField o) {
      o.requestFocusInWindow();
      o.setText("");
  }
  ```
- BookManagementGUI class are including multiple functionalities, splits four distinct Listener classes into four
  new <br>
  classes in order to improve cohesion. For example: <br>
  ```
  class AddBookListener implements ActionListener, DocumentListener {}
  class LoadListener implements ActionListener {}
  class SaveListener implements ActionListener {}
  class RemoveBookListener implements ActionListener, DocumentListener {}
  ```
- Abstracting Statement:EventLog.getInstance().logEvent(new Event("Save to system.")); into a new abstract class. <br>
  Creating an individual method in order to avoid duplicate code and improve coupling.  <br>
  ```
  public abstract class LogEventable {
      public void logEvent(String message) {
          EventLog.getInstance().logEvent(new Event(message));
      }
  }
  ```
  BookSystem class, JsonReader class and JsonWriter class extends this LogEventable abstract class.
- Composite pattern could be implemented in the application. Assuming the app rebuilds the hierarchy, Book class is 
  ***Leaf***,<br>
  BookSystem class is ***Composite*** which represents the category of books, and new ***Component*** which is<br>
  superclass extended by both the Composite and Leaf. <br>
  The application can classify the books. In the meantime the implementation of each class is needed to change.<br>
  For example:<br>
  Math(BookSystem)<br>
      textbook(Book)<br>
      linear Algebra(BookSystem)<br>
      calculus(BookSystem)<br>
          calculus1(Book)<br>
          calculus2(Book)<br>
  note: actual type is shown in the bracket.
