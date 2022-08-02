package com.sparta.myblog.repository;

import com.sparta.myblog.models.Comment;
import com.sparta.myblog.models.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllBypostingIdOrderByCreatedAt(Long postId);
    Optional<Comment> findBypostingIdAndId(Long postId, Long id);
//    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId);
//
}
