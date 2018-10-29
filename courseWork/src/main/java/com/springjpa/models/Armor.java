package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "armor", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","gladiatorEntities"})
@EqualsAndHashCode(exclude={"gladiatorEntities"})
@NoArgsConstructor
@Getter @Setter
public class Armor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_armor")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "armorimage")
    private String armorimage;

    @Column(name = "shield")
    private Boolean shield;

    @Column(name = "defense")
    private Integer defense;

    @Column(name = "cost")
    private Integer cost;

    @JsonIgnore
    @OneToMany(mappedBy = "armor", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Gladiator> gladiatorEntities;

    @JsonIgnore
    private byte[] picture;

    public Armor(String name, boolean shield,Integer defense,Integer cost){
        this.name=name;
        this.shield=shield;
        this.defense=defense;
        this.cost=cost;
    }

    public Armor(String name, boolean shield,Integer defense,Integer cost,String armorimage){
        this.name=name;
        this.shield=shield;
        this.defense=defense;
        this.cost=cost;
        this.armorimage=armorimage;
    }

}
