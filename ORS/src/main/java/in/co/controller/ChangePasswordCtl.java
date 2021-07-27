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
import in.co.exception.RecordNotFoundException;
import in.co.model.UserModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * Change Password functionality Controller. Performs operation for Change
 * Password
 */
@WebServlet(name="ChangePasswordCtl",urlPatterns={"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl {
	
	 public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	    private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	
	
	protected boolean validate(HttpServletRequest request){
		
		log.debug("ChangePasswordCtl Method validate Begin");
		boolean pass= true;
		
		String op= DataUtility.getString(request.getParameter("operation"));
		
		String pass1= request.getParameter("newPassword");
		
		if(OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)){
			System.out.println("pass");
			return pass;
			
		}
		
		if(DataValidator.isNull(request.getParameter("oldPassword"))){
			request.setAttribute("oldpassword",PropertyReader.getValue("error.require", "Old Password"));
			pass=false;
			System.out.println("pass in old pass"+pass);
		}
		
		else if(!DataValidator.isPassword(pass1)){
	       	 request.setAttribute("oldpassword",PropertyReader.getValue("error.password","Invalid"));
	            pass= false;
	            System.out.println("pass in new pass1"+pass);
	        }
		
		if(DataValidator.isNull(request.getParameter("newPassword"))){
			request.setAttribute("newpassword",PropertyReader.getValue("error.require", "New Password"));
			pass=false;
			System.out.println("pass in new  pass"+pass);
		}
		
		else if(!DataValidator.isPassword(pass1)){
       	 request.setAttribute("newpassword",PropertyReader.getValue("error.password","Invalid"));
            pass= false;
            System.out.println("pass in new pass1"+pass);
        }
		
		if(DataValidator.isNull(request.getParameter("confirmPassword"))){
			request.setAttribute("confirmpassword",PropertyReader.getValue("error.require", "Confirm Password"));
			pass=false;
			System.out.println("pass in confirm pass"+pass);
		}
		
		if(!request.getParameter("newPassword").equals
				(request.getParameter("confirmPassword"))&&!"".equals(request.getParameter("confirmPassword"))){
			
			request.setAttribute("confirmpassword","New and confirm passwords should be same");
			//ServletUtility.setErrorMessage("New and confirm passwords should be same", request);
			pass=false;
		}
		
		log.debug("ChangePasswordCtl Method validate Ended");
		return pass;
	}
	    
	@Override
    protected BaseBean populateBean(HttpServletRequest request) {
		
        log.debug("ChangePasswordCtl Method populatebean Started");

        UserBean bean = new UserBean();

        bean.setPassword(DataUtility.getString(request
                .getParameter("oldPassword")));

        bean.setConfirmPassword(DataUtility.getString(request
                .getParameter("confirmPassword")));

        populateDTO(bean,request);

        log.debug("ChangePasswordCtl Method populatebean Ended");

        return bean;
    }
	
	 /**
     * Display Logics inside this method
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
		ServletUtility.forward(getView(), request, response);

	}

	 /**
     * Submit logic inside it
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("ChangePasswordCtl Method doPost Begin");
		
		
		System.out.println("do post method");
		HttpSession session = request.getSession(true);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		System.out.println("operation"+op);
		
		UserModel model = new UserModel();
		
		UserBean Userbean = (UserBean) session.getAttribute("user");
		
		UserBean bean = (UserBean) populateBean(request);
		                    
		long id = Userbean.getId();
		
		System.out.println("id="+id);
		
		String newPassword= request.getParameter("newPassword");
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			try{
				System.out.println("yes save");
				boolean flag=true;
				flag=model.changePassword(id,bean.getPassword(), newPassword);
			
				System.out.println(flag);	
				if(flag==true){
		             System.out.println(flag);			
					//bean = model.findByLogin(Userbean.getLogin());
					//session.setAttribute("user",bean);
					//ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password Changed Successfully.",request);
					ServletUtility.forward(getView(), request, response);
					return;
				}
			}
			
			catch(ApplicationException e){
				e.printStackTrace();
				log.error(e.getMessage());
				ServletUtility.handleException(e,request, response);
			} catch (RecordNotFoundException e) {
				
				e.printStackTrace();
				
				ServletUtility.setErrorMessage("Old Password is invalid", request);
			}
		}
			else if(OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)){
				System.out.println("my profile view");
				ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			    return;
			}
		
		  ServletUtility.forward(getView(), request, response);
			log.debug("ChangePasswordCtl Method doPost Ended");
		}

	@Override
	protected String getView() {
		
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
