package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Domain.Comment;
import hs.kr.dgsw.spring0326.Protocol.CommentUsernameProtocol;

import java.util.List;

public interface CommentService {
    CommentUsernameProtocol saveComment(Comment comment);
    List<CommentUsernameProtocol> listAllComments();
    List<CommentUsernameProtocol> listByUserId(Long userId);
    CommentUsernameProtocol findCommentById(Long id);
    boolean deleteComment(Long id);
    CommentUsernameProtocol updateComment(Comment comment);
}
