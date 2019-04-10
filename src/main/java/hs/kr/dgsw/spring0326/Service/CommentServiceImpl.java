package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Domain.Comment;
import hs.kr.dgsw.spring0326.Domain.User;
import hs.kr.dgsw.spring0326.Protocol.AttachmentProtocol;
import hs.kr.dgsw.spring0326.Protocol.CommentUsernameProtocol;
import hs.kr.dgsw.spring0326.Repository.CommentRepository;
import hs.kr.dgsw.spring0326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

//    @PostConstruct
//    private void init() {
//        User u = this.userRepository.save(new User("AAA", "CCCC@na.co"));
//        this.commentRepository.save(new Comment(u.getId(), "Hello, World!11"));
//        this.commentRepository.save(new Comment(u.getId(), "Hello, World!22"));
//        this.commentRepository.save(new Comment(u.getId(), "Hello, World!33"));
//    }

    @Override
    public CommentUsernameProtocol saveComment(Comment comment) {
        User found = this.userRepository.findById(comment.getUserId()).orElse(null);
        if (found == null)
            return null;
        return new CommentUsernameProtocol(this.commentRepository.save(comment), found.getUsername());
    }

    @Override
    public List<CommentUsernameProtocol> listAllComments() {
        List<Comment> commentList = this.commentRepository.findAll();
        List<CommentUsernameProtocol> cupList = new ArrayList<>();
        commentList.forEach(comment -> {
            Optional<User> found =
                    this.userRepository.findById(comment.getUserId());
            String username = null;
            if (found.isPresent())
                username = found.get().getUsername();
            CommentUsernameProtocol cup = new CommentUsernameProtocol(comment, username);
            cupList.add(cup);
        });
        return cupList;
    }

    @Override
    public List<CommentUsernameProtocol> listByUserId(Long userId) {
        List<CommentUsernameProtocol> protocols;
        List<Comment> comments;
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null)
            return null;
        protocols = new ArrayList<>();
        comments = this.commentRepository.findByUserId(userId);
        comments.forEach(comment -> {
            protocols.add(commentToProtocol(comment));
        });
        return protocols;
    }

    @Override
    public CommentUsernameProtocol findCommentById(Long id) {
        Comment comment = this.commentRepository.findById(id).orElse(null);
        return commentToProtocol(comment);
    }

    @Override
    public boolean deleteComment(Long id) {
        try {
            this.commentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CommentUsernameProtocol updateComment(Comment comment) {
        Comment updated = this.commentRepository.findById(comment.getId()).map(comment1 -> {
            comment1.setContent(comment.getContent());
            if (comment.getFileName() != null)
                comment1.setFileName(comment.getFileName());
            if (comment.getFilePath() != null)
                comment1.setFilePath(comment.getFilePath());
            return this.commentRepository.save(comment1);
        }).orElse(null);
        return commentToProtocol(updated);
    }

    @Override
    public AttachmentProtocol uploadCommentImage(MultipartFile uploadFile) {
        String rootDirectory = "C:/Users/baejunjae/IdeaProjects/spring0326/upload/comment/";
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/"
                + UUID.randomUUID().toString() + "_"
                + uploadFile.getOriginalFilename();
        String destFilename = rootDirectory + fileName;
        File destFile = new File(destFilename);
        try {
            destFile.getParentFile().mkdirs();
            uploadFile.transferTo(destFile);
            return new AttachmentProtocol(destFilename, uploadFile.getOriginalFilename());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void showCommentImage(Long id, HttpServletRequest req, HttpServletResponse res) {
        try {
            String filePath = "";
            String fileName = "";
            Comment found = findCommentById(id);
            if (found == null)
                return;

            filePath = found.getFilePath();
            fileName = found.getFileName();

            File file = new File(filePath);
            if (!file.exists())
                return;
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null)
                mimeType = "application/octet-stream";

            res.setContentType(mimeType);
            res.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            res.setContentLength((int) file.length());
            InputStream is;
            is = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(is, res.getOutputStream());

        } catch (Exception e) {
            System.out.println("Comment Upload Exception" + e.getMessage());
        }
    }

    private CommentUsernameProtocol commentToProtocol(Comment comment) {
        if (comment == null)
            return null;
        User user;
        user = this.userRepository.findById(comment.getUserId()).orElse(null);
        if (user == null)
            return null;
        return new CommentUsernameProtocol(comment, user.getUsername());
    }
}
