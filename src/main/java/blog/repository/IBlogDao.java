package blog.repository;

import java.util.List;

import blog.entity.Blog;
import blog.form.PostForm;
import blog.form.PutForm;

public interface IBlogDao {

	/**
	 * 登録されている投稿を取得
	 */
	List<Blog> findList();

	
	/**
	 * 投稿する
	 * @param form
	 */
	int insert(PostForm form);

	/**
	 * idを指定して日記を1件取得
	 * @param id
	 */
	Blog findById(int id);


	/**
	 * 投稿を更新する
	 * @param form
	 */
	int update(PutForm form);
	

	/**
	 * 投稿を削除する
	 * @param id
	 */
	int delete(int id);
}
