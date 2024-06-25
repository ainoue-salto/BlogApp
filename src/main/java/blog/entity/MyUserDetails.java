package blog.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 *ユーザーとパスワードを入力し、
 *ユーザーが存在した場合にその他のチェックをする 
 *
 *
 */
public class MyUserDetails implements UserDetails {
     
 
    private static final long serialVersionUID = 1L;
    private final User user;
     
    //User.javaを内包して使用する
    public MyUserDetails(User user) {
        this.user = user;
        System. out.println(user.toString());
    }
 
    //権限情報を返却する
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
 
    //パスワードを返却する
    @Override
    public String getPassword() {
    	System. out.println(user.toString());
        return user.getPassword();
    }
 
    //ユーザー名を返却する
    @Override
    public String getUsername() {
    	System. out.println(user.toString());
        return user.getName();
    }
 
    //アカウントが有効期限内かどうかを返却する
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    //アカウントがロックかアンロックを返却する
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    //資格情報の有効期限内をかどうかを返却する
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    //アカウントが有効なユーザーかどうかを返却する
    @Override
    public boolean isEnabled() {
        return true;
    }
     
}