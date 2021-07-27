package in.co.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.util.ServletUtility;
/**
 * Error functionality Controller. 
 *  get ErrorView
 * 
 */
@WebServlet(name="ErrorCtl", urlPatterns="/ErrorCtl")
public class ErrorCtl extends BaseCtl {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
	/**
	 * Contains display logic
	 */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);
		
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		
		return ORSView.ERROR_VIEW;
	}

}
