package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.hw13.util.BandInfo;
import hr.fer.zemris.java.hw13.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * pozivom metode {@link #doGet(HttpServletRequest, HttpServletResponse)} šalju
 * sliku formata "png" koja predstavlja kružni dijagram glasanja za bendove. Za
 * generiranje slike koristi se metoda
 * {@link ServletUtil#createPieChart(String, PieDataset)}.
 * 
 * @see ServletUtil
 * @see PieDataset
 * @see HttpServlet
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "voting-graphics", urlPatterns = { "/glasanje-grafika", "/voting-graphics" })
public class VotingGraphicsServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PieDataset dataset = loadDataSet(request.getServletContext());

		response.setContentType("image/png");
		response.getOutputStream().write(ServletUtil.createPieChart("Band votes chart", dataset));
	}

	/**
	 * Pomoćna metoda koja se koristi za učitavanje podataka iz kojih se
	 * generira slika koja predstavlja kružni dijagram.
	 *
	 * @param context
	 *            primjerak razreda koji implementira sučelje
	 *            {@link ServletContext}, a koji modelira kontekst izvođenja ove
	 *            aplikacije. Unutar ove metode služi za dohvat apsolutne
	 *            putanje do datoteke iz koje se čita
	 * 
	 * @return primjerak razreda koje implementira sučelje {@link PieDataset}, a
	 *         koji modelira podatke korištene u izradi kružnog dijagrama
	 */
	private PieDataset loadDataSet(ServletContext context) {
		List<BandInfo> bandInfos = ServletUtil.loadDefaultBandInfos(context);

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (BandInfo bandInfo : bandInfos) {
			dataset.setValue(bandInfo.getName(), bandInfo.getVotes());
		}

		return dataset;
	}
}
