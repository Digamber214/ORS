package in.co.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.bean.MarksheetBean;
import in.co.exception.ApplicationException;
import in.co.model.MarksheetModel;

public class MarksheetModelTest {

	public static MarksheetModel model = new MarksheetModel();
	
	public static void main(String[] args) {		
		
		testAdd();  
		//testDelete();  
	//	testUpdate();   
	//testfindByRollNo();
		//testfindByPk();
		//testSearch();
		//testList();
	}
	
	public static void testAdd(){
		
		MarksheetBean bean = new MarksheetBean();
		   
		try{
		     	
		     	bean.setRollNo("01CS20");
	            bean.setPhysics(98);
	            bean.setChemistry(67);
	            bean.setMaths(99);
	            bean.setStudentId(1L);
	            bean.setCreatedBy("Admin");
	            bean.setModifiedBy("Admin");
	            
	           model.add(bean);
	       
	            
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testDelete(){
		
		MarksheetBean bean = new MarksheetBean();
		
		bean.setId(6);
		try {
			model.delete(bean);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testUpdate(){
		try{
		MarksheetBean bean = model.findByPk(5L);
		
		bean.setRollNo("104");
        bean.setPhysics(58);
        bean.setChemistry(57);
        bean.setMaths(50);
        bean.setStudentId(1L);
        bean.setName("kapil sharma");
        bean.setCreatedBy("Admin");
        bean.setModifiedBy("Admin");
        
        model.update(bean);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testfindByRollNo(){
		
		 try {
	            MarksheetBean bean = model.findByRollNo("01CS88");
	            
	            System.out.println(bean.getId());
	            System.out.println(bean.getRollNo());
	            System.out.println(bean.getName());
	            System.out.println(bean.getPhysics());
	            System.out.println(bean.getChemistry());
	            System.out.println(bean.getMaths());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void testfindByPk(){
		try{
		MarksheetBean bean = model.findByPk(5L);
		
		System.out.println(bean.getId());
        System.out.println(bean.getRollNo());
        System.out.println(bean.getName());
        System.out.println(bean.getPhysics());
        System.out.println(bean.getChemistry());
        System.out.println(bean.getMaths());
		
	}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testSearch(){
		
		//System.out.println("inside search");
		MarksheetBean bean = new MarksheetBean();
		
		List<MarksheetBean> list = new ArrayList();
		try{
			
			//bean.setName("shyam verma");
			//bean.setRollNo("104");
		list=model.search(bean,1,1);
		
		bean.setName("ram kumawat");
		
		Iterator it = list.iterator();
		
		while(it.hasNext()){
			bean = (MarksheetBean) it.next();
			//System.out.println("bean value"+bean);
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getMaths());
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void testList(){
		
		MarksheetBean bean = new MarksheetBean();
		
		List list = new ArrayList();
		
		try{
			list = model.list(1,10);
			
			Iterator<MarksheetBean> it = list.iterator();
			while(it.hasNext()){
				bean= it.next();
             
				System.out.println(bean.getId());
                System.out.println(bean.getRollNo());
                System.out.println(bean.getName());
                System.out.println(bean.getPhysics());
                System.out.println(bean.getChemistry());
                System.out.println(bean.getMaths());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
