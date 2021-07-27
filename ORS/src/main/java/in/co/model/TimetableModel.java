package in.co.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.bean.CourseBean;
import in.co.bean.SubjectBean;
import in.co.bean.TimetableBean;
import in.co.util.JDBCDataSource;

/**
 * The Class TimetableModel.
 */
public class TimetableModel {

	/**
	 * Next pk.
	 *
	 * @return the integer
	 * @throws Exception the exception
	 */
	public Integer nextPk() throws Exception {
		int pk = 0;

		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_timetable");
			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			conn.close();
		}
		return pk = pk + 1;
	}

	/**
	 * Find by pk.
	 *
	 * @param pk the pk
	 * @return the timetable bean
	 * @throws Exception the exception
	 */
	public TimetableBean findByPk(long pk) throws Exception {

		Connection conn = null;

		TimetableBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_timetable where id =?");

		System.out.println("id is" + sql);
		System.out.println("pk is" + pk);
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getInt(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getInt(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamTime(rs.getString(7));
				bean.setExamDate(rs.getDate(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreateDateTime(rs.getTimestamp(11));
				bean.setModifiedDateTime(rs.getTimestamp(12));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return bean;
	}

	/**
	 * Adds the.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws Exception the exception
	 */
	public long add(TimetableBean bean) throws Exception {
		int pk = 0;

		Connection conn = null;

		CourseModel cmodel = new CourseModel();
		CourseBean cbean = cmodel.findByPk(bean.getCourseId());
		System.out.println("cbean value" + cbean);
		System.out.println("course name is" + cbean.getcName());
		bean.setCourseName(cbean.getcName());

		SubjectModel smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPk(bean.getSubjectId());
		System.out.println("subject name" + sbean.getSubjectName());
		bean.setSubjectName(sbean.getSubjectName());

		TimetableBean bean1 = findByCSS(bean.getCourseName(), bean.getSubjectName(), bean.getSemester());

		TimetableBean bean2 = findByCSE(bean.getCourseName(), bean.getSemester(),
				new java.sql.Date(bean.getExamDate().getTime()));

		System.out.println("bean1 value " + bean1);
		System.out.println("bean2 value " + bean2);

		if (bean1 != null || bean2 != null) {
			throw new Exception("exam timetable already exist");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setInt(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setInt(4, bean.getSubjectId());
			pstmt.setString(5, bean.getSubjectName());
			pstmt.setString(6, bean.getSemester());
			pstmt.setString(7, bean.getExamTime());
			pstmt.setDate(8, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreateDateTime());
			pstmt.setTimestamp(12, bean.getModifiedDateTime());
			int i = pstmt.executeUpdate();
			conn.commit();
			System.out.println("record inserted" + i);
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return pk;
	}

	/**
	 * Delete.
	 *
	 * @param bean the bean
	 * @throws SQLException the SQL exception
	 */
	public void delete(TimetableBean bean) throws SQLException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_timetable where id =?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			System.out.println("record deleted");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * Update.
	 *
	 * @param bean the bean
	 * @throws Exception the exception
	 */
	public void update(TimetableBean bean) throws Exception {

		Connection conn = null;

		CourseModel cmodel = new CourseModel();
		CourseBean cbean = cmodel.findByPk(bean.getCourseId());
		System.out.println("cbean value" + cbean);
		System.out.println("course name is" + cbean.getcName());
		bean.setCourseName(cbean.getcName());

		SubjectModel smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPk(bean.getSubjectId());
		System.out.println("subject name" + sbean.getSubjectName());
		bean.setSubjectName(sbean.getSubjectName());

		TimetableBean bean1 = findByCSS(bean.getCourseName(), bean.getSubjectName(), bean.getSemester());

		TimetableBean bean2 = findByCSE(bean.getCourseName(), bean.getSemester(),
				new java.sql.Date(bean.getExamDate().getTime()));

		System.out.println("bean1 value " + bean1);
		System.out.println("bean2 value " + bean2);

		if (bean1 != null || bean2 != null) {
			throw new Exception("exam timetable already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_timetable set courseId=?,courseName=?,subjectId=?,subjectName=?,semester=?,examTime=?,examDate=?,createdBy=?,modifiedBy=?,createDatetime=?,modifiedDatetime=? where id =?");

			pstmt.setInt(1, bean.getCourseId());
			pstmt.setString(2, bean.getCourseName());
			pstmt.setInt(3, bean.getSubjectId());
			pstmt.setString(4, bean.getSubjectName());
			pstmt.setString(5, bean.getSemester());
			pstmt.setString(6, bean.getExamTime());
			pstmt.setDate(7, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreateDateTime());
			pstmt.setTimestamp(11, bean.getModifiedDateTime());
			pstmt.setLong(12, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			System.out.println("record updated");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws Exception the exception
	 */
	public List search(TimetableBean bean, int pageNo, int pageSize) throws Exception {

		List list = new ArrayList();

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {

				sql.append(" And id= " + bean.getId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" And courseId= " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND COURSENAME LIKE '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" And subjectId= " + bean.getSubjectId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECTNAME LIKE '" + bean.getSubjectName() + "%'");
			}
			if (bean.getSemester() != null && bean.getSemester().length() > 0) {
				sql.append(" AND SEMESTER LIKE '" + bean.getSemester() + "%'");
			}
			
			
			if (bean.getExamTime() != null && bean.getExamTime().length() > 0) {
				sql.append(" AND EXAMTIME = '" + bean.getExamTime() + "'");
			}
			
			if (bean.getExamDate()!=null && bean.getExamDate().getTime() > 0) {

				System.out.println("date before for" + bean.getExamDate().getTime());

				Date d = new Date(bean.getExamDate().getTime());
				
				sql.append(" AND EXAMDATE= '" + d+"'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + " ," + pageSize);
		}
		System.out.println("sql query is" + sql);
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			TimetableBean bean1;
			while (rs.next()) {
				bean1 = new TimetableBean();

				bean1.setId(rs.getLong(1));
				bean1.setCourseId(rs.getInt(2));
				bean1.setCourseName(rs.getString(3));
				bean1.setSubjectId(rs.getInt(4));
				bean1.setSubjectName(rs.getString(5));
				bean1.setSemester(rs.getString(6));
				bean1.setExamTime(rs.getString(7));
				bean1.setExamDate(rs.getDate(8));
				bean1.setCreatedBy(rs.getString(9));
				bean1.setModifiedBy(rs.getString(10));
				bean1.setCreateDateTime(rs.getTimestamp(11));
				bean1.setModifiedDateTime(rs.getTimestamp(12));

				list.add(bean1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * Search.
	 *
	 * @param bean the bean
	 * @return the list
	 * @throws Exception the exception
	 */
	public List search(TimetableBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	/**
	 * List.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list
	 * @throws Exception the exception
	 */
	public List list(int pageNo, int pageSize) throws Exception {

		List list = new ArrayList();

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + " ," + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			TimetableBean bean1;
			while (rs.next()) {
				bean1 = new TimetableBean();

				bean1.setId(rs.getLong(1));
				bean1.setCourseId(rs.getInt(2));
				bean1.setCourseName(rs.getString(3));
				bean1.setSubjectId(rs.getInt(4));
				bean1.setSubjectName(rs.getString(5));
				bean1.setSemester(rs.getString(6));
				bean1.setExamTime(rs.getString(7));
				bean1.setExamDate(rs.getDate(8));
				bean1.setCreatedBy(rs.getString(9));
				bean1.setModifiedBy(rs.getString(10));
				bean1.setCreateDateTime(rs.getTimestamp(11));
				bean1.setModifiedDateTime(rs.getTimestamp(12));

				list.add(bean1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;

	}

	/**
	 * List.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List list() throws Exception {
		return list(0, 0);
	}

	/**
	 * Find by course name.
	 *
	 * @param courseId the course id
	 * @param examDate the exam date
	 * @return the timetable bean
	 * @throws Exception the exception
	 */
	public TimetableBean findByCourseName(long courseId, java.util.Date examDate) throws Exception {

		Connection conn = null;
		TimetableBean bean1 = new TimetableBean();

		Date examdate1 = new Date(examDate.getTime());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_timetable where courseId=? AND examDate=?");

			System.out.println("examdate" + examdate1 + "courseId" + courseId);

			pstmt.setLong(1, courseId);
			pstmt.setDate(2, examdate1);

			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			long id = nextPk();

			while (rs.next()) {
				System.out.println("rs");
				bean1 = new TimetableBean();
				bean1.setId(rs.getLong(1));
				bean1.setCourseId(rs.getInt(2));
				bean1.setCourseName(rs.getString(3));
				bean1.setSubjectId(rs.getInt(4));
				bean1.setSubjectName(rs.getString(5));
				bean1.setSemester(rs.getString(6));
				bean1.setExamTime(rs.getString(7));
				bean1.setExamDate(rs.getDate(8));
				bean1.setCreatedBy(rs.getString(9));
				bean1.setModifiedBy(rs.getString(10));
				bean1.setCreateDateTime(rs.getTimestamp(11));
				bean1.setModifiedDateTime(rs.getTimestamp(12));
			}

		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return bean1;
	}

	/**
	 * Find by CSS.
	 *
	 * @param course the course
	 * @param subject the subject
	 * @param sem the sem
	 * @return the timetable bean
	 * @throws Exception the exception
	 */
	public TimetableBean findByCSS(String course, String subject, String sem) throws Exception {

		Connection conn = null;
		TimetableBean bean1 = null;

		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where courseName=? AND subjectName=? AND semester=?");

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, course);
			pstmt.setString(2, subject);
			pstmt.setString(3, sem);

			ResultSet rs = pstmt.executeQuery();
			conn.commit();

			while (rs.next()) {

				bean1 = new TimetableBean();

				bean1.setId(rs.getLong(1));
				bean1.setCourseId(rs.getInt(2));
				bean1.setCourseName(rs.getString(3));
				bean1.setSubjectId(rs.getInt(4));
				bean1.setSubjectName(rs.getString(5));
				bean1.setSemester(rs.getString(6));
				bean1.setExamTime(rs.getString(7));
				bean1.setExamDate(rs.getDate(8));
				bean1.setCreatedBy(rs.getString(9));
				bean1.setModifiedBy(rs.getString(10));
				bean1.setCreateDateTime(rs.getTimestamp(11));
				bean1.setModifiedDateTime(rs.getTimestamp(12));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}

		return bean1;
	}

	/**
	 * Find by CSE.
	 *
	 * @param course the course
	 * @param sem the sem
	 * @param examDate the exam date
	 * @return the timetable bean
	 * @throws Exception the exception
	 */
	public TimetableBean findByCSE(String course, String sem, java.util.Date examDate) throws Exception {

		Connection conn = null;
		TimetableBean bean1 = null;

		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where courseName=? AND semester=? AND examDate=?");

		Date examDate1 = new Date(examDate.getTime());

		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, course);
			pstmt.setString(2, sem);
			pstmt.setDate(3, examDate1);

			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			while (rs.next()) {

				bean1 = new TimetableBean();

				bean1.setId(rs.getLong(1));
				bean1.setCourseId(rs.getInt(2));
				bean1.setCourseName(rs.getString(3));
				bean1.setSubjectId(rs.getInt(4));
				bean1.setSubjectName(rs.getString(5));
				bean1.setSemester(rs.getString(6));
				bean1.setExamTime(rs.getString(7));
				bean1.setExamDate(rs.getDate(8));
				bean1.setCreatedBy(rs.getString(9));
				bean1.setModifiedBy(rs.getString(10));
				bean1.setCreateDateTime(rs.getTimestamp(11));
				bean1.setModifiedDateTime(rs.getTimestamp(12));

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}

		return bean1;
	}

}
