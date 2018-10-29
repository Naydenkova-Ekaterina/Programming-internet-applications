package com.springjpa.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "City", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","arenaEntities","masterEntities","gladiatorialSchoolEntities"})
@EqualsAndHashCode(exclude={"arenaEntities","masterEntities","gladiatorialSchoolEntities"})
@NoArgsConstructor
@Getter @Setter
public class City implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_city")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_residents")
    private Integer numberOfResidents;

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Arena> arenaEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Master> masterEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private  Set<GladiatorialSchool> gladiatorialSchoolEntities;


    public City(String name, int numberOfResidents){
        this.name=name;
        this.numberOfResidents=numberOfResidents;
    }

}
