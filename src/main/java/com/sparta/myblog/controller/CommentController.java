package com.sparta.myblog.controller;

import com.sparta.myblog.Dto.CommentRequestDto;
import com.sparta.myblog.Dto.CommentResponseDto;
import com.sparta.myblog.models.Comment;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //게시글에 댓글 작성
    @PostMapping("/api/auth/comment")
    public CommentResponseDto addComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto){

        Comment comment =commentService.saveComment( requestDto, userDetails );
        return new CommentResponseDto(comment);
    }

    //해당 게시글 댓글 조회
    @GetMapping("/api/comment/{id}")
    public CommentResponseDto readComments(@PathVariable Long id){
        List<Comment> comments= commentService.readComments(id);
        return new CommentResponseDto(comments);
    }

    @PutMapping("/api/auth/comment/{id}")
    public CommentResponseDto updateComments(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long id, @RequestBody CommentRequestDto requestDto  ){
        Comment comment = commentService.updateComment(id,requestDto,userDetails);
        return new CommentResponseDto(comment);
    }

    @DeleteMapping("/api/auth/comment/{id}")
    public CommentResponseDto deleteComments(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long id){
        boolean isDelete = commentService.deleteComment(id,userDetails);
        return new CommentResponseDto(isDelete);
    }

}
