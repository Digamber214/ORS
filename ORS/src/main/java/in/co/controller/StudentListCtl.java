package in.co.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.StudentBean;
import in.co.exception.ApplicationException;
import in.co.model.StudentModel;
import in.co.util.DataUtility;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 */

@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(StudentListCtl.class);

	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		StudentBean bean = new StudentBean();
		
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("inside do get");
		
		int pageNo = 1;
		int pageSize= DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		StudentModel model = new StudentModel();
		
		StudentBean bean = (StudentBean)populateBean(request);
		
		List<StudentBean> list = new ArrayList();
		
		
		try{
			
			list=model.search(bean, pageNo, pageSize);
		    
			if(list==null || list.size()==0){
				
				ServletUtility.setErrorMessage("no record found", request);
			}
			
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Contains Submit logics
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
     
		String op= DataUtility.getString(request.getParameter("operation"));
		
		String[] ids= request.getParameterValues("ids");
		
		int pageNo= DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize= DataUtility.getInt(request.getParameter("pageSize"));
		
		
		pageNo= (pageNo==0)?1:pageNo;
		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			pageNo=1;
		}
		else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			pageNo--;   
			/*if(pageNo>1){
				   pageNo--;
			   }
			   else{
				   pageNo=1;
			   }*/
		}
		else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
		}
		
		else if(OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
		    return;
		}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
		    return;
		}
		
		else if(OP_BACK.equalsIgnoreCase(op)){
			System.out.println("inside back");
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		}
		else if(OP_DELETE.equalsIgnoreCase(op)){
			
			StudentModel mod = new StudentModel();
			StudentBean dbean= new StudentBean();
			System.out.println("ids val"+ids);
			if(ids!=null && ids.length>0){
				
				for(String id2:ids){
					
				int	idnew = DataUtility.getInt(id2);
				System.out.println("id new val"+idnew);
				dbean.setId(idnew);
				
				try {
					mod.delete(dbean);
					ServletUtility.setSuccessMessage("Student Deleted Successfully", request);
				    
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			else{
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}
		}
		
		StudentModel model = new StudentModel();
		StudentBean bean = (StudentBean)populateBean(request);
		List list = new ArrayList();
	
		System.out.println("list before========"+list);
		try{
			list = model.search(bean, pageNo, pageSize);
			System.out.println("list After========"+list);
			
			if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
				System.out.println("Student list..............");
				ServletUtility.setErrorMessage("No Student Found", request);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
         	
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_LIST_VIEW;
	}

}
