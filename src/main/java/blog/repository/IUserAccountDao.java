package blog.repository;

import java.util.Optional;

import blog.entity.User;
 
public interface IUserAccountDao {
    // Userを取得
    Optional<User> findUser(String userId);
}