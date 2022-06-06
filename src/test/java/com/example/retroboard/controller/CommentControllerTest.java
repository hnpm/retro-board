package com.example.retroboard.controller;

import static com.example.retroboard.TestUtils.createComment;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.retroboard.entity.Comment;
import com.example.retroboard.entity.CommentType;
import com.example.retroboard.service.CommentService;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    public void saveComments_HappyPath_ShouldReturnStatus302() throws Exception {
        // When
        var result = mockMvc.perform(
                post("/comment").with(csrf()).with(user("hien").roles("USER")).param("plusComment", "Test Plus"));

        // Then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(commentService, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(commentService);
    }

    @Test
    public void getComments_HappyPath_ShouldReturnStatus200() throws Exception {
        // Given
        Comment comment = createComment("Test Plus", CommentType.PLUS);
        Comment comment2 = createComment("Test Star", CommentType.STAR);
        List<Comment> comments = List.of(comment, comment2);
        when(commentService.getAllCommentsForToday()).thenReturn(comments);

        // When
        var result = mockMvc.perform(get("/").with(user("hien").roles("USER")));

        // Then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("comment"))
                .andExpect(model().attribute("plusComments", hasSize(1)))
                .andExpect(model().attribute("plusComments",
                        hasItem(allOf(hasProperty("createdBy", is("hien")), hasProperty("text", is("Test Plus"))))))
                .andExpect(model().attribute("starComments", hasSize(1)))
                .andExpect(model().attribute("starComments",
                        hasItem(allOf(hasProperty("createdBy", is("hien")), hasProperty("text", is("Test Star"))))));

        verify(commentService, times(1)).getAllCommentsForToday();
        verifyNoMoreInteractions(commentService);
    }
}
