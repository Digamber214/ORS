package in.co.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.bean.BaseBean;
import in.co.bean.UserBean;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.ServletUtility;
/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 */
public abstract class BaseCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_RESET = "Reset";
	
	/**
     * Success message key constant
     */
	public static final String MSG_SUCCESS = "Success";

	/**
     * Error message key constant
     */
	public static final String MSG_ERROR = "Error";
	
	/**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	/**Loads the list data required at html form
	 * @param request
	 */
	
	protected void preload(HttpServletRequest request) {
        
	}

	/**
	 * @param request
	 * @return
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		
		return null;
	}

	 /**
     * Populates Generic attributes in DTO
     * 
     * @param dto
     * @param request
     * @return
     */	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;
		
		UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		if (userBean == null) {

			createdBy = "root";
			modifiedBy = "root";
		}else {

			modifiedBy = userBean.getLogin();

			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}

		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long ctd = DataUtility.getLong(request.getParameter("createdDateTime"));

		if (ctd > 0) {
			dto.setCreateDateTime(DataUtility.getTimestamp(ctd));
		} else {
			dto.setCreateDateTime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDateTime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//
		preload(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("opeartion value in basectl------->"+op);
		// convert the operation value into string

		// Check if operation is not DELETE, VIEW, CANCEL, and NULL then
        // perform input data validation

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("condition check in basectl 22");
			// Check validation, If fail then send back to page with error
            // messages
			//Polymorphism concept
			if (!validate(request)) {
				System.out.println("inside validate of base ctl"+validate(request));
				BaseBean bean = populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		super.service(request, response);
	}
	private boolean DataValidatorisNotNull(String op){
		return false;
	}
	
	/**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	protected abstract String getView();

}
