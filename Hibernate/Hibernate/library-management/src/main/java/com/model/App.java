package com.model;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {

            System.out.println("\n====== Library Management ======");
            System.out.println("1. Add Student");
            System.out.println("2. Add Book");
            System.out.println("3. Issue Book");
            System.out.println("4. View Students");
            System.out.println("5. View Books");
            System.out.println("6. Delete Book");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            switch(choice) {

                case 1 -> {
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Student Age: ");
                    int age = sc.nextInt();

                    Student s = new Student(name, age);
                    session.save(s);

                    System.out.println("Student Added!");
                }


                case 2 -> {
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    Book b = new Book(title, author);
                    session.save(b);

                    System.out.println("Book Added!");
                }


                case 3 -> {
                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();

                    System.out.print("Enter Book ID: ");
                    int bid = sc.nextInt();

                    Issue issue = new Issue(sid, bid, new Date());
                    session.save(issue);

                    System.out.println("Book Issued!");
                }


                case 4 -> {
                    List<Student> students =
                            session.createQuery("from Student", Student.class).list();

                    System.out.println("\nStudents List:");
                    for(Student st : students) {
                        System.out.println(st.getId() + " " + st.getName() + " " + st.getAge());
                    }
                }


                case 5 -> {
                    List<Book> books =
                            session.createQuery("from Book", Book.class).list();

                    System.out.println("\nBook List:");
                    for(Book bk : books) {
                        System.out.println(bk.getId() + " " + bk.getTitle() + " " + bk.getAuthor());
                    }
                }


                case 6 -> {
                    System.out.print("Enter Book ID to Delete: ");
                    int id = sc.nextInt();

                    Book book = session.get(Book.class, id);

                    if(book != null) {
                        session.delete(book);
                        System.out.println("Book Deleted!");
                    } else {
                        System.out.println("Book Not Found!");
                    }
                }


                case 7 -> System.out.println("Exiting...");


                default -> System.out.println("Invalid Choice");

            }

            tx.commit();
            session.close();

        } while(choice != 7);

        sc.close();
    }
}