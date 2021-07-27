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
import in.co.bean.CollegeBean;
import in.co.exception.ApplicationException;
import in.co.model.CollegeModel;
import in.co.util.DataUtility;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * College List functionality Controller. Performs operation for list, search
 * and delete operations of College
 */
@WebServlet(name = "CollegeListCtl", urlPatterns = "/ctl/CollegeListCtl")
public class CollegeListCtl extends BaseCtl {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(CollegeListCtl.class);
    
	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	
	protected BaseBean populateBean(HttpServletRequest request) {

		CollegeBean bean = new CollegeBean();

		bean.setName(DataUtility.getString(request.getParameter("cName")));
		bean.setCity(DataUtility.getString(request.getParameter("cCity")));
		populateDTO(bean, request);
		return bean;

	}
	
	/**
     * Contains display logic
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("CollegeListCtl doget Start");

		long id = DataUtility.getLong(request.getParameter("id"));

		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CollegeModel model = new CollegeModel();
		CollegeBean bean =new CollegeBean();

		List list = null;
		List next=null;
		try {
			list = model.search(bean, pageNo, pageSize);
            next= model.search(bean, pageNo+1, pageSize);
            
			if(list==null  || list.size()==0){
		    	  ServletUtility.setErrorMessage("no record exist", request);	
		    	}
			if(next==null || next.size()==0){
				  request.setAttribute("nextList1","0");
			  }
			  else{
				  request.setAttribute("nextList1",next.size());
			  }

			
			
		} catch (Exception e) {
			e.printStackTrace();
           ServletUtility.handleException(e, request, response);
		}
		
				
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	 /**
     * Contains Submit logics
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		  System.out.println("inside do post");
		  
		  String op = DataUtility.getString(request.getParameter("operation"));
		  
		  String ids[]= request.getParameterValues("ids"); 
		  log.debug("");
		  
		  int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		  int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		  
		  pageNo=  (pageNo==0)?1:pageNo;
		  pageSize= (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize; 
		
		  
		  if(OP_SEARCH.equalsIgnoreCase(op)){
			  pageNo=1;
		  }
		  else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			  
			     if(pageNo>1){
			    	 pageNo--;
			     }
			     else{
			    	 pageNo=1;
			    	 ServletUtility.setErrorMessage("No Previous Page Available", request);
			     }
		  }
		  
		  else if(OP_NEXT.equalsIgnoreCase(op)){
			  pageNo++;
		  }
		  
		  else if(OP_NEW.equalsIgnoreCase(op)){
			  ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
		  return;
		  }
		  
		  else if(OP_RESET.equalsIgnoreCase(op)){
			  System.out.println("reset called");
			  ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
		     return;
		  }
		  
		  else if(OP_DELETE.equalsIgnoreCase(op)){
			  
			  CollegeModel mod = new CollegeModel();
			  CollegeBean dbean = new CollegeBean();
			  if(ids!=null && ids.length>0){
			  for(String id2:ids){
			
				  int idnew= DataUtility.getInt(id2);
				  System.out.println("id new is"+idnew);
				  dbean.setId(idnew);
				  try {
					mod.delete(dbean);
					ServletUtility.setSuccessMessage("College Deleted Successfully", request);
				}  catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  }
			  else{
				  ServletUtility.setErrorMessage("Select Atleast One Record", request);
			  }
		  }
		  CollegeModel model = new CollegeModel();
		  CollegeBean bean = (CollegeBean)populateBean(request);
		  
		  List list=null;
		  List next=null;
		  
		  try{
			  list = model.search(bean, pageNo, pageSize);
			  
			  next= model.search(bean, pageNo+1, pageSize);
			  
			  System.out.println("liiiiiiiiiiii"+list.size());
			  if(list==null  || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
				  System.out.println("list"+list);
		    	  ServletUtility.setErrorMessage("No College Found", request);	
		    	}
			  
			  if(next==null || next.size()==0){
				  request.setAttribute("nextList1","0");
			  }
			  else{
				  request.setAttribute("nextList1",next.size());
			  }
				
			  ServletUtility.setList(list, request);
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  ServletUtility.handleException(e, request, response);
		  }
		  
		  
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
			log.debug("");
	}

	@Override
	protected String getView() {
	
		return ORSView.COLLEGE_LIST_VIEW;
	}

}
