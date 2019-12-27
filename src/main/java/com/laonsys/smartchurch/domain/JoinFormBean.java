package com.laonsys.smartchurch.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.laonsys.springmvc.extensions.validation.constraints.Contact;
import com.laonsys.springmvc.extensions.validation.constraints.Password;

public class JoinFormBean {

    @Email(groups = {Agree.class, Verify.class, Join.class, FindPassword.class})
    private String email;

    @NotEmpty(groups = {Verify.class})
    private String code;

    @NotEmpty(groups = {Join.class, FindPassword.class})
    @Size(max=20, groups = {Join.class, FindPassword.class})
    private String name;

    @Contact(groups = {Join.class})
    private String contact;

    @Password(groups = {Join.class})
    private String password;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "email [" + email + "], name [" + name + "], contact [" + contact + "], password [" + password + "]";
    }
    
    public interface Agree {};
    public interface Verify {};
    public interface Join {};
    public interface FindPassword {};
}
