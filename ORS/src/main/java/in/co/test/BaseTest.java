package in.co.test;

import in.co.bean.CollegeBean;
import in.co.exception.ApplicationException;
import in.co.model.BaseModel;
import in.co.model.CollegeModel;
import in.co.util.DataUtility;

public class BaseTest extends BaseModel {
	
	public static BaseTest t = new BaseTest();
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "st_college";
	}

	
    public static void main(String[] args) throws ApplicationException {
    	
    	
    	t.getTableName();
      	t.setCreatedBy("Ayush");
      	t.setCreateDateTime(DataUtility.getCurrentTimestamp());
      	t.setId(2);
    	t.updateCreatedInfo();
    	
	}	 
    	    

}
