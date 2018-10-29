package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gladiatorial_school", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","gladiatorEntities","masterSet"})
@EqualsAndHashCode(exclude={"gladiatorEntities","city","masterSet"})
@NoArgsConstructor
@Getter @Setter
public class GladiatorialSchool {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_gladiatorialschool")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "schoolimage")
    private String schoolimage;

    @Column(name = "contribution")
    private Integer contribution;


    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_city")
    private City city;

    @JsonIgnore
    @OneToMany(mappedBy = "gladiatorialSchool", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Gladiator> gladiatorEntities;

    @JsonIgnore
    @ManyToMany(mappedBy = "gladiatorialSchoolSet")
    private Set<Master> masterSet;


    public GladiatorialSchool(String name, City city,int contribution){
        this.name=name;
        this.city = city;
        this.contribution=contribution;
    }

    public GladiatorialSchool(String name, City city,int contribution,String schoolimage){
        this.name=name;
        this.city = city;
        this.contribution=contribution;
        this.schoolimage=schoolimage;
    }

}
