package blog.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.entity.Blog;
import blog.form.PostForm;
import blog.form.PutForm;

//DBにアクセスするクラスに付与するアノテーション
@Repository
public class BlogDao implements IBlogDao {

	private final JdbcTemplate jdbcTemplate;

	//依存性を注入
	@Autowired
	public BlogDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 一覧画面に表示するデータを取得する
	 * 
	 * @return list (すべての投稿)
	 */
	@Override
	public List<Blog> findList() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT id, title, content, TO_CHAR(date, 'YYYY/MM/DD') AS date, update_datetime FROM blog "
				+ "WHERE del_flg = '0' "
				+ "ORDER BY date ASC");

		String sql = sqlBuilder.toString();

		//投稿一覧をMapのListで取得 検索機能は使用しないためjdbcTemplate.queryForListに変更
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		//return用の空のListを用意
		List<Blog> list = new ArrayList<Blog>();

		//データをBlogにまとめる
		for (Map<String, Object> result : resultList) {
			Blog blog = new Blog();
			blog.setId((int) result.get("id"));
			blog.setTitle((String) result.get("title"));
			blog.setContent((String) result.get("content"));
			blog.setDate((String) result.get("date"));
			blog.setUpdate_datetime((Timestamp) result.get("update_datetime"));
			list.add(blog);
		}
		return list;
	}

	/**
	 * 投稿する
	 * 
	 * @param PostForm form
	 * @return int 更新した行数(失敗時は0)
	 */
	@Override
	public int insert(PostForm form) {
		String sql = "INSERT INTO blog(title, content, date, update_datetime) "
				+ "VALUES (?, ?, ?, ?)";

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Object[] params = { form.getTitleForm(), form.getContentForm(), timestamp, timestamp };

		return jdbcTemplate.update(sql, params);
	}

	/**
	 * 投稿詳細を表示
	 * 
	 * @param id 一覧画面から選択して取得した投稿のid
	 * @return Map blog 取得したidの投稿データ
	 */
	@Override
	public Blog findById(int id) throws IncorrectResultSizeDataAccessException {
		String sql = "SELECT id, title, content, TO_CHAR(date, 'YYYY/MM/DD') AS date, update_datetime FROM blog "
				+ "WHERE id = ?";

		Object[] param = { id };
		// sqlで1件ずつ取得し、その結果をmap形式で返す
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, param);
		Blog blog = new Blog();
		blog.setId((int) result.get("id"));
		blog.setTitle((String) result.get("title"));
		blog.setContent((String) result.get("content"));
		blog.setDate((String) result.get("date"));
		blog.setUpdate_datetime((Timestamp) result.get("update_datetime"));
		return blog;
	}

	
	/**
	 *  投稿を編集
	 * 
	 * @param PutForm form
	 * @return int 更新した行数(失敗時は0)
	 */
	@Override
	public int update(PutForm form) {
		String sql = "UPDATE blog "
				+ "SET title=?, content=?, update_datetime=? "
				+ "WHERE id=?";
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Object[] params = { form.getTitleForm(), form.getContentForm(), timestamp, form.getId() };

		return jdbcTemplate.update(sql, params); //更新した行数(int)が入る
	}
	

	/**
	 * 投稿を削除する
	 * 
	 * @param int id 一覧画面から選択して取得した投稿のid
	 * @return int 更新した行数(失敗時は0)
	 */
	@Override
	public int delete(int id) {
	    int count = 0;
	    String sql = "UPDATE blog SET del_flg = 1 "
	            + "WHERE id = ?";

	    Object[] param = { id };
	    count = jdbcTemplate.update(sql, param);
	    return count;
	}
}
