package in.co.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mchange.v2.lock.ExactReentrantSharedUseExclusiveUseLock;

import in.co.bean.RoleBean;
import in.co.model.RoleModel;

public class RoleModelTest {

	public static RoleModel model = new RoleModel();
	
	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
		testdelete();
		//testfindByPk();
		//testSearch();
		//testlist();
	}
	
	public static void testAdd(){
		
		try{
			
			RoleBean bean = new RoleBean();
			
			bean.setName("Aaa");
            bean.setDescription("ughade");
            bean.setCreatedBy("student");
            bean.setModifiedBy("student");
            bean.setCreateDateTime(new Timestamp(new Date().getTime()));
            bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		                   
		    model.add(bean);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testUpdate(){
		
		try{
			
			RoleBean bean = new RoleBean();
			bean.setId(3);
			bean.setName("Ankit");
            bean.setDescription("Agrwal");
            bean.setCreatedBy("student");
            bean.setModifiedBy("student");
            
            model.update(bean);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testdelete(){
		
		try{
			RoleBean bean = new RoleBean();
			
			bean.setId(3);
			
			model.delete(bean);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testfindByPk(){
		
		try{
			RoleBean bean = new RoleBean();
			
			bean=model.findByPk(2);
			
			System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getDescription());
			
		}
		catch(Exception e){
			
		}
	}
	
	public static void testSearch(){
		
		
		
		try{
			
			RoleBean bean = new RoleBean();
			
			List<RoleBean> al = new ArrayList();
			
			bean.setName("ddd");
			//bean.setId(2);
			//bean.setDescription("verma");
			al= model.search(bean,0,0);
			
			Iterator it = al.iterator();
			while(it.hasNext()){
				bean=(RoleBean) it.next();
				System.out.println(bean.getId());
	            System.out.println(bean.getName());
	            System.out.println(bean.getDescription());
				
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testlist(){
		
		RoleBean bean = new RoleBean();
		
		try{
			List al = new ArrayList();
			al = model.list(1,10);
			
			Iterator it = al.iterator();
			while(it.hasNext()){
				bean = (RoleBean)it.next();
     
				System.out.println(bean.getId());
	            System.out.println(bean.getName());
	            System.out.println(bean.getDescription());

			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
