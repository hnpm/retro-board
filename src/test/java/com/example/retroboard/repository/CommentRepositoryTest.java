package com.example.retroboard.repository;

import static com.example.retroboard.TestUtils.createComment;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.retroboard.entity.Comment;
import com.example.retroboard.entity.CommentType;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldReturn1Comment() {
        // Given
        Comment comment = createComment("Test", CommentType.PLUS);
        testEntityManager.persist(comment);
        testEntityManager.flush();

        // When
        LocalDate now = LocalDate.now();
        List<Comment> comments = commentRepository.findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonthValue(),
                now.getDayOfMonth());

        // Then
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0)).hasFieldOrPropertyWithValue("text", "Test");
    }

    @Test
    public void save_HappyPath_ShouldSave1Comment() {
        // Given
        Comment comment = createComment("Test", CommentType.PLUS);

        // When
        Comment saved = commentRepository.save(comment);

        // Then
        assertThat(testEntityManager.find(Comment.class, saved.getId())).isEqualTo(saved);
    }
}