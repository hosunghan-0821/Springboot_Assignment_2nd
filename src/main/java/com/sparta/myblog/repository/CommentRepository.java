package com.sparta.myblog.repository;

import com.sparta.myblog.models.Comment;
import com.sparta.myblog.models.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId);
    Optional<Comment> findByPostIdAndId(Long postId, Long id);
}
