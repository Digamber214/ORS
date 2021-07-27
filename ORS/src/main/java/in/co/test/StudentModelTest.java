package in.co.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.bean.CollegeBean;
import in.co.bean.StudentBean;
import in.co.exception.ApplicationException;
import in.co.exception.DuplicateRecordException;
import in.co.model.StudentModel;

public class StudentModelTest {

	public static StudentModel model = new StudentModel();

	public static void main(String[] args) throws Exception {
		testAdd(); // It should be tested according to college id in college
		// table
		//testDelete();
		// testFindByPK();
		 //testUpdate();
		// testFindByEmailId();
		//testSearch();
		//testList();

	}

	public static void testAdd() throws ApplicationException , DuplicateRecordException {
		StudentBean bean = new StudentBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    bean.setId(1);
          	bean.setCollegeName("REC");
			bean.setFirstName("Ayush");
			bean.setLastName("kumawat");
			bean.setDob(sdf.parse("31/11/1990"));
			bean.setMobileNo("9165254357");
			bean.setEmail("a@gmail.com");
			bean.setCollegeId(2L);
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreateDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			model.add(bean);
			// StudentBean add= model.nextPk(pk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		StudentBean bean = new StudentBean();
		try {
			bean.setId(2);
			model.delete(bean);
		} catch (Exception e) {
        e.printStackTrace();
		}
	}

	public static void testFindByPK() {

		try {
			StudentBean bean = model.findByPk(277L);
			System.out.println(bean);
			if(bean==null)
			{
				System.out.println("Null Hai");
			}else{
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getDob());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			}
			if (bean != null) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {

			StudentBean bean = new StudentBean();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setId(1L);
			bean.setCollegeName("LNCT");
			bean.setFirstName("Ankur");
			bean.setLastName("Agarwal");
			bean.setDob(sdf.parse("03/02/1990"));
			bean.setMobileNo("9165254357");
			bean.setEmail("ankur@gmail.com");
			// bean.setCollegeId(4L);
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreateDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

			model.update(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByEmailId() {

		try {
			StudentBean bean = new StudentBean();

			bean = model.findByEmail("ankur@gmail.com");

			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getDob());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());

		} catch (Exception e) {
           e.printStackTrace();
		}
	}
	
	public static void testSearch(){
		
		StudentBean bean = new StudentBean();
		
		List<StudentBean> al= new ArrayList();
		
		try{
			bean.setFirstName("ankur");
		   al= model.search(bean,1,1);
		   
		   Iterator it = al.iterator();
		   while(it.hasNext()){
			   bean = (StudentBean) it.next();
		     
			   System.out.println(bean.getId());
               System.out.println(bean.getFirstName());
               System.out.println(bean.getLastName());
               System.out.println(bean.getDob());
               System.out.println(bean.getMobileNo());
               System.out.println(bean.getEmail());
               System.out.println(bean.getCollegeId());
			   
		   }
		   
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testList(){
		
		StudentBean bean = new StudentBean();
		
		List<StudentBean> al = new ArrayList();
		try{
			al= model.list(1,10);
			
			Iterator<StudentBean> it = al.iterator();
			while(it.hasNext()){
				bean = it.next();
				System.out.println(bean.getId());
	               System.out.println(bean.getFirstName());
	               System.out.println(bean.getLastName());
	               System.out.println(bean.getDob());
	               System.out.println(bean.getMobileNo());
	               System.out.println(bean.getEmail());
	               System.out.println(bean.getCollegeId());
				
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
