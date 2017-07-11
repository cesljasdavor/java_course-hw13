package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * imaju za zadaću pozivom metode
 * {@link #doGet(HttpServletRequest, HttpServletResponse)} promijeniti
 * pozadinsku boju čitave aplikacije. Metoda
 * {@link #doGet(HttpServletRequest, HttpServletResponse)} čita jedan parametar
 * sa ključem{@value #COLOR_KEY} i postavlja boju na vrijednost pod tim ključem.
 * Ukoliko taj parametar ne postoji boja će biti postavljena na
 * {@value #DEFAULT_COLOR}
 * 
 * @see HttpServlet
 * @see HttpServletResponse
 * @see HttpServletRequest
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "colorChanger", urlPatterns = { "/color-changer" })
public class ColorChangerServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstanta koja predstavlja niz znakova koji predstavljaju naziv
	 * pretpostavljene pozadinske boje
	 */
	private static final String DEFAULT_COLOR = "white";

	/**
	 * Konstanta koja predstavlja niz znaova koji predstavljaju ključ pod kojim
	 * se nalazi boja unutar parametara zahtjeva
	 */
	private static final String COLOR_KEY = "color";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String color = request.getParameter(COLOR_KEY);
		if (color == null) {
			color = DEFAULT_COLOR;
		}

		request.getSession().setAttribute("pickedBgCol", color);
		request.getRequestDispatcher("colors.jsp").forward(request, response);
	}

}
