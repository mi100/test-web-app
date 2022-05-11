package org.example.dao;


import org.example.model.Contact;
import org.example.model.Gender;
import org.example.model.MaritalStatus;
import org.example.model.SocialNetwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

public class ContactDao {

    private Connection conn;

    public ContactDao() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String url = "jdbc:mysql://localhost:3306/contacts";
        String userName = "root";
        String pass = "QWEqwe123";

        Class.forName("com.mysql.jdbc.Driver").newInstance();

        conn = DriverManager.getConnection(
                url,
                userName, pass);

        conn.setAutoCommit(false);

        if (conn == null) {
            System.out.println("Немає з'єднання з БД!");
            System.exit(0);
        }
    }

    public Contact createContact(Contact contact) {
        UUID uuid = UUID.randomUUID();
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into contacts (id, avatar, firstName, lastName, phone, email, " +
                            "gender, socialNetworks, maritalStatus) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            Blob blob = conn.createBlob();
            blob.setBytes(1, contact.getAvatar());

            stmt.setString(1, uuid.toString());
            stmt.setBlob(2, blob);
            stmt.setString(3, contact.getFirstName());
            stmt.setString(4, contact.getLastName());
            stmt.setString(5, contact.getPhone());
            stmt.setString(6, contact.getEmail());
            stmt.setString(7, contact.getGender().name());

            String combinedSocialMediaNetworksValue = contact.getSocialNetworks()
                    .stream()
                    .map(SocialNetwork::name)
                    .collect(joining(","));

            stmt.setString(8, combinedSocialMediaNetworksValue);
            stmt.setString(9, contact.getMaritalStatus().name());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating contact blead failed, no rows affected.");
            }

            contact.setId(uuid);

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Can't insert contact into DB, because " + e.getMessage());
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return contact;
    }

    public Contact getContact(UUID id) {

        try {
            PreparedStatement stmt = conn.prepareStatement("select * from contacts where id = ?");
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();

            Contact contact = null;

            while (rs.next()) {
                Blob avatarBlob = rs.getBlob(2);
                byte[] avatar = null;
                if (avatarBlob != null) {
                    long avatarSize = avatarBlob.length();
                    avatar = avatarBlob.getBytes(1, (int) avatarSize);
                }
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String phone = rs.getString(5);
                String email = rs.getString(6);

                Gender gender = Gender.fromName(rs.getString(7));

                String combinedSocialMediaNetworksValue = rs.getString(8);
                String[] socialNetworkValues = combinedSocialMediaNetworksValue.split(",");
                List<SocialNetwork> socialNetworks = new ArrayList<>();
                for(String socialNetworkValue : socialNetworkValues){
                    SocialNetwork socialNetwork = SocialNetwork.fromName(socialNetworkValue);
                    socialNetworks.add(socialNetwork);
                }

                MaritalStatus maritalStatus = MaritalStatus.fromName(rs.getString(9));

                if (contact == null) {
                    contact = new Contact(id, avatar, firstName, lastName, phone, email, gender, socialNetworks, maritalStatus);
                    if(avatar != null){
                        String avatarEncoded = Base64.getEncoder().encodeToString(avatar);
                        contact.setAvatarEncoded(avatarEncoded);
                    }
                }
            }
            return contact;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Contact> getContacts() {
        try {
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement("select * from contacts");
            ResultSet rs = stmt.executeQuery();
            conn.setAutoCommit(false);

            List<Contact> contacts = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString(1);
                Blob avatarBlob = rs.getBlob(2);
                byte[] avatar = null;
                if (avatarBlob != null) {
                    long avatarSize = avatarBlob.length();
                    avatar = avatarBlob.getBytes(1, (int) avatarSize);
                }
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String phone = rs.getString(5);
                String email = rs.getString(6);
                Gender gender = Gender.fromName(rs.getString(7));

                String combinedSocialMediaNetworksValue = rs.getString(8);
                String[] socialNetworkValues = combinedSocialMediaNetworksValue.split(",");
                List<SocialNetwork> socialNetworks = new ArrayList<>();
                for(String socialNetworkValue : socialNetworkValues){
                    SocialNetwork socialNetwork = SocialNetwork.fromName(socialNetworkValue);
                    socialNetworks.add(socialNetwork);
                }

                MaritalStatus maritalStatus = MaritalStatus.fromName(rs.getString(9));

                UUID uuid = UUID.fromString(id);

                Contact contact = new Contact(uuid, avatar, firstName, lastName, phone, email, gender, socialNetworks, maritalStatus);
                if(avatar != null){
                    String avatarEncoded = Base64.getEncoder().encodeToString(avatar);
                    contact.setAvatarEncoded(avatarEncoded);
                }

                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Contact updateContact(Contact contact) {
        try {
            PreparedStatement contactStmt = conn.prepareStatement("update contacts set avatar = ?, firstName = ?, " +
                    "lastName = ?, phone = ?, email = ?, gender = ?, socialNetworks = ?, maritalStatus = ?  where id = ?");

            Blob blob = conn.createBlob();
            blob.setBytes(1,contact.getAvatar());

            contactStmt.setBlob(1, blob);
            contactStmt.setString(2, contact.getFirstName());
            contactStmt.setString(3, contact.getLastName());
            contactStmt.setString(4, contact.getPhone());
            contactStmt.setString(5, contact.getEmail());
            contactStmt.setString(6, contact.getGender().name());

            String combinedSocialMediaNetworksValue = contact.getSocialNetworks()
                    .stream()
                    .map(SocialNetwork::name)
                    .collect(joining(","));

            contactStmt.setString(7, combinedSocialMediaNetworksValue);
            contactStmt.setString(8, contact.getMaritalStatus().name());
            contactStmt.setString(9, contact.getId().toString());

            contactStmt.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return contact;
    }

    public Contact deleteContact(UUID id) {
        try {
            if (getContact(id) != null) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM contacts WHERE id = ?");
                stmt.setString(1, id.toString());
                stmt.execute();

                conn.commit();
            } else {
                throw new IllegalStateException("Contact[id=" + id + "] does not exit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
