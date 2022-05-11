package org.example.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Contact {
    private UUID id;
    private byte[] avatar;
    private String avatarEncoded;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Gender gender;
    private List<SocialNetwork> socialNetworks;
    private MaritalStatus maritalStatus;

    public Contact(UUID id, byte[] avatar, String firstName, String lastName, String phone, String email, Gender gender, List<SocialNetwork> socialNetworks, MaritalStatus maritalStatus) {
        this.id = id;
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.socialNetworks = socialNetworks;
        this.maritalStatus = maritalStatus;
    }

    public Contact(int i) {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarEncoded() {
        return avatarEncoded;
    }

    public void setAvatarEncoded(String avatarEncoded) {
        this.avatarEncoded = avatarEncoded;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(List<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) && Arrays.equals(avatar, contact.avatar) && Objects.equals(firstName, contact.firstName) && Objects.equals(lastName, contact.lastName) && Objects.equals(phone, contact.phone) && Objects.equals(email, contact.email) && gender == contact.gender && Objects.equals(socialNetworks, contact.socialNetworks) && maritalStatus == contact.maritalStatus;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, firstName, lastName, phone, email, gender, socialNetworks, maritalStatus);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", avatar=" + Arrays.toString(avatar) +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", socialNetworks=" + socialNetworks +
                ", maritalStatus=" + maritalStatus +
                '}';
    }
}