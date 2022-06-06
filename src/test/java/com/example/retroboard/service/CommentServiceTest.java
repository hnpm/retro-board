package com.example.retroboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.retroboard.entity.Comment;
import com.example.retroboard.entity.CommentType;
import com.example.retroboard.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    public void init() {
        commentService = new CommentService(commentRepository);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        // Given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = List.of(comment);
        LocalDate now = LocalDate.now();
        when(commentRepository.findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonthValue(), now.getDayOfMonth()))
                .thenReturn(comments);

        // When
        List<Comment> actualComments = commentService.getAllCommentsForToday();

        // Then
        verify(commentRepository, times(1)).findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonthValue(),
                now.getDayOfMonth());
        assertThat(comments).isEqualTo(actualComments);
    }

    @Test
    public void saveAll_HappyPath_ShouldSave2Comments() {
        // Given
        Comment comment = new Comment();
        comment.setComment("Test Plus");
        comment.setType(CommentType.PLUS);
        comment.setCreatedBy("hien");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        Comment comment2 = new Comment();
        comment2.setComment("Test Star");
        comment2.setType(CommentType.STAR);
        comment2.setCreatedBy("hien");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = List.of(comment, comment2);
        when(commentRepository.saveAll(comments)).thenReturn(comments);

        // When
        List<Comment> saved = commentService.saveAll(comments);

        // Then
        assertThat(saved).isNotEmpty();
        verify(commentRepository, times(1)).saveAll(comments);
    }
}
