package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.util.BandInfo;
import hr.fer.zemris.java.hw13.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * pozivom metode {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * otvaraju datoteku s opisima pojedinih bendova za koje je moguće dati glas te
 * iz njih stvaraju {@link List} primjeraka razreda {@link BandInfo}. Potom
 * generiranje HTML dokumenta deliegiraju JSP datoteci
 * "/WEB-INF/pages/votingIndex.jsp".
 * 
 * @see HttpServlet
 * @see BandInfo
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "voting", urlPatterns = { "/glasanje", "/voting" })
public class VotingServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filePath = request.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");

		List<BandInfo> bandInfos = ServletUtil.parseBandInfo(filePath);

		request.getSession().setAttribute("bandInfos", bandInfos);
		request.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(request, response);
	}
}
