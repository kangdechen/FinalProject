package bean;

import bean.Recommendation;

public class Recommendation {
  private int Id;
  
  private String username;
  
  private String movieName;
  
  private String comment;
  
  private Float rating;
  
  private String pic;
  
  public Recommendation(int id, String username, String movieName, String comment, Float rating, String pic) {
    this.Id = id;
    this.username = username;
    this.movieName = movieName;
    this.comment = comment;
    this.rating = rating;
    this.pic = pic;
  }
  
  public Recommendation() {}
  
  public int getId() {
    return this.Id;
  }
  
  public void setId(int id) {
    this.Id = id;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getMovieName() {
    return this.movieName;
  }
  
  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }
  
  public String getComment() {
    return this.comment;
  }
  
  public void setComment(String comment) {
    this.comment = comment;
  }
  
  public Float getRating() {
    return this.rating;
  }
  
  public void setRating(Float rating) {
    this.rating = rating;
  }
  
  public String getPic() {
    return this.pic;
  }
  
  public void setPic(String pic) {
    this.pic = pic;
  }
}
