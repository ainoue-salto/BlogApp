package blog.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
public class PostForm {
	
    @NotNull (message = "題名を入力してください。")
    @Size(min = 1, max = 25, message="25文字以内で入力してください。")
    private String titleForm;
 
    private String contentForm;


    public PostForm(String titleForm, String contentForm){
    	this.titleForm = titleForm;
    	this.contentForm = contentForm;
    }
    
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
    
}