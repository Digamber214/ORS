package in.co.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.MarksheetBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.model.MarksheetModel;
import in.co.model.StudentModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 */

@WebServlet(name = "MarksheetCtl", urlPatterns = "/ctl/MarksheetCtl")

public class MarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	protected void preload(HttpServletRequest request) {

		StudentModel model = new StudentModel();

		try {
			List list = model.list();
			request.setAttribute("StudentList", list);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollno"))) {
			request.setAttribute("rollno", PropertyReader.getValue("error.require", "RollNo"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollno"))) {
			request.setAttribute("rollno", "Invalid Roll No (Enter Roll no in format as  Ex. 01cs01)");
			pass = false;
		}

		/*
		 * else if(!DataValidator.isInteger(request.getParameter("rollno")))
		 * 
		 * { request.setAttribute("rollno", "Enter Roll No Only"); pass=false; }
		 */

		if (DataValidator.isNull(request.getParameter("studentid"))) {
			request.setAttribute("studentid1", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
			System.out.println("pass in student" + pass);
		}

		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics1", PropertyReader.getValue("error.require", "Physics Marks"));
			pass = false;
			System.out.println("pass in physics" + pass);
		}

		else if (DataUtility.getInt(request.getParameter("physics")) > 100) {
			request.setAttribute("physics1", "Marks Can Not Be More Then 100");
			pass = false;
		}

		else if (DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics1", "Marks Can Not Be Negative");
			pass = false;
		}

		else if (!DataValidator.isInteger(request.getParameter("physics"))
				&& DataValidator.isNotNull(request.getParameter("physics"))) {
			request.setAttribute("physics1", "Enter Number Only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Marks"));
			pass = false;

		}

		else if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
			request.setAttribute("chemistry", "Marks Can Not Be More Then 100");
			pass = false;
		}

		else if (DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "Marks Can Not Be Negative");
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("chemistry"))
				&& DataValidator.isNotNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", "Enter Number Only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("math"))) {
			request.setAttribute("math", PropertyReader.getValue("error.require", "Maths Marks"));
			pass = false;

		}

		else if (DataUtility.getInt(request.getParameter("math")) > 100) {
			request.setAttribute("math", "Marks Can Not Be More Then 100");
			pass = false;
		}

		else if (DataUtility.getInt(request.getParameter("math")) < 0) {
			request.setAttribute("math", "Marks Can not be negative");
			pass = false;
		}

		else if (!DataValidator.isInteger(request.getParameter("math"))
				&& DataValidator.isNotNull(request.getParameter("math"))) {
			request.setAttribute("math", "Enter Number Only");
			pass = false;
		}
		return pass;
	}

	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollno")));
		bean.setStudentId(DataUtility.getLong(request.getParameter("studentid")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("math")));
		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logic
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside do get");

		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("do Get  id : " + id);
		System.out.println("do Get  op : " + op);

		MarksheetModel model = new MarksheetModel();
		if (id > 0) {
			MarksheetBean bean;
			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
		return;
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside dopost");

		log.debug("");

		MarksheetModel model = new MarksheetModel();

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			MarksheetBean bean = (MarksheetBean) populateBean(request);
			System.out.println("roll no " + bean.getRollNo());

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Marksheet Added Successfully", request);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.setErrorMessage("Roll Nunmber Already Exist", request);
				ServletUtility.setBean(bean, request);
			}

			ServletUtility.forward(getView(), request, response);
		}

		else if (op.equals("Update")) {

			MarksheetBean bean1 = (MarksheetBean) populateBean(request);

			long id = DataUtility.getLong(request.getParameter("id"));

			System.out.println("id in update" + id);
			if (id > 0) {
				try {
					MarksheetModel model1 = new MarksheetModel();
					model1.update(bean1);
					ServletUtility.setSuccessMessage("Marksheet Updated Successfully", request);
				} catch (Exception e) {
					e.printStackTrace();
					ServletUtility.setErrorMessage("Record Already Exist", request);
				}
			}
			ServletUtility.setBean(bean1, request);
			ServletUtility.forward(getView(), request, response);
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		}

	}

	@Override
	protected String getView() {
		
		return ORSView.MARKSHEET_VIEW;
	}

}
