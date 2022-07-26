package com.sparta.myblog.controller;

import com.sparta.myblog.models.Posting;
import com.sparta.myblog.models.PostingRepository;
import com.sparta.myblog.utils.PostingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PostingRepository postingRepository;
    private final PostingUtils postingUtils;

    @GetMapping("/")
    public String main(Model model){
        List<Posting> postings = postingRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("postingList", postingUtils.changeToPostingVo(postings));
        //PostingVo postingVo = new PostingVo()
        return "index";
    }

    @GetMapping("/posting")
    public String writePage(Model model){
        model.addAttribute("posting","");
        return "write";
    }
    @GetMapping("/posting/modified")
    public String updatePage(Model model, @RequestParam Long id){
        Posting posting = postingRepository.findById(id).orElseThrow(()->new NullPointerException("해당 아이디가 없습니다"));
        model.addAttribute("posting",posting);
        return "write";
    }

    @GetMapping("/posting/details")
    public String detailPage(Model model,@RequestParam Long id){
        model.addAttribute("id",id);
        return "postRead";
    }



}
