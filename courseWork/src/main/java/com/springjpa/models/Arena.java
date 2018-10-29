package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "arena", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","fightEntities"})
@EqualsAndHashCode(exclude={"fightEntities","city"})
@NoArgsConstructor
@Getter @Setter
public class Arena {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_arena")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "arenaimage")
    private String arenaimage;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @Column(name = "number_of_rows")
    private Integer numberOfRows;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @JsonIgnore
    @OneToMany(mappedBy = "arena", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Fight> fightEntities;

    @JsonIgnore
    private byte[] picture;

    public Arena(String name, String address, Integer numberOfRows, Integer numberOfSeats, City city){
        this.name=name;
        this.address=address;
        this.city = city;
        this.numberOfRows=numberOfRows;
        this.numberOfSeats=numberOfSeats;
    }

    public Arena(String name, String address, Integer numberOfRows, Integer numberOfSeats, City city,String arenaimage){
        this.name=name;
        this.address=address;
        this.city = city;
        this.numberOfRows=numberOfRows;
        this.numberOfSeats=numberOfSeats;
        this.arenaimage=arenaimage;
    }

}
