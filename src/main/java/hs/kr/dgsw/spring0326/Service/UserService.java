package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Domain.User;
import hs.kr.dgsw.spring0326.Protocol.AttachmentProtocol;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> listUser();

    User findUser(Long userId);

    User updateUser(User user);

    boolean deleteUser(Long userId);

    AttachmentProtocol uploadProfile(Long userId, MultipartFile multipartFile);

    void showProfile(Long id, HttpServletRequest req, HttpServletResponse res);

    User login(User user);
}
