package com.example.retroboard.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.retroboard.entity.Comment;
import com.example.retroboard.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Comment> saveAll(List<Comment> comments) {
        log.info("Saving {}", comments);
        return commentRepository.saveAll(comments);
    }

    public List<Comment> getAllCommentsForToday() {
        LocalDate date = LocalDate.now();
        return commentRepository.findByCreatedYearAndMonthAndDay(date.getYear(), date.getMonthValue(),
                date.getDayOfMonth());
    }

}
