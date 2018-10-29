package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "weapon", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","animalEntities","gladiatorEntities"})
@EqualsAndHashCode(exclude={"animalEntities","gladiatorEntities"})
@NoArgsConstructor
@Getter @Setter
public class Weapon {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_weapon")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "weaponimage")
    private String weaponimage;

    @Column(name = "damage")
    private Integer damage;

    @Column(name = "cost")
    private Integer cost;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "animals_weapon", joinColumns = @JoinColumn(name = "id_weapon", referencedColumnName = "id_weapon"), inverseJoinColumns = @JoinColumn(name = "id_animals", referencedColumnName = "id_animals"))
    private Set<Animal> animalEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "weapon", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Gladiator> gladiatorEntities;

    @JsonIgnore
    private byte[] picture;


    public Weapon(String name, Integer damage,Integer cost){
        this.name=name;
        this.damage=damage;
        this.cost=cost;

    }

    public Weapon(String name, Integer damage,Integer cost,String weaponimage){
        this.name=name;
        this.damage=damage;
        this.cost=cost;
this.weaponimage=weaponimage;
    }

}
