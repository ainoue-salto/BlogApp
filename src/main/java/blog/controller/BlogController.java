package blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.entity.Blog;
import blog.form.PostForm;
import blog.form.PutForm;
import blog.service.BlogService;
import jakarta.validation.Valid;

@Controller
//urlの末尾につく
@RequestMapping("/blog")
public class BlogController {

	private BlogService blogservice;

	//@Autowired = Springが起動時にインスタンス化したインスタンスを入れる機能
	@Autowired
	public BlogController(BlogService blogservice) {
		this.blogservice = blogservice;
	}

	
	
	/**
	 * アプリの一覧画面を表示
	 * 
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return resources/templates/list.html
	 */
	//引数に入れているときは勝手にnewしてくれる
	@GetMapping
	public String blogList(Model model) {        
		List<Blog> list = blogservice.findList(); //一覧に表示するすべての投稿を取得する
		model.addAttribute("list", list);         //listという名前でlistの値の中身を渡す htmlでは${list}
		return "list";
	}

	/**
	 * 新規登録へ遷移
	 * 
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return resources/templates/form.html
	 */
	@GetMapping("/form")
	public String formPage(Model model) {
		return "form";
	}

	/**
	 * 「一覧へ」選択時、一覧画面へ遷移
	 * 
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return resources/templates/blog.html
	 */
	@PostMapping(path={"/insert", "/form", "/update"}, params="back")
	public String backPage(Model model){
	    return "redirect:/blog";
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
	@PostMapping(path = "/insert", params = "insert")
	public String insert(@Valid @ModelAttribute("postForm") PostForm form, BindingResult result, Model model){
		if (result.hasErrors()) {
			System.out.println(result.toString());
			model.addAttribute("error", "パラメータエラーが発生しました。");
			return "form";
		}

		blogservice.insert(form);
		model.addAttribute("postForm", form);
		return "redirect:/blog"; //二重送信防止
	}

	/**
	 * 一件タスクデータを取得し、詳細ページ表示
	 * 
	 * @PathVariable URLに含まれる動的なパラメータを受け取ることができ、受け取った値を@GetMappingにて指定することが可能
	 * @param int id 一覧画面にて選択して取得した投稿のid
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return 成功時：resources/templates/detail.html
	 * @return 失敗時：resources/templates/list.html
	 */
	@GetMapping("/{id}")
	public String showUpdate(@PathVariable int id, Model model) {
		//Blogを取得
		Blog blog = blogservice.findById(id);

		//NULLかどうかのチェック
		if (blog != null) {
			model.addAttribute("blog", blog); //blogという名前でblogの値の中身を渡す htmlでは${blog}
			return "detail";
		} else {
			System.out.println("error:対象データが存在しません");
			return "list";
		}
	}

	/**
	 * 投稿を編集
	 * @param PutForm
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return resources/templates/blog.html
	 */
	@PostMapping(path = "/update", params = "update")
	public String update(
			@ModelAttribute PutForm form,
			BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("error", "パラメータエラーが発生しました。");
			return "form";
		}
		System.out.println(form.getTitleForm());
		System.out.println(form.getContentForm());
		System.out.println(form.getId());
		int updateResult = blogservice.update(form); //Title, Content, idが入っている update失敗の場合, 0
		//update失敗(0の場合)、ログを出力
		if(updateResult == 0) {
			System.out.println("update失敗");
		}
		return "redirect:/blog";
	}
	
	
	/**
	 * 詳細画面から更新画面へ遷移
	 * 
	 * @param PutForm form 
	 * @param Model model
	 * @return  resources/templates/updateform.html
	 */
	@PostMapping("/updateform")
	public String formPage(@ModelAttribute PutForm form, Model model) {
		model.addAttribute("putForm", form); //putFormという名前でformの値の中身を渡す htmlでは${putForm}
		return "updateform";
	}
	
	
	/**
	 * 日記を削除
	 * 
	 * @RequestParam ブラウザからのリクエストの値(パラメータ)を取得することができるアノテーション 
	 * @param int id 一覧画面から選択して取得した投稿のid
	 * @param Model model 取得したデータを渡すことでよしなにhtmlのthymeleafに記載されているjavaの値を入れてくれるクラス
	 * @return resources/templates/blog.html
	 */
	@PostMapping("/delete")
	public String delete(@RequestParam int id, Model model){
	    blogservice.delete(id);
	    return "redirect:/blog"; //リクエストの二重送信防止
	}

}
