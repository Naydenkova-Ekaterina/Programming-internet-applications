package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gladiator", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","fightEntities","user"})
@EqualsAndHashCode(exclude={"fightEntities","user"})
@NoArgsConstructor
@Getter @Setter
public class Gladiator {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_gladiator")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "gladiatorimage")
    private String gladiatorimage;

    @Column(name = "age")
    private Integer age;

    @Column(name = "wins")
    private Integer wins;


    @Column(name = "cost")
    private Integer cost;

    @Column(name = "isforsale")
    private boolean isForSale;

    @Column(name = "jabberaddress")
    private String jabberaddress;

    @ManyToOne
    @JoinColumn(name = "id_gladiatorialschool")
    private GladiatorialSchool gladiatorialSchool;

    @ManyToOne
    @JoinColumn(name = "id_weapon")
    private Weapon weapon;

    @ManyToOne
    @JoinColumn(name = "id_armor")
    private Armor armor;

    @ManyToOne
    @JoinColumn(name = "id_master")
    private Master master;

    @JsonIgnore
    //@ManyToMany(mappedBy = "gladiatorEntities")
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "gladiator_fight", joinColumns = @JoinColumn(name = "id_gladiator", referencedColumnName = "id_gladiator"), inverseJoinColumns = @JoinColumn(name = "id_fight", referencedColumnName = "id_fight"))
    private Set<Fight> fightEntities;

    @ManyToOne
    @JoinColumn(name = "id_job")
    private Job job;

    @JsonIgnore
    private byte[] picture;

    @OneToOne(mappedBy = "gladiator")
    private User user;

    public Gladiator(String name, int age,Integer cost,String jabberaddress, GladiatorialSchool gladiatorialSchool, Weapon weapon, Armor armor, Master master, Job job){
        this.name=name;
        this.age=age;
        this.cost=cost;
        this.jabberaddress=jabberaddress;
        this.gladiatorialSchool = gladiatorialSchool;
        this.weapon = weapon;
        this.armor = armor;
        this.master = master;
        this.job = job;
        this.gladiatorimage="1.png";

    }

    public Gladiator(String name, int age,Integer cost,String jabberaddress, GladiatorialSchool gladiatorialSchool, Weapon weapon, Armor armor, Master master, Job job,String gladiatorimage){
        this.name=name;
        this.age=age;
        this.cost=cost;
        this.jabberaddress=jabberaddress;
        this.gladiatorialSchool = gladiatorialSchool;
        this.weapon = weapon;
        this.armor = armor;
        this.master = master;
        this.job = job;
        this.gladiatorimage=gladiatorimage;
    }

    public Gladiator(String name, int age,String jabberaddress){
        this.name=name;
        this.age=age;
        this.jabberaddress=jabberaddress;
        this.gladiatorimage="1.png";

    }

    public Gladiator(String name, int age,String jabberaddress,String gladiatorimage){
        this.name=name;
        this.age=age;
        this.jabberaddress=jabberaddress;
        this.gladiatorimage=gladiatorimage;

    }

}
