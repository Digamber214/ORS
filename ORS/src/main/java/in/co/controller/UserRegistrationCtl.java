package in.co.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.RoleBean;
import in.co.bean.UserBean;
import in.co.exception.ApplicationException;
import in.co.model.UserModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * * UserRegistration functionality Controller. Performs operation for add, update and get
 * Subject
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public static final String OP_SIGN_UP = "SignUp";

	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("UserRegistrationCtl Method validate Started");

		System.out.println("inside validate method of user Reg");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("firstName"))){
			request.setAttribute("firstName","Invalid First Name");
			pass=false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("firstName"))){
			request.setAttribute("lastName","Invalid Last Name");
			pass=false;
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
		
		else if(!DataValidator.isPassword(request.getParameter("password"))){
			
			request.setAttribute("password",PropertyReader.getValue("error.password","Invalid"));
		    pass=false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;

		}
		
		if(DataValidator.isNull(request.getParameter("address"))){
			request.setAttribute("address1",PropertyReader.getValue("error.require","Address"));
			pass=false;
		}
		
		else if(!DataValidator.isAddress(request.getParameter("address"))){
			request.setAttribute("address1","Invalid Address");
			pass=false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob2", PropertyReader.getValue("error.require", "DOB"));
			pass = false;
		} /*else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "DOB"));
			pass = false;
		}*/

		else if(!DataValidator.isValidAge(request.getParameter("dob"))){
			System.out.println("..........................");
			request.setAttribute("dob2", PropertyReader.getValue("error.date","DOB"));
			pass=false;
			System.out.println("pass of dob "+pass);
		}
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword","Password & Confirm  Password  should be matched");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;

		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile",PropertyReader.getValue("error.mobile","Invalid"));
		    pass=false;
		}

		log.debug("UserRegistrationCtl Method validate Ended");
		System.out.println("validate method of user Reg ended");
		return pass;
	}
	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside doget method of user Reg");
		log.debug("");

		ServletUtility.forward(getView(), request, response);
		System.out.println("doget method of user Reg ended");
	}
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("UserRegistrationCtl Method populatebean begin");

		
		
		UserBean bean = new UserBean();
System.out.println("populate bean");
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollId(RoleBean.STUDENT); // by default role is student
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));       	       
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
    System.out.println(request.getParameter("dob"));
		//String x= request.getParameter("dob");  //modified
		//x=x.replaceAll("-","/");
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
       //  System.out.println("date is"+bean.getDob());
		
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
        populateDTO(bean, request);

		log.debug("UserRegistrationCtl Method populatebean Ended");

		System.out.println("populateBean method of user Reg ended");
		return bean;
	}
	/**
     * Contains Submit logics
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside dopost method of user Reg");

		log.debug("");

		String op = DataUtility.getString(request.getParameter("operation"));

		   System.out.println("operation is"+op);		   
		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			long id = DataUtility.getLong(request.getParameter("id"));
			
			UserModel model = new UserModel();
			UserBean bean = (UserBean)populateBean(request);
			try {
				long pk = model.registerUser(bean);
				bean.setId(pk);
				request.getSession().setAttribute("UserBean", bean);
				request.setAttribute("registration","Registration Successfully");
				ServletUtility.setSuccessMessage("Registration Successfully", request);
				//ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);
	            ServletUtility.forward(getView(), request, response);
				return;

				
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				
			}
            catch(Exception e){
           	 log.error("");
           	 ServletUtility.setBean(bean, request);
           	 ServletUtility.setErrorMessage("login  id already exist", request);
                ServletUtility.forward(getView(),request, response);
            }
					}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.forward(getView(), request, response);
           return;
		}
		
		System.out.println("populateBean method of user Reg ended");

	}
	

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
