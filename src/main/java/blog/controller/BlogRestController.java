package blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import blog.form.PostForm;
import blog.form.PutForm;
import blog.service.BlogService;
import jakarta.validation.Valid;

@RestController
public class BlogRestController {
	private BlogService blogservice;

	//@Autowired = Springが起動時にインスタンス化したインスタンスを入れる機能
	@Autowired
	public BlogRestController(BlogService blogservice) {
		this.blogservice = blogservice;
	}

	
	/**
	 * 日記を新規登録
	 * 
	 * @param @Valid formにバインドされたデータをチェックする
	 * @param PostForm form 新規登録時に画面に入力された値
	 * @param BindingResult result 「@Valid」でチェックした値を受け取る エラー発生時に条件分岐可能
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return 成功時：resources/templates/blog.html
	 * @return エラー発生時：resources/templates/form.html
	 */
    @PostMapping(path = "/insert")
    public ResponseEntity<String> insert(@Valid @RequestBody PostForm form, BindingResult result) {
    	System.out.println("jasontest");
        if (result.hasErrors()) {
            // エラーメッセージを収集
            StringBuilder errorMessages = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            for (ObjectError error : result.getGlobalErrors()) {
                errorMessages.append(error.getObjectName()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            
            // エラーメッセージをJSON形式で返す
            return ResponseEntity.badRequest().body("{\"error\": \"" + errorMessages.toString() + "\"}");
        }
        blogservice.insert(form); // データを挿入する処理
        return ResponseEntity.ok("{\"message\": \"登録成功\", \"redirect\": \"/blog\"}"); // 二重送信防止
    }
    
	/**
	 * 投稿を編集
	 * @param PutForm
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return resources/templates/blog.html
	 */
    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@Valid @RequestBody PutForm form, BindingResult result) {
    	System.out.println(result.toString());
        System.out.println(form.getTitleForm());
        System.out.println(form.getContentForm());
        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            for (ObjectError error : result.getGlobalErrors()) {
                errorMessages.append(error.getObjectName()).append(": ").append(error.getDefaultMessage()).append("; ");
            }

            // エラーメッセージをJSON形式で返す
            return ResponseEntity.badRequest().body("{\"error\": \"" + errorMessages.toString() + "\"}");
        }
        int updateResult = blogservice.update(form);
        if(updateResult == 0) {
        	return ResponseEntity.badRequest().body("{\"error\": \"更新失敗\"}");
        }
        return ResponseEntity.ok("{\"message\": \"更新成功\", \"redirect\": \"/blog\"}");
    }

}
