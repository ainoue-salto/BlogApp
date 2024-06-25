package blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.entity.Blog;
import blog.form.PostForm;
import blog.form.PutForm;
import blog.repository.BlogDao;
import blog.repository.IBlogDao;

@Service //サービス層のクラス(ビジネスロジック等)に該当
@Transactional //クラス内のメソッドすべてが例外時に自動でロールバック
public class BlogService {

	private final IBlogDao dao;

	@Autowired
	public BlogService(BlogDao dao) {
		this.dao = dao;
	}

	/**
	 * すべての投稿を取得するsqlの実行先に繋ぐ
	 * 
	 * @return list (すべての投稿)
	 */
	public List<Blog> findList() {
		return dao.findList();
	}
	
	/**
	 * 投稿するsqlの実行先に繋ぐ
	 * 
	 * @param form
	 * @return int 更新した行数(失敗時は0)
	 */
	public int insert(PostForm form) {
		return dao.insert(form);
	}

	/**
	 * 任意の投稿詳細を表示するsqlの実行先に繋ぐ
	 * 
	 * @param int id 一覧画面にて選択して取得したid
	 * @return 成功時：Map blog 取得したidの投稿データ
	 * @return 失敗時(結果が期待してた件数じゃない場合)：null
	 */
	public Blog findById(int id) {
		try {
			return dao.findById(id);
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	/**
	 * 投稿を編集するsqlの実行先に繋ぐ
	 * 
	 * @param PutForm form
	 * @return int 更新した行数(失敗時は0)
	 */
	public int update(PutForm form) {
		return dao.update(form);
	}
	
	/**
	 * 投稿を論理削除するsqlの実行先に繋ぐ
	 * 
	 * @param  
	 * @return 
	 */
	public int delete(int id) {
	    return dao.delete(id);
	}
}
