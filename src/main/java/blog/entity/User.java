package blog.entity;

public class User {
    
    private int id;
    private String userId;
    private String password;
    private String name;
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserId() {
    	System. out.println(userId.toString());
        return userId;
    }
    public void setUserId(String userId) {
    	System. out.println(userId.toString());
        this.userId = userId;
    }
    public String getPassword() {
    	System. out.println(password.toString());
        return password;
    }
    public void setPassword(String password) {
    	System. out.println(password.toString());
        this.password = password;
    }
    public String getName() {
    	System. out.println(name.toString());
        return name;
    }
    public void setName(String name) {
    	System. out.println(name.toString());
        this.name = name;
    }
}