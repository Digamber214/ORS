package in.co.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Forget Password functionality Controller. Performs operation for Forget
 * Password
 */
@WebServlet(name="ForgetPasswordCtl",urlPatterns={"/ForgetPasswordCtl"})
public class ForgetPasswordCtl extends BaseCtl {
	/** The log. */
	private static Logger log= Logger.getLogger(ForgetPasswordCtl.class);
	
	
	protected boolean validate(HttpServletRequest request){
		log.debug("ForgetPasswordCtl Method validate Begin");

		
		boolean pass=true;
		
		String login = request.getParameter("login");
		
		if(DataValidator.isNull(login)){
			
			request.setAttribute("login",PropertyReader.getValue("error.require","Emailid"));
            pass=false;
		}
		else if(!DataValidator.isEmail(login)){
			request.setAttribute("login",PropertyReader.getValue("error.email","Invalid"));
			pass=false;
			
		}
		
		log.debug("ForgetPasswordCtl Method validate Ended");

		return pass;
	}
	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request){
		log.debug("ForgetPasswordCtl Method populatebean Begin");
		
		UserBean bean = new UserBean();
		
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		
		log.debug("ForgetPasswordCtl Method populatebean Ended");
		return bean;
	}
	
	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
		log.debug("");
		
		ServletUtility.forward(getView(),request,response);
		
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("inside do post");
		
		log.debug("ForgetPasswordCtl Method doPost begin");
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		System.out.println("operation value of dopost ForgetPasswordCtl"+op);
		
		if(OP_GO.equalsIgnoreCase(op)){
			
			UserBean bean  = (UserBean) populateBean(request);
			
			UserModel model = new UserModel();
			
			boolean b=true;
			try{			
				
				model.forgetPassword(bean.getLogin());
				
				ServletUtility.setSuccessMessage("Password has been sent to your email id.",request);
			}
			catch(RecordNotFoundException e){
				ServletUtility.setErrorMessage(e.getMessage(), request);
                log.error(e);
				
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
			}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			System.out.println("reset performed");
			ServletUtility.redirect(ORSView.FORGET_PASSWORD_CTL, request, response);
			// Not to use forward to perform reset
			//ServletUtility.forward(getView(), request, response);
			return;
		}
		
		log.debug("ForgetPasswordCtl Method doPost Ended");
	}


	@Override
	protected String getView() {
		
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
