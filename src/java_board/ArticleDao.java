package java_board;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class ArticleDao {
	// Data Access Object

	private ArrayList<Article> articles;
	private int no = 4;
	LikesDao likesDao = new LikesDao();

	public ArticleDao() {
//		articles = new ArrayList<>();
//		for(int i = 1; i <= 50; i++)
//		{
//			Article a1 = new Article();
//			a1.setId(i);
//			a1.setTitle("제목 : " + i);
//			a1.setBody("내용 : " + i);
//			
//			articles.add(a1);
//		}
		
		Article a1 = new Article(1, "안녕하세요.", "내용1", "didrudcks", 100, getCurrentDate(), 30);
		Article a2 = new Article(2, "반갑습니다.", "내용2", "a", 30, getCurrentDate(), 20);
		Article a3 = new Article(3, "안녕", "내용3", "hanna001kr", 50, getCurrentDate(), 40);
		Article a4 = new Article(4, "안녕?", "내용4", "1", 40, getCurrentDate(), 15);
		Article a5 = new Article(5, "안녕하세요?", "내용5", "2", 20, getCurrentDate(), 19);
		Article a6 = new Article(6, "안녕ㅎㅎ", "내용6", "3", 10, getCurrentDate(), 5);
		Article a7 = new Article(7, "안녕!", "내용7", "4", 5, getCurrentDate(), 2);
		

//		articles.add(a1);
//		articles.add(a2);
//		articles.add(a3);
//		articles.add(a4);
//		articles.add(a5);
//		articles.add(a6);
//		articles.add(a7);
	}
	
	public void writeJsonFile(JSONObject jobj)
	{
		try {
			int id = (int)jobj.get("id");
			String filePath = "C:/test/article_" + id + ".json";
			
			String jsonText = jobj.toString();
			
			File file = new File("C:/test/article1.json");
			// 파일 객체 생성
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

			if (file.isFile()) {
				// 쓰기
				bufferedWriter.write(jsonText);

				bufferedWriter.close();

			}

		}

		catch (IOException e) {
			System.out.println(e);
		}

	}
	
	public Object articleToJsonObject(Article article)
	{
		JSONObject obj = new JSONObject();
		String jsonText;

		obj.put("id", article.getId());
		obj.put("title", article.getTitle());
		obj.put("body", article.getBody());
		obj.put("writer", article.getWriter());
		obj.put("view", article.getView());
		obj.put("like", article.getLikes());
		obj.put("date", article.getDate());
		
		return obj;
		
	}
	
	public void insertArticle(Article a) {
		a.setId(no);
		no++;
		a.setDate(getCurrentDate());

		JSONObject jobj = (JSONObject)articleToJsonObject(a);
		writeJsonFile(jobj);
		// 파일 저장
		

	}
	
	public void removeArticle(Article a) 
	{
		articles.remove(a);
	}
	
	private static Object getCurrentDate() {
		LocalDate date = LocalDate.now();

		return date;
	}

	// Article ver.
	public Article getArticleById(int targetId) {
		for (int i = 0; i < articles.size(); i++) {
			int id = articles.get(i).getId();
			if (id == targetId) {
				return articles.get(i);
			}
		}
		return null;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}
	
	public ArrayList<Article> getSearchedArticlesByChoice(int choice, String keyword)
	{
		ArrayList<Article> searchedArticles = new ArrayList<>();
		for(int i = 0; i < articles.size(); i++)
		{
			Article article = articles.get(i);
			String str = article.getPropertiesByChoice(choice);
			
			if(str.contains(keyword))
			{
				searchedArticles.add(article);
			}
		
		}
					
		return searchedArticles;
	}
	
//	public ArrayList<Article> getSearchedArticlesByBody(String keyword)
//	{
//		ArrayList<Article> searchedArticles = new ArrayList<>();
//		for(int i = 0; i < articles.size(); i++)
//		{
//			Article article = articles.get(i);
//			String str = article.getBody(); // 각 게시물 내용
//			if( str.contains(keyword))
//			{
//				searchedArticles.add(article);
//			}
//		}
//		return searchedArticles;
//	}
//	
//	public ArrayList<Article> getSearchedArticlesByTitleAndBody(String keyword)
//	{
//		ArrayList<Article> searchedArticles = new ArrayList<>();
//		
//		for(int i = 0; i < articles.size(); i++)
//		{
//			Article article = articles.get(i);
//			String title = article.getTitle();
//			String body = article.getBody();
//			if(title.contains(keyword) || body.contains(keyword))
//			{
//				searchedArticles.add(article);
//			}
//		}
//		
//		return searchedArticles;
//	}
//	
//	public ArrayList<Article> getSearchedByWriter(String keyword)
//	{
//		ArrayList<Article> searchedArticles = new ArrayList<>();
//		
//		for(int i = 0; i < articles.size(); i++)
//		{
//			Article article = articles.get(i);
//			String Writer = article.getWriter();
//			if(Writer.contains(keyword))
//			{
//				searchedArticles.add(article);
//			}
//		}
//		
//		return searchedArticles;
//	}
//	
	public void displayArticles(ArrayList<Article> searchedArticles) // 전체출력
	{
		for(int i = 0; i < searchedArticles.size(); i++)
		{
			Article article = searchedArticles.get(i);
			System.out.println("번호 : " + article.getId());
			System.out.println("제목 : " + article.getTitle());
			System.out.println("작성자 : " + article.getWriter());
			System.out.println("등록날짜 : " + article.getDate());
			System.out.println("조회수 : " + article.getView());
//			int likeCount = likesDao.getLikeCount(article.getId());
			System.out.println("좋아요 : " + article.getLikes());
			System.out.println("====================");
		}
	}
	
	public void displayAnArticle(Article targetArticle, LikesDao likes) // 하나출력
	{
		System.out.println("번호 : " + targetArticle.getId());
		System.out.println("제목 : " + targetArticle.getTitle());
		System.out.println("작성자 : " + targetArticle.getWriter());
		System.out.println("등록날짜 : " + targetArticle.getDate());
		System.out.println("조회수 : " + targetArticle.getView());
//		likesDao = likes;
//		int likeCount = likesDao.getLikeCount(targetArticle.getId());
		System.out.println("좋아요 : " + targetArticle.getLikes()); 
		System.out.println("====================");
		System.out.println("========댓글========");
	}
	
	public void displayArticlesByPage(ArrayList<Article> articles, Page p) // 페이지당 출력
	{
//		System.out.println("==== " + p.getStartIndex());
//		System.out.println("==== " + p.getEndIndex());
		
		
		
		for(int i = p.getStartIndex(); i < p.getEndIndex(); i++)
		{
			System.out.println("번호 : " + articles.get(i).getId());
			System.out.println("제목 : " + articles.get(i).getTitle());
			System.out.println("작성자 : " + articles.get(i).getWriter());
			System.out.println("등록날짜 : " + articles.get(i).getDate());
			System.out.println("조회수 : " + articles.get(i).getView());
			System.out.println("좋아요 : " + articles.get(i).getLikes()); 
			System.out.println("====================");	
		}
	}
}

	