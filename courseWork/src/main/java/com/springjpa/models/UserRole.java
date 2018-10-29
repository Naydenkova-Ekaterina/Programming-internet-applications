package com.springjpa.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_roles", schema = "s225036", catalog = "studs")
@ToString(exclude={"id"})
@EqualsAndHashCode(exclude={"user"})
@NoArgsConstructor
@Getter
@Setter
public class UserRole {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public UserRole(String username,String role){
        this.username=username;
        this.role=role;
    }

}
