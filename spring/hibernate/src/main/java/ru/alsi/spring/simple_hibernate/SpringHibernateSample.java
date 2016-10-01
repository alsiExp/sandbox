package ru.alsi.spring.simple_hibernate;

import java.util.List;
import java.util.Date;
import java.util.Set;

import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringHibernateSample {
    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("spring/app-context-xml.xml");


        ContactDao contactDao = ctx.getBean("contactDao", ContactDao.class);

        Contact c = contactDao.findById(100000L);

        contactDao.delete(c);

        Contact contact = new Contact () ;
        contact.setFirstName("Michael");
        contact.setLastName("Jackson");
        contact.setBirthDate(new Date());
        ContactTelDetail contactTelDetail =
                new ContactTelDetail("Home", "1111111111");
        contact.addContactTelDetail(contactTelDetail);
        contactTelDetail = new ContactTelDetail("Mobile", "2222222222");
        contact.addContactTelDetail(contactTelDetail);
        contactDao.save(contact);

        listContactsWithDetail(contactDao.findAllWithDetail());
    }

    private static void listContactsWithDetail(List<Contact> contacts) {
        System.out.println("");
        System.out.println("Listing contacts with details:");

        for (Contact contact: contacts) {
            System.out.println(contact);

            if (contact.getContactTelDetails() != null) {
                for (ContactTelDetail contactTelDetail: 
                         contact.getContactTelDetails()) {
                    System.out.println(contactTelDetail);
                }
            }

            if (contact.getHobbies() != null) {
                for (Hobby hobby: contact.getHobbies()) {
                    System.out.println(hobby);
                }
            }

            System.out.println();
        }
    }
}
