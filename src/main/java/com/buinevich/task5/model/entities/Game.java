package com.buinevich.task5.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "player_one", referencedColumnName = "id")
    private User playerOne;

    @ManyToOne
    @JoinColumn(name = "player_two", referencedColumnName = "id")
    private User playerTwo;

    @OneToOne
    @JoinColumn(name = "winner", referencedColumnName = "id")
    private User winner;

    @OneToOne
    @JoinColumn(name = "turn", referencedColumnName = "id")
    private User turn;

    @Column(name = "tags")
    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    private List<Tag> tags;

    @OneToOne
    @JoinColumn(name = "move_one", referencedColumnName = "id")
    private User moveOne;
    @OneToOne
    @JoinColumn(name = "move_two", referencedColumnName = "id")
    private User moveTwo;
    @OneToOne
    @JoinColumn(name = "move_three", referencedColumnName = "id")
    private User moveThree;
    @OneToOne
    @JoinColumn(name = "move_four", referencedColumnName = "id")
    private User moveFour;
    @OneToOne
    @JoinColumn(name = "move_five", referencedColumnName = "id")
    private User moveFive;
    @OneToOne
    @JoinColumn(name = "move_six", referencedColumnName = "id")
    private User moveSix;
    @OneToOne
    @JoinColumn(name = "move_seven", referencedColumnName = "id")
    private User moveSeven;
    @OneToOne
    @JoinColumn(name = "move_eight", referencedColumnName = "id")
    private User moveEight;
    @OneToOne
    @JoinColumn(name = "move_nine", referencedColumnName = "id")
    private User moveNine;
}
