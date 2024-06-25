package blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import blog.entity.MyUserDetails;
import blog.entity.User;
import blog.repository.IUserAccountDao;
 
@Service
public class MyUserService implements UserDetailsService { //UserDetailsService...User情報を取得するためのインターフェース
 
    private final IUserAccountDao dao;
     
    @Autowired
    public MyUserService(IUserAccountDao dao) {
        this.dao = dao;
    }
    
    //ユーザーIDをキーにユーザー情報を取得する
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
         
    	//ユーザーIDでユーザー情報が取得できない可能性に備えて、Optionalクラスで受け取り
        Optional<User> user = dao.findUser(userId);
        //ユーザーIDでユーザー情報が取得できなかった場合
        if(!user.isPresent()) {
            throw new UsernameNotFoundException(userId + "が存在しません");
        }   
        //ユーザーが取得できた場合
        System.out.println(user.get());
        return new MyUserDetails(user.get());
    }
}