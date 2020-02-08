package com.climbr.domain;

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
public class Account extends AbstractPersistable<Long> {

    private String userName;
    private String password;
    private String profileString;
    private LocalDateTime joinDateTime = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    private Picture profilePicture;

    @OneToMany(mappedBy = "account")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "author")
    private List<WallPost> wallPosts;

    @OneToMany(mappedBy = "follower")
    private List<FollowingFollower> followers;
    @OneToMany(mappedBy = "following")
    private List<FollowingFollower> following;

    @ManyToMany(mappedBy = "likes")
    private Set<WallPost> likedWallPosts;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + getId() +
                ", userName='" + userName + '\'' +
                ", profileString='" + profileString + '\'' +
                '}';
    }
}
