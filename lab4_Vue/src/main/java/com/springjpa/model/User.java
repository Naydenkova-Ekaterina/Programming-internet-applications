package com.springjpa.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "myuser", schema = "s225036", catalog = "studs")
//@Table(name = "users",schema = "public")
public class User implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "enabled")
    private Boolean enabled;


    public User(){}

    public User(String username,String password,Boolean enabled){
        this.username=username;
        this.password=password;
        this.enabled=enabled;
    }


   @Override
    public int hashCode() {
        int result = 3;
        result = 31 * result + (username.hashCode() != +0.0f ? Float.floatToIntBits(username.hashCode()) : 0);
        result = 31 * result + (password.hashCode() != +0.0f ? Float.floatToIntBits(password.hashCode()) : 0);

        return result;
    }

    @Override
    public boolean equals(Object other) {

        if(!super.equals(other)) return false;
        if (this == other) return true;
        if (other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        User otherObj = (User) other;
        return this.username.equals( otherObj.username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

