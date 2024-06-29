package blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
//	@PostMapping(path = "/insert", params = "insert")
//	public String insert(@Valid @ModelAttribute("postForm") PostForm form, BindingResult result, Model model){
//		if (result.hasErrors()) {
//			System.out.println(result.toString());
//			model.addAttribute("error", "パラメータエラーが発生しました。");
//			return "form";
//		}
//
//		blogservice.insert(form);
//		model.addAttribute("postForm", form);
//		return "redirect:/blog"; //二重送信防止
//	}
    @PostMapping(path = "/insert")
    public String insert(@Valid @RequestBody PostForm form, BindingResult result) {
        if (result.hasErrors()) {
        	System.out.println(result.toString());
            System.out.println(form.getTitleForm());
            System.out.println(form.getContentForm());
            return "form";
        }
        blogservice.insert(form);
        return "redirect:/blog"; // 二重送信防止
    }
    
	/**
	 * 投稿を編集
	 * @param PutForm
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return resources/templates/blog.html
	 */
//	@PostMapping(path = "/update", params = "update")
//	public String update(
//			@ModelAttribute PutForm form,
//			BindingResult result,
//			Model model) {
//		if (result.hasErrors()) {
//			model.addAttribute("error", "パラメータエラーが発生しました。");
//			return "form";
//		}
//		System.out.println(form.getTitleForm());
//		System.out.println(form.getContentForm());
//		System.out.println(form.getId());
//		int updateResult = blogservice.update(form); //Title, Content, idが入っている update失敗の場合, 0
//		//update失敗(0の場合)、ログを出力
//		if(updateResult == 0) {
//			System.out.println("update失敗");
//		}
//		return "redirect:/blog";
//	}
    @PostMapping(path = "/update")
    public String insert(@Valid @RequestBody PutForm form, BindingResult result) {
    	System.out.println(result.toString());
        System.out.println(form.getTitleForm());
        System.out.println(form.getContentForm());
        if (result.hasErrors()) {
            return "form";
        }
        int updateResult = blogservice.update(form);
        if(updateResult == 0) {
        	blogservice.update(form);
            return "form";
        }
        return "redirect:/blog";
    }

}
