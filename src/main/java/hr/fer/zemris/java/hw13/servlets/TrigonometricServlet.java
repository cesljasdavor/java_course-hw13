package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.util.ParamsUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * pozivom metode {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * dohvaćaju dvije vrijednosti parametara, a koje moraju biti pod ključevima "a"
 * i "b", gdje bi pod parametrom s ključem "a" trebala biti manja vrijednosti od
 * one s ključem "b". Ukoliko to nije slučaj zamjenjuju se vrijednosti. Ukoliko
 * jedan (ili oba) parametra nisu predani ili su predane vrijednosti koje se ne
 * mogu parsirati u cijeli broj za njihove vrijednosti se postavljaju
 * {@link #DEFAULT_A} i {@link #DEFAULT_B} (vidi metodu
 * {@link ParamsUtil#getOrDefault(HttpServletRequest, String, int)}). Nakon što
 * su svi parametri prisutni stvaraju se dvije {@link List}e sa sinus
 * vrijednostima i kosinus vrijednostima svih brojeva unutar raspona. Potom se
 * ostatak posla predaje JSP dokumentu "trigonometric.jsp" koji obavlja
 * stvaranje HTML dokumenta.
 * 
 * @see HttpServlet
 * @see ParamsUtil
 * @see List
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "trigonometric", urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstanta koja predstavlja granicu koju razlika parametara pod ključevima
	 * "b" i "a" ne smije prijeći
	 */
	private static final int TRESHOLD = 720;

	/**
	 * Konstanta koja predstavlja pretpostavljenu vrijednost parametra pod
	 * ključem "a"
	 */
	private static final int DEFAULT_A = 0;

	/**
	 * Konstanta koja predstavlja pretpostavljenu vrijednost parametra pod
	 * ključem "b"
	 */
	private static final int DEFAULT_B = 360;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int a = ParamsUtil.getOrDefault(request, "a", DEFAULT_A);
		int b = ParamsUtil.getOrDefault(request, "b", DEFAULT_B);

		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}

		b = (b > a + TRESHOLD) ? (a + TRESHOLD) : b;

		List<Double> sinusValues = new ArrayList<>();
		List<Double> cosinusValues = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			double radians = Math.toRadians(i);

			sinusValues.add(Math.sin(radians));
			cosinusValues.add(Math.cos(radians));
		}

		request.setAttribute("sinusValues", sinusValues);
		request.setAttribute("cosinusValues", cosinusValues);
		request.setAttribute("a", a);
		request.setAttribute("b", b);

		request.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(request, response);
	}

}
