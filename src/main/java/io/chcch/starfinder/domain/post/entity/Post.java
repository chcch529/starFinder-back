package io.chcch.starfinder.domain.post.entity;

import io.chcch.starfinder.domain.post.dto.PostRequest;
import io.chcch.starfinder.global.entity.BaseDateEntity;
import io.chcch.starfinder.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;

    public void update(PostRequest request) {
        this.content = request.getContent();
    }

}

