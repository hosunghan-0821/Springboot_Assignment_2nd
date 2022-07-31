package com.sparta.myblog.repository;

import com.sparta.myblog.models.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting,Long> {
    List<Posting> findAllByOrderByCreatedAtDesc();

}
