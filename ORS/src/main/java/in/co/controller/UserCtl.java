package in.co.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.UserBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.model.RoleModel;
import in.co.model.UserModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * * User functionality Controller. Performs operation for add, update and get
 * User
 */

@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UserCtl.class);

	protected void preload(HttpServletRequest request) {
		System.out.println("preload");
		RoleModel model = new RoleModel();

		try {
			List list = model.list();
			request.setAttribute("roll_list", list);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("");
		System.out.println("validate method");

		boolean pass = true;

		long id = DataUtility.getInt(request.getParameter("id"));

		System.out.println("id =" + id);

		
			if (DataValidator.isNull(request.getParameter("firstName"))) {
				request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
				pass = false;
			} else if (!DataValidator.isName(request.getParameter("firstName"))) {
				request.setAttribute("firstName", "Invalid First Name");
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("lastName"))) {
				request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
				pass = false;
			} else if (!DataValidator.isName(request.getParameter("lastName"))) {
				request.setAttribute("lastName", "Invalid Last Name");
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("login"))) {
				request.setAttribute("login", PropertyReader.getValue("error.require", "Emailid"));
				pass = false;
			} else if (!DataValidator.isEmail(request.getParameter("login"))) {
				request.setAttribute("login", PropertyReader.getValue("error.email", "Invalid"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
				pass = false;

			}

			else if (!DataValidator.isPassword(request.getParameter("password"))) {

				request.setAttribute("password", PropertyReader.getValue("error.password", "Invalid"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
				pass = false;

			}

			if(DataValidator.isNull(request.getParameter("address"))){
				request.setAttribute("Address",PropertyReader.getValue("error.require","Address"));
				pass=false;
			}
			else if (!DataValidator.isAddress(request.getParameter("address"))) {
				request.setAttribute("Address", "Invalid Address");
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("gender"))) {
				request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
				pass = false;

			}
			if (DataValidator.isNull(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.require", "DOB"));
				pass = false;
			} else if (!DataValidator.isDate(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.date", "DOB"));
				pass = false;
			}

			else if (!DataValidator.isValidAge(request.getParameter("dob"))) {
				System.out.println("..........................");
				request.setAttribute("dob", PropertyReader.getValue("error.date", "DOB"));
				pass = false;
				System.out.println("pass of dob " + pass);
			}
			if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
					&& !"".equals(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", "Confirm  Password  & Password Should  Be Matched");
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("mobile"))) {
				request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
				pass = false;

			} else if (!DataValidator.isMobileNo(request.getParameter("mobile"))) {
				request.setAttribute("mobile", PropertyReader.getValue("error.mobile", "Invalid"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("roleId"))) {
				request.setAttribute("roll", PropertyReader.getValue("error.require", "Roll Name"));
				pass = false;
			}

		
		log.debug("UserCtl Method validate Ended");

		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("UserCtl Method populatebean begin");

		long id = DataUtility.getInt(request.getParameter("id"));

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
        bean.setAddress(DataUtility.getString(request.getParameter("address")));
        
		System.out.println("confirm password=" + (request.getParameter("confirmPassword")));

		bean.setRollName(DataUtility.getString(request.getParameter("roleName")));
		System.out.println("role name is" + request.getParameter("roleName"));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		// bean.setDob(DataUtility.getString(request.getParameter("dob")));

		String role = request.getParameter("roleId");
		String trim = role.trim();
		bean.setRollId(DataUtility.getLong(trim));
		// System.out.println("role id --------->"+bean.getRollId()+"@");
		System.out.println("role id is" + request.getParameter("roleId") + "@");

		bean.setRollName(DataUtility.getString(request.getParameter("roleName")));
		System.out.println("role name is" + request.getParameter("roleName"));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		populateDTO(bean, request);

		log.debug("UserCtl Method populatebean Ended");

		return bean;

	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("UserCtl Method doGet Started");

		log.debug("UserCtl Method doGet Started");

		long id = DataUtility.getLong(request.getParameter("id"));

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		UserBean bean = null;

		if (id > 0 || op != null) {

			try {

				bean = model.findByPK(id);

			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}
			ServletUtility.setBean(bean, request);
		}

		ServletUtility.forward(getView(), request, response);
	}
	
	/**
	 * Contains Submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside dopost");

		log.debug("");

		UserBean bean = (UserBean) populateBean(request);

		UserModel model = new UserModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("User Added Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {

				e.printStackTrace();
				ServletUtility.setErrorMessage("User Already Exist", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}

		}

		else if (op.equals("Update")) {
			UserModel model1 = new UserModel();
			UserBean bean1 = (UserBean) populateBean(request);

			System.out.println("password=" + bean1.getPassword() + "roll name=" + bean1.getRollName());

			try {

				System.out.println("id is" + bean1.getId());

				model.update(bean1);
				ServletUtility.setSuccessMessage("User Updated Successfully", request);

			} catch (Exception e) {
				e.printStackTrace();
			}
			ServletUtility.setBean(bean1, request);
			ServletUtility.forward(getView(), request, response);
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("inside reset");
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_VIEW;
	}

}
