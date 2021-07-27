package in.co.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.modelmbean.ModelMBean;

import in.co.bean.CollegeBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.model.CollegeModel;

public class CollegeModelTest {

	public static CollegeModel model = new CollegeModel();
	
     public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
    	 addTest();
    	 //testDelete();
    	 //testUpdate();
    	 //testSearch();
    	//testList();
    	 //testFindByName();
    	 //testFindByPk();
	}
     
     public static void addTest() throws ApplicationException, DuplicateRecordException{
    	 try{
    	 CollegeBean bean = new CollegeBean();
    	 
    	 bean.setName("ayushh");
    	 bean.setAddress("pithampur");
    	 bean.setCity("indore");
    	 bean.setState("MP");
    	 bean.setPhoneNo("88199");
    	 bean.setCreatedBy("Admin");
    	 bean.setModifiedBy("Admin");
    	 bean.setCreateDateTime(new Timestamp(new Date().getTime()));
    	 bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
        
    	 long pk = model.add(bean);
         System.out.println("Test add succ");
         CollegeBean addedBean = model.findByPK(pk);
    	if(addedBean == null){
    		System.out.println("Test add fail");
    	}
     }catch(ApplicationException e){
    	 e.printStackTrace();
     } 
}
     
     public static void testDelete(){
    	  long pk=2;
    	 try{
    	 CollegeBean bean = new CollegeBean();
    	 
    	 bean.setId(pk);
    	 
    	 model.delete(bean);
    	 
    	// CollegeBean deleteBean = model.findByPK(pk);
    	 
    	  
    	 }
    	 catch(Exception e){
    		 
    	 }
     }
     
     public static void testUpdate(){
    	 
    	 CollegeBean bean = new CollegeBean();
    	 
    	 try{
    		 bean = model.findByPK(4);
    		 
    		 bean.setName("Ankit");
    		 bean.setCity("kanpur");
    		 
    		 model.update(bean);
    	 }
    	 catch(Exception e){
    		 
    	 }
    	 
     }
     
     public static void testSearch(){
    	 
    	 try{
    		 CollegeBean bean = new CollegeBean();
    		 List<CollegeBean> l = new ArrayList();
    		 bean.setCity("bhopal");
    		 l=model.search(bean,1,1);  // how size affected 
    		// System.out.println(l);
    		 Iterator<CollegeBean> it = l.iterator();
    		 while(it.hasNext()){
    			 bean=it.next();
    			 System.out.println(bean.getAddress());
    			 System.out.println(bean.getId());
    			 System.out.println(bean.getCity());
    		 }
    		 
    	 }
    	 catch(Exception e){
    		 e.printStackTrace();
    	 }
     }
     
    public static void testList(){
    	try{
    		CollegeBean bean = new CollegeBean();
    		List<CollegeBean> l1= new ArrayList<CollegeBean>();
    		l1= model.list(1,1);
    		Iterator<CollegeBean> it= l1.iterator();
    		while(it.hasNext()){
    			bean = it.next();
    			System.out.println(bean.getName());
    			System.out.println(bean.getAddress());
    			
    		}
    		
    	}
    	catch(Exception e){
    e.printStackTrace();
    	}
    	
    }
    
    public static void testFindByName(){
    	
    	try{
    		CollegeBean bean = new CollegeBean();
    		bean=model.findByName("Ankur");
    		if(bean!=null){
    			System.out.println(bean.getId());
    			System.out.println(bean.getAddress());
    		}
    	}
    	catch(Exception e){
    		
    	}
    }
    
    public static void testFindByPk(){
    	try{
    		CollegeBean bean = new CollegeBean();
    		bean = model.findByPK(4);
    		if(bean!=null){
    			System.out.println(bean.getName());
    		}
    		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
     
}