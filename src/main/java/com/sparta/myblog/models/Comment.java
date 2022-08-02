package com.sparta.myblog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.myblog.Dto.CommentRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;


    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String comment;

    @ManyToOne // Many = Comment , One = Posting
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name="postId", referencedColumnName = "id")
    private Posting posting; //Db는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트르 저장할 수 있다.

    @ManyToOne
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinColumn(name="userId")
    private UserInfo userInfo;

    public Comment(CommentRequestDto requestDto, UserDetailsImpl userDetails,Posting posting ){
        this.userInfo=userDetails.getUser();
        this.posting=posting;
        this.writer= userDetails.getUsername();
        this.comment= requestDto.getComment();
    }
    public void update(CommentRequestDto requestDto){
        this.comment=requestDto.getComment();
    }



}
