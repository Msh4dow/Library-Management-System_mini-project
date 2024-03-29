package com.pinaclelabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {

    private static List<Book> books = new ArrayList<>(); // List to store books
    private static List<Loan> loans = new ArrayList<>(); // List to store loans (borrowed books)

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Books");
            System.out.println("5. View Loans");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.addBook(); // Call instance method on the instance
                    break;
                case 2:
                    library.borrowBook(); // Call instance method on the instance
                    break;
                case 3:
                    library.returnBook(); // Call instance method on the instance
                    break;
                case 4:
                    library.viewBooks(); // Call instance method on the instance
                    break;
                case 5:
                    library.viewLoans(); // Call instance method on the instance
                    break;
                case 6:
                    System.out.println("Exiting Library Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        System.out.print("Enter ISBN number: ");
        String isbn = scanner.nextLine();

        Book newBook = new Book(title, author, isbn);
        books.add(newBook);
        System.out.println("Book added successfully!");
    }

    private void borrowBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();

        Book bookToBorrow = findBookByIsbn(isbn);

        if (bookToBorrow != null) {
            if (bookToBorrow.isAvailable()) {
                System.out.print("Enter borrower name: ");
                String borrowerName = scanner.nextLine();

                Loan newLoan = new Loan(bookToBorrow, borrowerName);
                loans.add(newLoan);
                bookToBorrow.setAvailable(false); // Mark book as borrowed

                System.out.println("Book borrowed successfully by " + borrowerName + ".");
            } else {
                System.out.println("The book is currently unavailable.");
            }
        } else {
            System.out.println("Book with ISBN " + isbn + " not found.");
        }
    }

    // Loan.java
    public class Loan {
        private Book borrowedBook;
        private String borrowerName;

        // Constructor
        public Loan(Book borrowedBook, String borrowerName) {
            this.borrowedBook = borrowedBook;
            this.borrowerName = borrowerName;
        }

        // Getters
        public Book getBorrowedBook() {
            return borrowedBook;
        }

        public String getBorrowerName() {
            return borrowerName;
        }
    }

    private static void returnBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();

        Book bookToReturn = findBookByIsbn(isbn);

        if (bookToReturn != null) {
            Loan loanToReturn = findLoanByBook(bookToReturn);

            if (loanToReturn != null) {
                loans.remove(loanToReturn); // Remove loan from list
                bookToReturn.setAvailable(true); // Mark book as available

                System.out.println("Book returned successfully.");
            } else {
                System.out.println("This book is not currently borrowed.");
            }
        } else {
            System.out.println("Book with ISBN " + isbn + " not found.");
        }
    }

    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("There are no books in the library.");
        } else {
            System.out.println("** Books in Library **");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
    // Book.java
    public class Book {
        private String title;
        private String author;
        private String isbn;
        private boolean available;

        // Constructor
        public Book(String title, String author, String isbn) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.available = true;
        }

        // Getters and setters
        public String getIsbn() {
            return isbn;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        // toString method to display book details
        @Override
        public String toString() {
            return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + (available ? "Yes" : "No");
        }
    }



    private static void viewLoans() {
        if (loans.isEmpty()) {
            System.out.println("There are no loans currently.");
        } else {
            System.out.println("** Current Loans **");
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }
    }

    private static Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private static Loan findLoanByBook(Book book) {
        for (Loan loan : loans) {
            if (loan.getBorrowedBook().equals(book)) {
                return loan;
            }
        }
        return null;
    }

}
