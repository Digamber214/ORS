package in.co.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.bean.TimetableBean;
import in.co.bean.UserBean;
import in.co.exception.ApplicationException;
import in.co.model.TimetableModel;

public class TimetableModelTest {

	public static TimetableModel model = new TimetableModel();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		 testAdd();
		// testDelete();
		// testUpdate();
		//testFindByPK();
		//testsearch();
		//testList();
		//testfindbycourse();
	}

	public static void testAdd() throws Exception {

		TimetableBean bean = new TimetableBean();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setCourseId(2);
		bean.setCourseName("BTech");
		bean.setSubjectId(6);
		bean.setSubjectName("ED");
		bean.setExamTime("2 to 3 pm");
		bean.setExamDate(sdf.parse("4/5/2000"));
		bean.setSemester("1st");
		model.add(bean);
	}

	public static void testDelete() throws Exception {

		TimetableBean bean = new TimetableBean();

		bean.setId(1);

		model.delete(bean);
	}

	public static void testUpdate() throws Exception {

		TimetableBean bean = new TimetableBean();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		bean.setId(2);

		bean.setCourseId(2);
		bean.setCourseName("BTech");
		bean.setSubjectId(6);
		bean.setSubjectName("ED");
		bean.setExamTime("1 to 2 pm");
		bean.setExamDate(sdf.parse("14/8/2008"));
		bean.setSemester("1st");
		model.update(bean);
	}
	
	public static void testFindByPK() throws Exception {
	       try {
	           TimetableBean bean = new TimetableBean();
	           //long pk = 2;
	           
	           bean=model.findByPk(2L);
	           System.out.println(bean.getId());
	           System.out.println(bean.getCourseName());
	           System.out.println(bean.getSubjectName());
	           System.out.println(bean.getExamDate());
	           
	       } catch (ApplicationException e) {
	           e.printStackTrace();
	       }

	   }
	
	public static void testsearch() throws Exception{
		
		TimetableBean bean = new TimetableBean();
		
		List list =  new ArrayList();
		
		list = model.search(bean,0,0);
		
		Iterator it = list.iterator();
		
		 while(it.hasNext()){
			bean = (TimetableBean)it.next();
			System.out.println(bean.getId());
	           System.out.println(bean.getCourseName());
	           System.out.println(bean.getSubjectName());
	           System.out.println(bean.getExamDate());
		}
	}
	
	public static void testList() throws Exception{
		
		TimetableBean bean = new TimetableBean();
		
		List list = new ArrayList();
		
		list = model.list(0,0);
				
		Iterator it = list.iterator();
		while(it.hasNext()){
			bean = (TimetableBean)it.next();
			
			System.out.println(bean.getId());
	           System.out.println(bean.getCourseName());
	           System.out.println(bean.getSubjectName());
	           System.out.println(bean.getExamDate());
		}
	}
	
	public static void testfindbycourse() throws ParseException, Exception{
		
		TimetableBean bean = new TimetableBean();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//bean.setCourseId(3);
		bean=model.findByCourseName(3,sdf.parse("24/08/2008"));
		
		
			   System.out.println(bean.getId());
	           System.out.println(bean.getCourseName());
	           System.out.println(bean.getSubjectName());
	           System.out.println(bean.getExamDate());
		
		
	}

}
