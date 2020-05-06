package bean;
import bean.User;

public class User {
  private String no;
  
  private String username;
  
  private String password;
  
  private String pic;
  
  public User(String no, String username, String password) {
    this.no = no;
    this.username = username;
    this.password = password;
  }
  
  public User(String no, String username, String password, String pic) {
    this.no = no;
    this.username = username;
    this.password = password;
    this.pic = pic;
  }
  
  public String getNo() {
    return this.no;
  }
  
  public void setNo(String no) {
    this.no = no;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getPic() {
    return this.pic;
  }
  
  public void setPic(String pic) {
    this.pic = pic;
  }
}
