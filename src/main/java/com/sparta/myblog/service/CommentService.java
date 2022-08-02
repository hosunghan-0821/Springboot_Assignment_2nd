package com.sparta.myblog.service;

import com.sparta.myblog.Dto.CommentRequestDto;
import com.sparta.myblog.Dto.CommentResponseDto;
import com.sparta.myblog.models.Comment;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    //댓글 저장
    public Comment saveComment (CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Comment comment = new Comment(requestDto,userDetails);
        return commentRepository.save(comment);
    }

    //댓글 조회
    public List<Comment> readComments(Long id){
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(id);
    }

    //댓글 수정
    @Transactional
    public Comment updateComment(Long id,CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Comment comment=commentRepository.findByPostIdAndId(requestDto.getPostId(), id).orElseThrow(()->new NullPointerException("해당하는 댓글이 없습니다"));
        if(userDetails.getUsername().equals(comment.getWriter())){
            comment.update(requestDto);
            return comment;
        }
        else{
            throw new NullPointerException("작성자만 댓글을 지울 수 있습니다.");
        }
    }

    public boolean deleteComment(Long id,UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디의 댓글 없습니다"));
        if(comment.getWriter().equals(userDetails.getUsername())){
            commentRepository.deleteById(id);
            return true;
        }
        else{
           throw new NullPointerException("작성자만 댓글을 삭제할 수 있습니다");
        }

    }
}
