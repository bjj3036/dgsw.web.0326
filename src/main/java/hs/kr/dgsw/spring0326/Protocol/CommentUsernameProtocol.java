package hs.kr.dgsw.spring0326.Protocol;

import hs.kr.dgsw.spring0326.Domain.Comment;
import lombok.Data;

public class CommentUsernameProtocol
        extends Comment {
    private String username;

    public CommentUsernameProtocol(Comment c, String username){
        super(c);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
