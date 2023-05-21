package com.cda.freely.dto.user;

import com.cda.freely.dto.company.CompanyDTO;
import com.cda.freely.entity.User;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;


public class UserDTO {
    private String firstName;

    private String lastName;

    private String email;

    private String Password;

    private String profilePic;

    private User.Role role;

    private User.Gender gender;

    private User.Status userAccountState;

    private User.Availability userAvailability;

    private Long familyId;

    private List<CompanyDTO> companies;

    private List<Long> tagIds;

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public User.Gender getGender() {
        return gender;
    }

    public void setGender(User.Gender gender) {
        this.gender = gender;
    }

    public User.Status getUserAccountState() {
        return userAccountState;
    }

    public void setUserAccountState(User.Status userAccountState) {
        this.userAccountState = userAccountState;
    }

    public User.Availability getUserAvailability() {
        return userAvailability;
    }

    public void setUserAvailability(User.Availability userAvailability) {
        this.userAvailability = userAvailability;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public List<CompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDTO> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", Password='" + Password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", role=" + role +
                ", gender=" + gender +
                ", userAccountState=" + userAccountState +
                ", userAvailability=" + userAvailability +
                ", familyId=" + familyId +
                ", companies=" + companies +
                ", tagIds=" + tagIds +
                '}';
    }
}
