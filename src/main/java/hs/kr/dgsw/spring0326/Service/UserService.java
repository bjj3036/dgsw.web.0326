package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Domain.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> listUser();

    User findUser(Long userId);

    User updateUser(User user);

    boolean deleteUser(Long userId);
}
