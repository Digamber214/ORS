package in.co.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.CourseBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.model.CourseModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * Course functionality Controller. Performs operation for add, update, delete
 * and get Course
 * 
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(CourseCtl.class);

	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		System.out.println("populate");

		CourseBean bean = new CourseBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setcName(DataUtility.getString(request.getParameter("name")));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDescription(DataUtility.getString(request.getParameter("desc")));
		populateDTO(bean, request);
		return bean;

	}

	protected boolean validate(HttpServletRequest request) {

		System.out.println("validation");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name1", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		else if (!DataValidator.isName1(request.getParameter("name"))) {
			request.setAttribute("name1", "Invalid Course Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration1", PropertyReader.getValue("error.require", "Course Duration"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("desc"))) {
			request.setAttribute("desc1", PropertyReader.getValue("error.require", "Course Description"));
			pass = false;
		}

		else if (!DataValidator.isName1(request.getParameter("desc"))) {
			request.setAttribute("desc1", "Invalid Description");
			pass = false;
		}

		System.out.println("pass=" + pass);
		return pass;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside do get");

		long id = DataUtility.getLong(request.getParameter("id"));

		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("id=" + id + "  " + "op=" + op);
		CourseBean bean = null;
		CourseModel model = new CourseModel();

		if (id > 0 || op != null) {

			try {

				bean = model.findByPk(id);

			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("no record exist", request);
			}

		}
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside do post");

		long id = DataUtility.getLong(request.getParameter("id"));

		String op = DataUtility.getString(request.getParameter("operation"));

		CourseModel model = new CourseModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CourseBean bean;
			bean = (CourseBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Course Added Successfully", request);
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.setErrorMessage("Course Already Exist", request);
				ServletUtility.setBean(bean, request);

				}catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

			ServletUtility.forward(getView(), request, response);

		}

		else if (op.equals("Update")) {
			CourseBean bean1;
			bean1 = (CourseBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean1);
					ServletUtility.setSuccessMessage("Course Updated Successfully", request);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			ServletUtility.setBean(bean1, request);
			ServletUtility.forward(getView(), request, response);
		}

		else if (op.equals("Reset")) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);

		}
	}

	@Override
	protected String getView() {
	
		return ORSView.COURSE_VIEW;
	}

}
