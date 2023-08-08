package book.store.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import book.store.entity.Book;
import book.store.entity.Genre;
import book.store.entity.Order;
import book.store.entity.OrderDetails;
import book.store.service.BookService;
import book.store.service.GenreService;
import book.store.service.OrderDetailsService;
import book.store.service.OrderService;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		// Get book id and command from the view

		try {

			// Import List of genre here to display book's genre on navigation bar
			GenreService genreService = new GenreService();
			List<Genre> genreList = genreService.getAllGenres();

			int bookId = 0;
			String command = request.getParameter("command");

			// Add to cart case
			if (command != null && command.equals("ADD_TO_CART")) {
				bookId = Integer.parseInt(request.getParameter("bookId"));
				BookService bookService = new BookService();
				Book book = bookService.getBookDetails(bookId);
				HttpSession session = request.getSession();
				Map<Integer, Book> cart = (Map<Integer, Book>) session.getAttribute("cart");

				if (cart == null) {
					cart = new HashMap<Integer, Book>();
				}

				cart.put(book.getIsbn(), book);
				session.setAttribute("cart", cart);
				request.setAttribute("book", book);

				response.sendRedirect("details?command=DETAIL&bookId=" + bookId);

			} else if (command != null && command.equals("ADD_FROM_INDEX")) {
				bookId = Integer.parseInt(request.getParameter("bookId"));
				BookService bookService = new BookService();
				Book book = bookService.getBookDetails(bookId);
				HttpSession session = request.getSession();
				Map<Integer, Book> cart = (Map<Integer, Book>) session.getAttribute("cart");

				if (cart == null) {
					cart = new HashMap<Integer, Book>();
				}

				cart.put(book.getIsbn(), book);
				session.setAttribute("cart", cart);
				request.setAttribute("book", book);

				response.sendRedirect("home");
			}

			else if (command != null && command.equals("ADD_FROM_LIST")) {
				bookId = Integer.parseInt(request.getParameter("bookId"));
				BookService bookService = new BookService();
				Book book = bookService.getBookDetails(bookId);
				HttpSession session = request.getSession();
				Map<Integer, Book> cart = (Map<Integer, Book>) session.getAttribute("cart");

				if (cart == null) {
					cart = new HashMap<Integer, Book>();
				}

				cart.put(book.getIsbn(), book);
				session.setAttribute("cart", cart);
				request.setAttribute("book", book);

				response.sendRedirect("home");
			} else if (command != null && command.equals("REMOVE_CART")) {
				bookId = Integer.parseInt(request.getParameter("bookId"));
				HttpSession session = request.getSession();
				Map<Integer, Book> cart = (Map<Integer, Book>) session.getAttribute("cart");
				cart.remove(bookId);

				response.sendRedirect("home");

			}

			else if (command != null && command.equals("VIEW_CART")) {
				HttpSession session = request.getSession();
				Map<Integer, Book> cart = (Map<Integer, Book>) session.getAttribute("cart");
				double totalPrice = 0;

				for (Book book : cart.values()) {
					totalPrice = totalPrice + book.getPrice();
				}
				RequestDispatcher rd = request.getRequestDispatcher("cart.jsp");
				request.setAttribute("genreList", genreList);
				request.setAttribute("totalPrice", totalPrice);
				rd.forward(request, response);
			}

			else if (command != null && command.equals("CHECKOUT")) {
				HttpSession session = request.getSession();
				Map<Integer, Book> cart = (Map<Integer, Book>) session.getAttribute("cart");

				// create order - return orderId

				int customerId = (int) session.getAttribute("customerId");
				double finalTotalPrice = Double.parseDouble(request.getParameter("totalPrice"));
				// int amount = Integer.parseInt(request.getParameter("amount"));
				// double orderedPrice =
				// Double.parseDouble(request.getParameter("ordered{roce"));

				Order order = new Order(customerId, finalTotalPrice);
				System.out.println("Total price: " + finalTotalPrice);
				OrderService orderService = new OrderService();
				Integer orderId = orderService.addOrder(order);

				session.setAttribute("orderId", orderId);

				
				// create orderDetails
				for (Book book : cart.values()) {
					int amount = Integer.parseInt(request.getParameter("amount"));
					double orderedPrice = Double.parseDouble(request.getParameter("orderedPrice"));

					OrderDetails orderDetails = new OrderDetails(orderId, book.getIsbn(), amount, orderedPrice);
					OrderDetailsService orderDetailsService = new OrderDetailsService();
					orderDetailsService.addOrderDetails(orderDetails);
				}

				session.removeAttribute("cart");
				response.sendRedirect("home");
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
