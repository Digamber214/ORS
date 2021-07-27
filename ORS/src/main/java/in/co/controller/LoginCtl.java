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
import in.co.bean.RoleBean;
import in.co.bean.UserBean;
import in.co.exception.ApplicationException;
import in.co.model.RoleModel;
import in.co.model.UserModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;
/**
 * Login functionality Controller. Performs operation for Login
 */

@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUP";
	public static final String OP_LOG_OUT = "LogOut";
	
	/** The log. */
	private static Logger log = Logger.getLogger(LoginCtl.class);
	
	protected boolean validate(HttpServletRequest request) {
		log.debug("LoginCtl Method validate begin");
		System.out.println("validate in login ctl");
		
		boolean pass = true ;
		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
		String login = request.getParameter("login");
		String pass1 = request.getParameter("password");
		
		if (DataValidator.isNull(login)) {
			
			request.setAttribute("login", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Invalid"));
			pass = false;
		} 
		 if(DataValidator.isNull(pass1)){
				  request.setAttribute("password",PropertyReader.getValue("error.require", "password"));
				  pass =false; 
			  }
	            
		        /*added by me */
		 else if(!DataValidator.isPassword(pass1)){
	        	 request.setAttribute("password",PropertyReader.getValue("error.password","Invalid"));
	             pass= false;
	         }
		
		 
		 System.out.println("LoginCtl Method validate Ended"+pass);
		log.debug("LoginCtl Method validate Ended");
		return pass;
	}
		
	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("LoginCtl Method populatebean Begin");

		UserBean bean = new UserBean();
	
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");
		return bean;
	}
	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("model doget begin");
		HttpSession session = request.getSession();

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("opeartion value in doget loginctl" + op);
   
		if (OP_LOG_OUT.equals(op)) {
			session = request.getSession();
			ServletUtility.setSuccessMessage("Logged Out Successfully", request);
			session.invalidate();
		}
				          
		ServletUtility.forward(getView(), request, response);
		log.debug("dogetend");
	}

	 /**
     * Submitting or login action performing
     * 
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("Method doPost begin");
		
		HttpSession session = request.getSession(true);

		String op = DataUtility.getString(request.getParameter("operation"));
	
		System.out.println("operation in dopost of loginctl"+op);
		
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();


		long id = DataUtility.getLong(request.getParameter("id"));
		
		
		System.out.println("id in dopost of loginctl"+id);
		
		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean)populateBean(request);
			
			System.out.println("name is"+bean.getLogin());
			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());
				
				if (bean != null) {
					System.out.println("bean is not null");
					session.setAttribute("user", bean);
					long rollId = bean.getRollId();
					
					//ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
					RoleBean roleBean = role.findByPk(rollId);
					
					
					if (roleBean != null) {
						session.setAttribute("role", roleBean.getName());
					}
					
					
               	//code for uri
					
					String str= (String)session.getAttribute("URI");
					System.out.println("str..........."+str);
					
					if(str==null){
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} 
					else{
						
						ServletUtility.redirect(str, request, response);
						return;
					}
				
					
				}else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid Loginid & Password", request);
					ServletUtility.forward(getView(), request, response);
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
				    request.getAttribute("exception");
					ServletUtility.handleException(e, request, response);
					return;
			}
			
		} 
		
		    else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		
		
		
		//ServletUtility.forward(getView(), request, response);
		log.debug("Method doPost Ended");
	}

	
	

	@Override
	protected String getView() {
	
		return ORSView.LOGIN_VIEW;
	}
	

}
