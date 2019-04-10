package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Domain.User;
import hs.kr.dgsw.spring0326.Protocol.AttachmentProtocol;
import hs.kr.dgsw.spring0326.Repository.CommentRepository;
import hs.kr.dgsw.spring0326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public User saveUser(User user) {
        Optional<User> found = this.userRepository.findByEmail(user.getEmail());
        if (found.isPresent())
            return null;
        return userRepository.save(user);
    }

    @Override
    public List<User> listUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User findUser(Long userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.findById(user.getId())
                .map(user1 -> {
                    user1.setUsername(user.getUsername());
                    user1.setEmail(user.getEmail());
                    return this.userRepository.save(user1);
                })
                .orElse(null);
    }

    @Override
    public boolean deleteUser(Long userId) {
        try {
            this.userRepository.deleteById(userId);
            this.commentRepository.deleteAllByUserId(userId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public AttachmentProtocol uploadProfile(Long id, MultipartFile uploadFile) {
        Optional<User> found = this.userRepository.findById(id);
        if (!found.isPresent())
            return null;
        User foundUser = found.get();
        String rootDirectory = "C:/Users/baejunjae/IdeaProjects/spring0326/upload/";
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/"
                + UUID.randomUUID().toString() + "_"
                + uploadFile.getOriginalFilename();
        String destFilename = rootDirectory + fileName;
        File destFile = new File(destFilename);
        try {
            destFile.getParentFile().mkdirs();
            uploadFile.transferTo(destFile);
            foundUser.setFilePath(destFilename);
            foundUser.setFileName(uploadFile.getOriginalFilename());
            this.userRepository.save(foundUser);
            return new AttachmentProtocol(destFilename, uploadFile.getOriginalFilename());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void showProfile(Long id, HttpServletRequest req, HttpServletResponse res) {
        try {
            String filePath = "";
            String fileName = "";

            User found = findUser(id);

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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User login(User user) {
        User found = this.userRepository.findById(user.getId()).orElse(null);
        if(found == null)
            return null;
        if(found.getPassword().equals(user.getPassword()))
            return found;
        return null;
    }
}
