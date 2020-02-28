package com.climbr.domain;

import com.climbr.domain.Validators.AccountCreationValidationGroup;
import com.climbr.domain.Validators.ProfileEditValidationGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Account extends AbstractPersistable<Long> {

    @NotBlank(message = "You must enter a user name",
            groups = {AccountCreationValidationGroup.class})
    @Size(max = 20,message = "The user name must have less than 20 characters",
            groups = {AccountCreationValidationGroup.class, ProfileEditValidationGroup.class})
    private String userName;
    @NotBlank(message = "You must enter a valid Nick name",
            groups = {AccountCreationValidationGroup.class})
    @Size(max = 20, message = "The Nickname must have less than 20 characters",
            groups = {AccountCreationValidationGroup.class, ProfileEditValidationGroup.class})
    private String profileString;
    @NotBlank
    @Size(min = 4, max = 10, message = "The password must be between 4 and 10 characters long",
            groups = {AccountCreationValidationGroup.class})
    private String password;
    private LocalDateTime joinDateTime = LocalDateTime.now();


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Picture profilePicture;

    //@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    //private List<Picture> pictures;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
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
