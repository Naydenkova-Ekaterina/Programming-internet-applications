package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "fight", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","animalEntities","gladiatorEntities","ticketsEntities"})
@EqualsAndHashCode(exclude={"animalEntities","gladiatorEntities","ticketsEntities"})
@NoArgsConstructor
@Getter @Setter
public class Fight {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_fight")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_arena")
    private Arena arena;

    @Column(name = "prize")
    private Integer prize;

    @Column(name = "winner")
    private String winner;


   // @ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(name = "Animals_Fight", joinColumns = @JoinColumn(name = "id_fight", referencedColumnName = "id_fight"), inverseJoinColumns = @JoinColumn(name = "id_animals", referencedColumnName = "id_animals"))
    @ManyToMany(mappedBy = "fightEntities")
    private Set<Animal> animalEntities;



    //@ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(name = "Gladiator_Fight", joinColumns = @JoinColumn(name = "id_fight", referencedColumnName = "id_fight"), inverseJoinColumns = @JoinColumn(name = "id_gladiator", referencedColumnName = "id_gladiator"))
    @ManyToMany(mappedBy = "fightEntities")
    private Set<Gladiator> gladiatorEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "fight", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Ticket> ticketsEntities;

    public Fight(int prize,Arena arena, String winner,Set<Animal>animalEntities,Set<Gladiator>gladiatorEntities){
        this.arena = arena;
        this.prize=prize;
        this.winner=winner;
        this.animalEntities=animalEntities;
        this.gladiatorEntities=gladiatorEntities;
    }

}
