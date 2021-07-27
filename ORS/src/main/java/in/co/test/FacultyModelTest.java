package in.co.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.bean.FacultyBean;
import in.co.model.FacultyModel;

public class FacultyModelTest {

	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		testAdd();
		//testUpdate();
		//testDelete();
		//testfindByPk();
		//testfindByEmail();
		//testSearch();
		//testList();
	}

	public static void testAdd() throws ParseException {
		FacultyBean bean = new FacultyBean();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

		Date d = new Date();
		
		bean.setFirstName("Poojan");
		bean.setLastName("jain");
		bean.setGender("M");
		bean.setLoginId("poojan@gmail.com");
		bean.setAddress("Indore");
		bean.setDateOfJoining(sdf.parse("10/11/1999"));
		bean.setQualification("BTECH");
		bean.setMobileNo("9981249472");
		bean.setCollegeId(2);
		bean.setCollegeName("TRUBA");
		bean.setCourseId(1);
		bean.setCourseName("BE");
		bean.setSubjectId(1);
		bean.setSubjectName("TOC");
	
		
        
		try {
			model.add(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testUpdate() throws Exception {
		FacultyBean bean = new FacultyBean();
	
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		bean.setId(1);
		bean.setFirstName("Palash");
		bean.setLastName("jain");
		bean.setLoginId("palash@gmail.com");
		bean.setCollegeId(6);
		bean.setCourseId(1);
		bean.setSubjectId(1);

		bean.setQualification("PhD");
		bean.setMobileNo("987665444");
		bean.setDateOfJoining(sdf.parse("20-08-1988"));
		
		model.update(bean);
		
	}
	
	public static void testDelete() throws Exception {
		FacultyBean bean = new FacultyBean();

		bean.setId(2);
		
		model.delete(bean);
	}

	public static void testfindByPk() throws Exception {
		
		FacultyBean bean = new FacultyBean();
 
		bean= model.findByPk(1);
		
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		
	}
	
	public static void testfindByEmail(){
		
		FacultyBean bean = new FacultyBean();
		
		try {
			bean= model.findByEmail("palash@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
	}
	
	public static void testSearch() throws Exception{
		
		List list = new ArrayList();
		
		FacultyBean bean=new FacultyBean();
		

		list = model.search(bean,0,0);
		
		bean.setId(1);
		Iterator<FacultyBean> it = list.iterator();
		while(it.hasNext()){
			bean= it.next();
			
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getDateOfJoining());
			
		}
	}
	
public static void testList() throws Exception{
		
		List list = new ArrayList();
		
		FacultyBean bean=new FacultyBean();
		

		list = model.list(0,0);
		
		bean.setId(1);
		Iterator<FacultyBean> it = list.iterator();
		while(it.hasNext()){
			bean= it.next();
			
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getDateOfJoining());
			
		}
	}
}
