package com.example.retroboard;

import java.sql.Timestamp;

import com.example.retroboard.entity.Comment;
import com.example.retroboard.entity.CommentType;
import com.example.retroboard.entity.User;

public class TestUtils {

    public static Comment createComment(String text, CommentType type) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setType(type);
        comment.setCreatedBy("hien");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return comment;
    }

    public static User createUser() {
        User user = new User();
        user.setUsername("hien");
        user.setPassword("password");
        user.setRole("USER");
        return user;
    }
}
