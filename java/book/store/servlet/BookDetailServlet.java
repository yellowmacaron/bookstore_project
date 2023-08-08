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
 * Servlet implementation class BookDetailServlet
 */
@WebServlet("/details")
public class BookDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			//genre non-fiction list
			GenreService genreService = new GenreService();
			List<Genre> genreNonFicList = genreService.getNonFictionGenres();
			
			//genre fiction-list
			GenreService genreService1 = new GenreService();
			List<Genre> genreFicList = genreService1.getFictionGenres();
			
			BookService bookService= new BookService();

			Book book= bookService.getBookDetails(bookId);
			List<Book> bookList = bookService.getSmallBookList(bookId);
			List<Book> AuthorBookList=bookService.getBooksSameAuthors(bookId);
			Author author=bookService.getAuthorOnBookDetails(bookId);
			
			
			//Adding request dispatcher in order to dispatch the request to the home page
			RequestDispatcher rd = request.getRequestDispatcher("product-details.jsp");
			request.setAttribute("book", book);
			request.setAttribute("NonFicList", genreNonFicList);
			request.setAttribute("FicList", genreFicList);
			request.setAttribute("bookList", bookList);
			request.setAttribute("author",author);
			request.setAttribute("AuthorBookList",AuthorBookList);
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
