package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Domain.User;
import hs.kr.dgsw.spring0326.Repository.CommentRepository;
import hs.kr.dgsw.spring0326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
