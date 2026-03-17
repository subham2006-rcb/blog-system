package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class App {

    public static void main(String[] args) {

        // Create SessionFactory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = factory.openSession();

        // Start transaction
        session.beginTransaction();

        // Insert sample students
        Student s1 = new Student("Rahul",21);
        Student s2 = new Student("Amit",22);
        Student s3 = new Student("Priya",20);

        session.save(s1);
        session.save(s2);
        session.save(s3);

        session.getTransaction().commit();

        // -------------------------------
        // HQL Query: Fetch All Students
        // -------------------------------

        session = factory.openSession();
        session.beginTransaction();

        Query<Student> query = session.createQuery("FROM Student", Student.class);

        List<Student> students = query.getResultList();

        System.out.println("All Students:");

        for(Student s : students) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getAge());
        }

        session.getTransaction().commit();

        // --------------------------------
        // HQL Query with Condition
        // --------------------------------

        session = factory.openSession();
        session.beginTransaction();

        Query<Student> query2 =
                session.createQuery("FROM Student WHERE age > 20", Student.class);

        List<Student> result = query2.getResultList();

        System.out.println("\nStudents with age > 20:");

        for(Student s : result) {
            System.out.println(s.getName() + " " + s.getAge());
        }

        session.getTransaction().commit();

        session.close();
        factory.close();
    }
}