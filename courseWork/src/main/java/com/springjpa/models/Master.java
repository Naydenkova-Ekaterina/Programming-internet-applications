package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "master", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","gladiatorEntities"})
@EqualsAndHashCode(exclude={"gladiatorEntities","user","gladiatorialSchoolSet"})
@NoArgsConstructor
@Getter @Setter
public class Master {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_master")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @JsonIgnore
    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Gladiator> gladiatorEntities;

    @OneToOne(mappedBy = "master")
    private User user;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "glschool_master", joinColumns = @JoinColumn(name = "id_master", referencedColumnName = "id_master"), inverseJoinColumns = @JoinColumn(name = "id_gladiatorialschool", referencedColumnName = "id_gladiatorialschool"))
    private Set<GladiatorialSchool> gladiatorialSchoolSet;

    public Master(String name, String title, City city){
        this.name=name;
        this.title=title;
        this.city = city;
    }

}
