package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users", schema = "s225036", catalog = "studs")
@ToString(exclude={"id"})
@EqualsAndHashCode(exclude={"userRole","master","gladiator"})
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "enabled")
    private boolean enabled;

   // @OneToOne(cascade = CascadeType.ALL)
   // @JoinColumn(name = "user_role_id")
   @JsonIgnore
   @OneToOne(mappedBy = "user")
    private UserRole userRole;

    @Column(name = "money")
    private Integer money;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_master")
    private Master master;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gladiator")
    private Gladiator gladiator;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Ticket> ticketSet;

    public User(String username,String password,String email,boolean enabled){
        this.username=username;
        this.password=password;
        this.email=email;
        this.enabled=enabled;
    }
}
