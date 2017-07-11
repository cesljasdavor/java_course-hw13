package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.hw13.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * pozivom metode {@link #doGet(HttpServletRequest, HttpServletResponse)} šalju
 * kao odgovor na zahtjev sliku formata "png". Na slici se prikazuju podaci o
 * tome koji su operacijski sustav najzastupljeniji prema nekom istraživanju.
 * Metoda za stvaranje slike koristi metodu
 * {@link ServletUtil#createPieChart(String, PieDataset)} kojoj predaje naziv i
 * podatke koji su modelirani sučeljem {@link PieDataset}
 * 
 * @see HttpServlet
 * @see ServletUtil
 * @see PieDataset
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "reportImage", urlPatterns = { "/reportImage" })
public class ReportImageServlet extends HttpServlet {
	
	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		byte[] pieChartBytes = ServletUtil.createPieChart("Resarch results", createRandomDataset());

		response.setContentType("image/png");
		response.getOutputStream().write(pieChartBytes);
	}

	/**
	 * Pomoćna metoda koja se koristi za generiranje nekih proizvoljnih podataka
	 * na temelju kojih se gradi dijagram. Podaci u oblikovani sučeljem
	 * {@link PieDataset}
	 *
	 * @return proizvoljni podaci modelirani sučeljem {@link PieDataset} koji se
	 *         koriste za generiranje dijagrama.
	 */
	private PieDataset createRandomDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		dataset.setValue("Windows 10", 23);
		dataset.setValue("Windows 8", 10);
		dataset.setValue("Windows 7", 15);
		dataset.setValue("OS X", 25);
		dataset.setValue("Ubuntu", 16);
		dataset.setValue("Linux Mint", 11);

		return dataset;
	}

}
