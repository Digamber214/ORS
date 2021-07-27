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
import in.co.bean.SubjectBean;
import in.co.model.CourseModel;
import in.co.model.SubjectModel;
import in.co.util.DataUtility;
import in.co.util.DataValidator;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * * Subject functionality Controller. Performs operation for add, update and get
 * Subject
 */

@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(SubjectCtl.class);

	protected void preload(HttpServletRequest request) {

		log.debug("");
		System.out.println("preloaded");
		CourseModel model = new CourseModel();

		List list;

		try {
			list = model.list();
			request.setAttribute("courseList", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {

		log.debug("");
		System.out.println("validate");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseid"))) {
			request.setAttribute("coursename1", PropertyReader.getValue("error.require", "Couse Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectname"))) {
			request.setAttribute("subjectname1", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		else if(!DataValidator.isName1(request.getParameter("subjectname"))){
			request.setAttribute("subjectname1","Invalid Subject Name");
			pass=false;
		}
		if (DataValidator.isNull(request.getParameter("desc"))) {
			request.setAttribute("desc1", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		else if(!DataValidator.isName1(request.getParameter("desc"))){
			request.setAttribute("desc1","Invalid Description");
			pass=false;
		}

		System.out.println("pass=" + pass);
		return pass;

	}
	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
         
		log.debug("");
		System.out.println("populateBean");
		
		SubjectBean bean = new SubjectBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseId(DataUtility.getInt(request.getParameter("courseid")));
		//bean.setCourseName(DataUtility.getString(request.getParameter("coursename")));
		System.out.println("course id "+request.getParameter("courseid"));
		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectname")));
		bean.setDescription(DataUtility.getString(request.getParameter("desc")));
		populateDTO(bean, request);
		return bean;
		
	}
	
	
	/**
	 * Contains display logic
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("");
		System.out.println("inside do get");
		
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
			
		SubjectModel model = new SubjectModel();
		SubjectBean bean ;
		if(id>0){
			
			try {
				bean= model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		 ServletUtility.forward(getView(), request, response);
		
	}
	
	
	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("");
		System.out.println("inside do get");
		
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
			
		SubjectModel model = new SubjectModel();
		
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			SubjectBean bean1 = (SubjectBean)populateBean(request);
			  id=0;
			try{
				 model.add(bean1);
				ServletUtility.setSuccessMessage("Subject Added Successfully", request);
				
			}
			catch(Exception e){
				e.printStackTrace();
				ServletUtility.setErrorMessage("Subject Name Already Exist", request);
				ServletUtility.setBean(bean1, request);
			}
			ServletUtility.forward(getView(), request, response);
			return;
		}
		
		else if(op.equals("Update")){
			
			SubjectBean bean2=(SubjectBean)populateBean(request);
              //SubjectBean bean2 = new SubjectBean();
			try {
				model.update(bean2);
				ServletUtility.setBean(bean2, request);
				System.out.println("id value is"+bean2.getId());
				ServletUtility.setSuccessMessage("Subject Updated Successfully", request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				ServletUtility.forward(getView(), request, response);
			return;
		}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			System.out.println("reset working");
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		
		else if(OP_CANCEL.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
		
		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_VIEW;
	}

}
