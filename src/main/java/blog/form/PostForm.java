package blog.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
public class PostForm {

//    private int id;
 
//    @NotNull (message = "日付を入力してください。")
//    private Date dateForm;
 
	//javax.validationライブラリを利用して画面の入力値チェックを実行
    @NotNull (message = "題名を入力してください。")
    @Size(min = 1, max = 25, message="25文字以内で入力してください。")
    private String titleForm;
 
    private String contentForm;


    public PostForm(String titleForm, String contentForm){
    	this.titleForm = titleForm;
    	this.contentForm = contentForm;
    }
    
//    public Date getDateForm() {
//        return dateForm;
//    }
// 
//    public void setDateForm(Date dateForm) {
//        this.dateForm = dateForm;
//    }
// 
    public String getTitleForm() {
        return titleForm;
    }
 
    public void setTitleForm(String titleForm) {
        this.titleForm = titleForm;
    }
 
    public String getContentForm() {
        return contentForm;
    }
 
    public void setContentForm(String contentForm) {
        this.contentForm = contentForm;
    }
    
//    public int getId() {
//        return id;
//    }
// 
//    public void setId(int id) {
//        this.id = id;
//    }
}