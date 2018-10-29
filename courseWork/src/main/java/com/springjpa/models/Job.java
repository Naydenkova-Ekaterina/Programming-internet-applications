package com.springjpa.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "job", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","gladiatorEntities"})
@EqualsAndHashCode(exclude="gladiatorEntities")
@NoArgsConstructor
@Getter @Setter
public class Job {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_job")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "reward")
    private Integer reward;

    @JsonIgnore
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Gladiator> gladiatorEntities;

    public Job(String name, int reward){
        this.name=name;
        this.reward=reward;
    }

}
