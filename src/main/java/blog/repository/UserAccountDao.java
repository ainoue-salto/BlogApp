package blog.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.entity.User;
 
@Repository
public class UserAccountDao implements IUserAccountDao {
 
    private final NamedParameterJdbcTemplate jdbcTemplate;
     
    @Autowired
    public UserAccountDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
     

	/**
	 * ユーザーIDをキーにして、
	 * 入力されたユーザー名及びパスワードが存在しているか&一致しているかを確認
	 * 
	 * @param String userId
	 * @return 取得に成功した場合：Optional<User> userOpl 合致したuserデータ(Map)が内包されたもの
	 * @return 取得できなかった場合：Optional<User> userOpl 空のOptional
	 */
    @Override
    public Optional<User> findUser(String userId) {
         
        String sql = "SELECT id, user_id, password, username "
                + "FROM users "
                + "WHERE user_id = :user_id";
        // パラメータ設定用Map
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
         
        User user = new User();
        // 一件取得
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, param);
            user.setId((int) result.get("id"));
            user.setUserId((String) result.get("user_id"));
            user.setPassword((String)result.get("password"));
            user.setName((String)result.get("username"));
        }catch(EmptyResultDataAccessException e){ //結果に少なくとも 1 つの行（または要素）があると予想されたが、実際にはゼロ行（または要素）が返された場合
            Optional <User> userOpl = Optional.ofNullable(user);
            return userOpl;
        }
         
        // 返り値をラップする
        Optional <User> userOpl = Optional.ofNullable(user);
        System.out.println(userOpl.get());
        return userOpl;
    }
}