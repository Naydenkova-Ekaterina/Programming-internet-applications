package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "animals", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","weaponEntities","fightEntities"})
@EqualsAndHashCode(exclude = {"weaponEntities"})
@NoArgsConstructor
@Getter @Setter
public class Animal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_animals")
    private Integer id;

    @Column(name = "name")
    private String name;


   @ManyToMany(mappedBy = "animalEntities",fetch=FetchType.EAGER)
    private Set<Weapon> weaponEntities;

    @JsonIgnore
    //@ManyToMany(mappedBy = "animalEntities")
    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "animals_fight", joinColumns = @JoinColumn(name = "id_animals", referencedColumnName = "id_animals"), inverseJoinColumns = @JoinColumn(name = "id_fight", referencedColumnName = "id_fight"))
    private Set<Fight> fightEntities;

    public Animal(String name,Set<Weapon> weaponEntities){
        this.name=name;
        this.weaponEntities=weaponEntities;
    }

}
