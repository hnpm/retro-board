package com.example.retroboard.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.retroboard.entity.Comment;
import com.example.retroboard.entity.CommentType;
import com.example.retroboard.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Comment> allComments = commentService.getAllCommentsForToday();
        Map<CommentType, List<Comment>> groupedComments = allComments.stream()
                .collect(Collectors.groupingBy(Comment::getType));
        model.addAttribute("starComments", groupedComments.get(CommentType.STAR));
        model.addAttribute("deltaComments", groupedComments.get(CommentType.DELTA));
        model.addAttribute("plusComments", groupedComments.get(CommentType.PLUS));
        return "comment";
    }

    @PostMapping("/comment")
    public String create(@RequestParam(name = "plusComment", required = false) String plusComment,
            @RequestParam(name = "deltaComment", required = false) String deltaComment,
            @RequestParam(name = "starComment", required = false) String starComment) {
        List<Comment> comments = new ArrayList<>();

        if (StringUtils.hasText(plusComment)) {
            comments.add(createComment(plusComment, CommentType.PLUS));
        }

        if (StringUtils.hasText(deltaComment)) {
            comments.add(createComment(deltaComment, CommentType.DELTA));
        }

        if (StringUtils.hasText(starComment)) {
            comments.add(createComment(starComment, CommentType.STAR));
        }

        if (!comments.isEmpty()) {
            log.info("Saved {}", commentService.saveAll(comments));
        }

        return "redirect:/";
    }

    private Comment createComment(String text, CommentType type) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setType(type);
        return comment;
    }
}
