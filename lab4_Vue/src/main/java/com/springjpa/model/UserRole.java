package com.springjpa.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role", schema = "s225036", catalog = "studs")
//@Table(name = "user_roles",schema = "public")
public class UserRole implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Integer id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "role")
    private String role;

    public UserRole(){}

    public UserRole(String username, String role){
        this.username=username;
        this.role=role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
