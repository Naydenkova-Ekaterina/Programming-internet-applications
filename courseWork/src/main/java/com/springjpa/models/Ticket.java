package com.springjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tickets", schema = "s225036", catalog = "studs")
@ToString(exclude={"id","gladiatorEntities"})
@EqualsAndHashCode(exclude={"gladiatorEntities","fight","user"})
@NoArgsConstructor
@Getter
@Setter
public class Ticket {

    @Id
    @Column(name = "id_tickets")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "cost")
    private int cost;

    @Column(name = "row")
    private int row;

    @Column(name = "seat")
    private int seat;

    //@Column(name = "id_user")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_fight")
    private Fight fight;

    public Ticket(Fight fight,int cost,int row,int seat){
        this.fight=fight;
        this.cost=cost;
        this.row=row;
        this.seat=seat;
    }

}
