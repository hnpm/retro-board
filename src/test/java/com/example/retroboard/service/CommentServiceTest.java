package com.example.retroboard.service;

import static com.example.retroboard.TestUtils.createComment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.retroboard.entity.Comment;
import com.example.retroboard.entity.CommentType;
import com.example.retroboard.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        // Given
        Comment comment = createComment("Test", CommentType.PLUS);
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
        Comment comment = createComment("Test Plust", CommentType.PLUS);
        Comment comment2 = createComment("Test Star", CommentType.STAR);
        List<Comment> comments = List.of(comment, comment2);
        when(commentRepository.saveAll(comments)).thenReturn(comments);

        // When
        List<Comment> saved = commentService.saveAll(comments);

        // Then
        assertThat(saved).isNotEmpty();
        verify(commentRepository, times(1)).saveAll(comments);
    }
}
