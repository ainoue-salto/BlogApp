package blog.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PutForm {

	@NotNull
	private int id;

	@NotNull(message = "題名を入力してください。")
	@Size(min = 1, max = 25, message = "25文字以内で入力してください。")
	private String titleForm;

	@NotNull
	private String contentForm;
	
	public PutForm(String titleForm, String contentForm, int id) {
    	this.titleForm = titleForm;
    	this.contentForm = contentForm;
    	this.id = id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}