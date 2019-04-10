package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Domain.Comment;
import hs.kr.dgsw.spring0326.Protocol.AttachmentProtocol;
import hs.kr.dgsw.spring0326.Protocol.CommentUsernameProtocol;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CommentService {
    CommentUsernameProtocol saveComment(Comment comment);
    List<CommentUsernameProtocol> listAllComments();
    List<CommentUsernameProtocol> listByUserId(Long userId);
    CommentUsernameProtocol findCommentById(Long id);
    boolean deleteComment(Long id);
    CommentUsernameProtocol updateComment(Comment comment);
    AttachmentProtocol uploadCommentImage(MultipartFile uploadFile);
    void showCommentImage(Long id, HttpServletRequest req, HttpServletResponse res);
}
