package book.store.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.store.entity.Book;
import book.store.entity.Genre;
import book.store.service.BookService;
import book.store.service.GenreService;


/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/book")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String genreId = request.getParameter("genreId");
			String bookName = request.getParameter("bookName");
			
			GenreService genreService = new GenreService();
			List<Genre> FicList = genreService.getFictionGenres();
			
			//genre non-fiction list
			GenreService genreService1 = new GenreService();
			List<Genre> NonFicList = genreService.getNonFictionGenres();
			
			BookService bookService= new BookService();
			List<Book> bookList = new ArrayList<Book>();
			List<Book> randomBookList=new ArrayList<Book>();
			List<Book> randomBookList1=new ArrayList<Book>();
		
			if(genreId == null && bookName == null) {
				bookList = bookService.getAllBooks();
				randomBookList=bookService.getThreeBooks();
				randomBookList1=bookService.getThreeBooks();
			}
			else if(bookName != null) {
				bookList = bookService.getBooksByName(bookName);
			}
			else {
				bookList = bookService.getBooksByGenreId(Integer.parseInt(genreId));
				randomBookList=bookService.getThreeBooks();
				randomBookList1=bookService.getThreeBooks();
			}
			
			//Adding request dispatcher in order to dispatch the request to the home page
			RequestDispatcher rd = request.getRequestDispatcher("shop-list.jsp");
			request.setAttribute("bookList", bookList);
			request.setAttribute("FicList", FicList);
			request.setAttribute("NonFicList", NonFicList);
			request.setAttribute("randomBookList", randomBookList);
			request.setAttribute("randomBookList1", randomBookList1);
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
