package in.co.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.MarksheetBean;
import in.co.exception.ApplicationException;
import in.co.model.MarksheetModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet.
 */

@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {

	/** The log. */
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);

	protected boolean validate(HttpServletRequest request) {
		log.debug("GetMarksheetCTL Method validate Ended");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollno1", PropertyReader.getValue("error.require", "Roll No"));
			pass = false;
		}
		/*else if(!DataValidator.isRollNo(request.getParameter("rollNo"))){
			request.setAttribute("rollno1","enter Valid roll No");
			pass=false;
		}*/
		/*else if(!DataValidator.isInteger(request.getParameter("rollNo")))
		 {
			request.setAttribute("rollno1", "Enter Number Only");
			pass=false;	 
		 }
*/
		
		log.debug("GetMarksheetCTL Method validate Ended");
		return pass;
	}

	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("GetMarksheetCtl Method populatebean Begin");
		MarksheetBean bean = new MarksheetBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));

		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

		log.debug("GetMarksheetCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside dopost method");

		log.debug("MarksheetCtl Method doGet Begin");

		MarksheetModel model = new MarksheetModel();

		MarksheetBean bean = (MarksheetBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("operation in " + op);
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_GO.equalsIgnoreCase(op)) {

			try {
				bean = model.findByRollNo(bean.getRollNo());
				System.out.println("bean value " + bean.getRollNo() + " " + bean.getName());

				if (bean.getRollNo() != null) {
					 System.out.println("if part of roll no");
					ServletUtility.setBean(bean, request);
					//ServletUtility.setSuccessMessage("found marksheet", request);
					ServletUtility.forward(getView(), request, response);
				}

				else {
					// System.out.println("else part");
					ServletUtility.setErrorMessage("RollNo Does Not exists", request);
					ServletUtility.forward(getView(), request, response);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

			
		}

		else if(OP_RESET.equalsIgnoreCase(op)){
			System.out.println("reset per");
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
			return;
		}
		
		log.debug("MarksheetCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
