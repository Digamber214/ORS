package in.co.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.UserBean;
import in.co.exception.ApplicationException;
import in.co.model.UserModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * My Profile functionality Controller. Performs operation for update your
 * Profile
 */
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {

	public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";

	/** The log. */
	private static Logger log = Logger.getLogger(MyProfileCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("MyProfileCtl Method validate Begin");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		
		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("firstName"))){
			request.setAttribute("firstName","Invalid Name");
			pass=false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}

		else if(!DataValidator.isName(request.getParameter("lastName"))){
			request.setAttribute("lastName","Invalid Name");
			pass=false;
		}
		
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile"));
			pass = false;
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile","Invalid Mobile No");
			pass=false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "DOB"));
			pass = false;
		}

		log.debug("MyProfileCtl Method validate Ended");

		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside doGet of MyProfileCtl");
		log.debug("");
		   
		    HttpSession session = request.getSession(true);
		
		    UserBean UserBean =(UserBean) session.getAttribute("user");
		    
		    System.out.println("loginid of user "+UserBean.getLogin());
		    String op = DataUtility.getString(request.getParameter("operation"));
		    
		    long id = UserBean.getId();
		    	
		   if(id>0||op!=null){
			   //UserBean bean;   
			   System.out.println("id="+id);
			   UserModel model = new UserModel();
			   try{
				   
				   UserBean=model.findByPK(id);
				   ServletUtility.setBean(UserBean, request);
				   ServletUtility.forward(getView(), request, response);
			   }
			   catch(Exception e){
				   e.printStackTrace();
				   log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
			   }
		   }
		  // ServletUtility.forward(getView(), request, response);

	        log.debug("MyProfileCtl Method doGet Ended");
	    }
	
	/**
	 * Contains Submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside doPost of MyProfileCtl");
		
		HttpSession session = request.getSession(true);

		UserBean bean = (UserBean) session.getAttribute("user");

		long id = bean.getId();

		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("operation="+op);
		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean ubean = (UserBean) populateBean(request);
			
			System.out.println("name"+ubean.getFirstName());
			
			try {
				
				if (id > 0) {

					bean.setFirstName(ubean.getFirstName());
					bean.setLastName(ubean.getLastName());
					bean.setGender(ubean.getGender());
					bean.setMobileNo(ubean.getMobileNo());
					bean.setDob(ubean.getDob());
               
					
					model.update(bean);
				}
 				ServletUtility.setBean(ubean, request);
				ServletUtility.setSuccessMessage("Profile Has Been Updated Successfully", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				
	                ServletUtility.setBean(bean, request);
	                ServletUtility.setErrorMessage("Loginid Already Exist",
	                        request);
	            
			}
			
			
		}
		
		else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request,
                    response);
            return;

        }
		ServletUtility.forward(getView(), request, response);

        log.debug("MyProfileCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
	
		return ORSView.MY_PROFILE_VIEW;
	}

}
