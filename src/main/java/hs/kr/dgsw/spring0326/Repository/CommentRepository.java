package hs.kr.dgsw.spring0326.Repository;

import hs.kr.dgsw.spring0326.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    //대량의 데이터 Update Delete 할 때
    @Transactional
    void deleteAllByUserId(Long userId);
}
