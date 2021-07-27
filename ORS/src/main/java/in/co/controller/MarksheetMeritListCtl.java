package in.co.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.bean.MarksheetBean;
import in.co.model.MarksheetModel;
import in.co.util.DataUtility;
import in.co.util.PropertyReader;
import in.co.util.ServletUtility;

/**
 * Marksheet Merit List functionality Controller. Performance operation of
 * Marksheet Merit List
 */

@WebServlet(name="MarksheetMeritListCtl",urlPatterns={"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	/** The log. */
	private static Logger log = Logger.getLogger(MarksheetMeritListCtl.class);
    
    /**
	 * Contains display logic
	 */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("inside do get");
		
		log.debug("");
	
		int pageNo=1;
		int pageSize= DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		MarksheetModel model = new MarksheetModel();
		try{
			List list =null;
			list=model.getMeritList(1,10);
			
			if(list==null && list.size()>0){
				ServletUtility.setErrorMessage("No record Found", request);
			}
			
			request.setAttribute("list",list);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(),request,response);
	}
    

/**
	 * Contains Submit logics
	 */

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			System.out.println("inside do post");
			
			String op = DataUtility.getString(request.getParameter("operation"));
			
			if(OP_BACK.equalsIgnoreCase(op)){
				ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
			}
	}

		@Override
		protected String getView() {
		
			return ORSView.MARKSHEET_MERIT_LIST_VIEW;
		}

}
