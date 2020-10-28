package java_board;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArticleDao dao = new ArticleDao();
		CommentDao c_dao = new CommentDao();

		while (true) {

			System.out.print("명령어 입력 : ");
			String cmd = sc.next();
			if (cmd.equals("exit")) {
				System.out.println("종료");
				break;
			}

			if (cmd.equals("add")) {

				Article a = new Article();

				System.out.println("게시물 제목을 입력해주세요 :");
				String title = sc.next();

				a.setTitle(title);
				
				System.out.println("게시물 내용을 입력해주세요 :");
				String body = sc.next();

				a.setBody(body);

				a.setWriter("익명");

				dao.insertArticle(a);

				System.out.println("게시물이 등록되었습니다.");

			}

			if (cmd.equals("list")) {
				ArrayList<Article> articles = dao.getArticles();

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					System.out.println("번호 : " + article.getId());
					System.out.println("제목 : " + article.getTitle());
					System.out.println("작성자 : " + article.getWriter());
					System.out.println("등록날짜 : " + article.getDate());
					System.out.println("조회수 : " + article.getView());
//					System.out.println("내용 : " + article.getBody());
					System.out.println("====================");
				}
			}

			if (cmd.equals("update")) {

//				Article ver.
				System.out.println("수정할 게시물 선택 : ");
				int targetId = sc.nextInt();
				Article target = dao.getArticleById(targetId);

				if (target == null) {
					System.out.println("없는 게시물입니다.");
				}

				else {
					System.out.println("게시물 제목을 입력해주세요 :");
					String newTitle = sc.next();

					System.out.println("게시물 내용을 입력해주세요 :");
					String newBody = sc.next();

					target.setTitle(newTitle);
					target.setBody(newBody);

				}

			}

			if (cmd.equals("delete")) {
				ArrayList<Article> articles = dao.getArticles();

				System.out.println("삭제할 게시물 선택 : ");
				int targetId = sc.nextInt();
				Article target = dao.getArticleById(targetId);

				if (target == null) {
					System.out.println(targetId + "번 게시물이 삭제되었습니다.");
				}

				else {
					dao.removeArticle(target);
				}

			}

			if (cmd.equals("read")) {

				System.out.println("몇번 째 게시물을 보시겠습니까?");
				int targetId = sc.nextInt();
				
				Article target = dao.getArticleById(targetId);
				ArrayList<Comment> commentArray = c_dao.getComments();
				

				if (target == null) {
					System.out.println("게시물이 존재하지 않습니다.");
				}

				else {
					target.setView(target.getView() + 1);

					System.out.println("====" + target.getId() + "번 게시물" + "====");
					System.out.println("번호 : " + target.getId());
					System.out.println("제목 : " + target.getTitle());
					System.out.println("내용 : " + target.getBody());
					System.out.println("==================");
					System.out.println("========댓글========");
					c_dao.displayComments(commentArray);

					while(true)
					{
						System.out.println("상세보기 기능을 선택해주세요");
						System.out.println("1. 댓글 등록, 2. 좋아요, 3. 수정, 4. 삭제, 5. 뒤로가기");
						
						int choice = sc.nextInt();
						
						if(choice == 1) // 댓글 등록
						{
							Comment c = new Comment();
							
							System.out.println("댓글 내용을 입력해주세요 : ");
							sc.nextLine();
							String comment = sc.nextLine();
							
							c.setParentId(target);
							c.setComment(comment);
							c.setWriter("익명");
							
							c_dao.insertComment(c);
							
							System.out.println("댓글이 등록 되었습니다.");
							
							dao.displayAnArticle(target);
							c_dao.displayComments(commentArray);
						}
						
						else if(choice == 2) // 좋아요
						{
							System.out.println("[좋아요 기능]");
						}
						
						else if(choice == 3) // 수정
						{
							System.out.println("수정할 제목을 입력해주세요");
							String newTitle = sc.next();
							System.out.println("수정할 내용을 입력해주세요");
							String newBody = sc.next();
							
							target.setTitle(newTitle);
							target.setBody(newBody);	
						}
						
						else if(choice == 4) // 삭제
						{
							dao.removeArticle(target);
							System.out.println("삭제되었습니다.");
							break;
						}
						
						else if(choice == 5) // 뒤로가기
						{
							break;
						}
						
						else
						{
							
						}
						
					}
				}
			}

			if (cmd.equals("search")) {
				
				System.out.println("검색 항목을 선택해주세요");
				System.out.println("1. 제목, 2. 내용, 3. 제목 + 내용, 4. 작성자");
				int choice = sc.nextInt();
				ArrayList<Article> searchedArticles;
				
				if(choice == 1)
				{
					System.out.println("검색할 제목 키워드를 입력해주세요.");
					String keyword = sc.next();
					
					searchedArticles = dao.getSearchedArticlesByChoice(choice, keyword);
					
					dao.displayArticles(searchedArticles);
				}
				
				if(choice == 2)
				{
					System.out.println("검색할 내용 키워드를 입력해주세요.");
					String keyword = sc.next();
					
					searchedArticles = dao.getSearchedArticlesByChoice(choice, keyword);
					
					dao.displayArticles(searchedArticles);
				}
				
				if(choice == 3)
				{
					System.out.println("검색할 제목과 내용 키워드를 입력해주세요");
					String keyword = sc.next();
					
					searchedArticles = dao.getSearchedArticlesByChoice(choice, keyword);
			
					dao.displayArticles(searchedArticles);
					
				}
				
				if(choice == 4)
				{
					System.out.println("검색할 작성자 키워드를 입력해주세요");
					String keyword = sc.next();
					
					searchedArticles = dao.getSearchedArticlesByChoice(choice, keyword);
					
					dao.displayArticles(searchedArticles);
				}
				
				
//				ArrayList<Article> target = dao.getArticles();
//				int check = 1;
//
//				for (int i = 0; i < target.size(); i++) {
//					if (target.get(i).contains(keyword)) {
//						System.out.println(target.get(i).getTitle());
//						check = 2;
//					}
//				}
//				
//				if(check == 1)
//				{
//					System.out.println("검색 결과가 없습니다.");
//				}

			}

		}
	}
}
