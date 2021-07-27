package in.co.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.BaseBean;
import in.co.bean.MarksheetBean;
import in.co.exception.ApplicationException;
import in.co.model.MarksheetModel;
import in.co.util.DataUtility;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * Marksheet List functionality Controller. Performs operation for list, search
 * and delete operations of Marksheet
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */

/**
 * Servlet implementation class MarksheetlistCtl
 */

@WebServlet(name="MarksheetListCtl",urlPatterns={"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	/** The log. */
	private static Logger log = Logger.getLogger(MarksheetListCtl.class);
    
	/**
	 * @see in.co.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
    protected BaseBean populateBean(HttpServletRequest request){
    	
    	MarksheetBean bean = new MarksheetBean();
    	
    	String name = DataUtility.getString(request.getParameter("name"));
    	String roll = DataUtility.getString(request.getParameter("rollNo"));
    	
    	/*bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));*/ 
    	bean.setName(name);
    	bean.setRollNo(roll);
    	populateDTO(bean, request);
    	
    	return bean;
    }
    
    /**
	 * Contains display logic
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("inside do get");
		log.debug("");
		
		MarksheetModel model = new MarksheetModel();
		
		MarksheetBean bean =  (MarksheetBean) populateBean(request);
		
		List<MarksheetBean> list = new ArrayList<MarksheetBean>();
		
		int pageNo=1;
		int pageSize= DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		try{
			
			list = model.search(bean, pageNo, pageSize);
			
			if(list==null || list.size()==0){
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ServletUtility.forward(getView(), request, response);
	}


     /**
	 * Contains Submit logics
	 */
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			System.out.println("inside do post");
			log.debug("");
			
			String op = DataUtility.getString(request.getParameter("operation"));
			
			int pageNo= DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize= DataUtility.getInt(request.getParameter("pageSize"));
			
			String ids[]= (String[])request.getParameterValues("ids");
			
			pageNo= (pageNo==0)?1:pageNo;
			pageSize= (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
			
			if(OP_SEARCH.equalsIgnoreCase(op)){
				System.out.println("search called");
				pageNo=1;
			}
			else if(OP_NEXT.equalsIgnoreCase(op)){
				System.out.println("next called");
				pageNo++;
			}
			else if(OP_PREVIOUS.equalsIgnoreCase(op)){
				
				if(pageNo>1){
					System.out.println("previous called");
					pageNo--;
					
				}
				else{
					pageNo=1;
					ServletUtility.setErrorMessage("No Previous Page Available", request);
				}
			}
			
			else if(OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);	
			return;
			}
			
			else if(OP_RESET.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			    return;
			}
			else if(OP_DELETE.equalsIgnoreCase(op)){
				
				MarksheetModel mod = new MarksheetModel();
				if(ids!=null && ids.length>0){
					
					pageNo=1;
					System.out.println("ids value is"+ids);
					
					MarksheetBean dbean= new MarksheetBean();
					
					for(String id2:ids){
						
						int idnew= DataUtility.getInt(id2);
						System.out.println("new id value"+idnew);
						dbean.setId(idnew);
			      try{
			    	  mod.delete(dbean);
			    	  ServletUtility.setSuccessMessage("Marksheet Deleted Successfully", request);
			    	  
			      }
			      catch(Exception e){
			    	  e.printStackTrace();
			    	  }
				
					}
				}
				else{
					ServletUtility.setErrorMessage("Select Atleast One Record", request);
				   
				}
				//ServletUtility.forward(getView(), request, response);
			}
			
			MarksheetBean bean = (MarksheetBean)populateBean(request);
			MarksheetModel model = new MarksheetModel();
			
			List<MarksheetBean> list = new ArrayList<MarksheetBean>();
			try{
				list = model.search(bean,pageNo,pageSize);
				
				if(list==null ||list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
					ServletUtility.setErrorMessage("No Marksheet Found", request);
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

		@Override
		protected String getView() {
			
			return ORSView.MARKSHEET_LIST_VIEW;
		}

}
