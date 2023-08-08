package book.store.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.store.entity.Author;
import book.store.entity.Book;
import book.store.entity.Genre;
import book.store.service.AuthorService;
import book.store.service.BookService;
import book.store.service.GenreService;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private int genreIdSkillBook=3;
       private int genreIdSelfHelpBook=6;
       private int genreIdChildrenBook=15;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//this is the controller
		try {
			//genre non-fiction list
			GenreService genreService = new GenreService();
			List<Genre> genreNonFicList = genreService.getNonFictionGenres();
			
			//genre fiction-list
			GenreService genreService1 = new GenreService();
			List<Genre> genreFicList = genreService1.getFictionGenres();
			
			//children genres list
			List<Genre> genreChildrenList = genreService.getChildrenGenres();
			//book list
			BookService bookService= new BookService();
			List<Book> bookListFeatured = bookService.getFeaturedBook();

			//monthly list
			List<Book> MonthlyBookList = bookService.getMonthlyDisplay();
			
			//new arrival list
			List<Book> NewArrivalBookList = bookService.getNewBook();
			
			//skill book list
			List<Book> SkillBookList = bookService.getSkillBooks(genreIdSkillBook);
			List<Book> SelfHelpList = bookService.getSkillBooks(genreIdSelfHelpBook);
			List<Book> ChildrenList = bookService.getSkillBooks(genreIdChildrenBook);
			
			//get isbn from book 
			String bookName = request.getParameter("bookName");
		    int bookISBN = bookService.getBookISBNFromName(bookName); 

		    
		    
			//Adding request dispatcher in order to dispatch the request to the home page
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("genreList", genreNonFicList);
			request.setAttribute("genreList1", genreFicList);
			request.setAttribute("genreChildrenList", genreChildrenList);
			request.setAttribute("FeaturedList", bookListFeatured);
			request.setAttribute("NewArrivalBookList", NewArrivalBookList);
			request.setAttribute("MonthlyBookList", MonthlyBookList);
			request.setAttribute("SkillBookList", SkillBookList);
			request.setAttribute("SelfHelpList", SelfHelpList);
			request.setAttribute("ChildrenList", ChildrenList);
			rd.forward(request, response);

			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
