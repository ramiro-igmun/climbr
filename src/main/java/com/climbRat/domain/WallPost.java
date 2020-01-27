package com.climbRat.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class WallPost extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Account author;
    private LocalDateTime postDateTime = LocalDateTime.now();
    @Column(length = 300)
    private String message;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parentPost")
    private Picture picture;

    @OneToMany(mappedBy = "parentPost")
    private List<WallPost> comments;
    @ManyToOne(fetch = FetchType.LAZY)
    private WallPost parentPost;

    @ManyToMany
    private Set<Account> likes;

    @Override
    public String toString() {
        return "WallPost{" +
                "id=" + getId() +
                ", postDateTime=" + postDateTime +
                '}';
    }
}
