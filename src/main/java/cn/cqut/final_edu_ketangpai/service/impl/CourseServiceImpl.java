package cn.cqut.final_edu_ketangpai.service.impl;

import cn.cqut.final_edu_ketangpai.dao.CourseDao;
import cn.cqut.final_edu_ketangpai.dao.CourseOfStudentDao;
import cn.cqut.final_edu_ketangpai.dao.CourseOfTeacherDao;
import cn.cqut.final_edu_ketangpai.dto.CourseExecution;
import cn.cqut.final_edu_ketangpai.entity.Course;
import cn.cqut.final_edu_ketangpai.entity.User;
import cn.cqut.final_edu_ketangpai.enums.CourseStateEnum;
import cn.cqut.final_edu_ketangpai.exception.CourseOperationException;
import cn.cqut.final_edu_ketangpai.service.CourseService;
import cn.cqut.final_edu_ketangpai.util.UserTool;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @CLASSNAME:CourseServiceImpl
 * @description:
 * @author: Nonameguy
 * @create: 2020-05-06 14:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private CourseOfStudentDao courseOfStudentDao;
	@Autowired
	private CourseOfTeacherDao courseOfTeacherDao;

	@Override
	public CourseExecution topCourse(int id) {
		int result = courseDao.topCourse(id);
		if (result <= 0) {
			throw new CourseOperationException("置顶课程失败");
		}
		CourseExecution courseExecution = new CourseExecution();
		courseExecution.setState(CourseStateEnum.SUCCESS.getState());
		return courseExecution;
	}

	@Override
	public CourseExecution untopCourse(int id) {
		int result = courseDao.untopCourse(id);
		if (result <= 0) {
			throw new CourseOperationException("取消课程置顶失败");
		}
		CourseExecution courseExecution = new CourseExecution();
		courseExecution.setState(CourseStateEnum.SUCCESS.getState());
		return courseExecution;
	}

	@Override
	public CourseExecution addCourse(Course course) {
		CourseExecution courseExecution = new CourseExecution();
		int result = courseDao.insert(course);
		if (result <= 0) {
			throw new CourseOperationException("课程添加失败");
		}
		courseExecution.setState(CourseStateEnum.SUCCESS.getState());
		return courseExecution;
	}


	@Override
	public CourseExecution updateCourse(Course course) {
		CourseExecution courseExecution = new CourseExecution();
		UpdateWrapper<Course> updateWrapper = new UpdateWrapper<>();
		int result = courseDao.update(course, updateWrapper
				.lambda()
				.eq(Course::getCourseId, course.getCourseId()));
		if (result <= 0) {
			throw new CourseOperationException("课程添加失败");
		}
		courseExecution.setState(CourseStateEnum.SUCCESS.getState());
		return courseExecution;
	}

	@Override
	public CourseExecution queryCourseById(String courseId) {

		Course course = courseDao.selectByCourseId(courseId);
		if (course == null) {
			return new CourseExecution(CourseStateEnum.NULL_COURSE, course);
		}
		return new CourseExecution(CourseStateEnum.SUCCESS, course);
	}

	@Override
	public CourseExecution getStudentCourseList(String studentId, HttpServletRequest request) {
		User currentUser = UserTool.getCurrentUser(request);
		if (currentUser != null) {
			List<Course> courseList = courseDao.getStudentCourses(currentUser.getUserId());
			CourseExecution courseExecution = new CourseExecution();
			if (courseList != null) {
				courseExecution.setCourseList(courseList);
			} else {
				courseExecution.setState(CourseStateEnum.NULL_COURSE.getState());
			}
			return courseExecution;
		}
		return new CourseExecution();
	}

	@Override
	public CourseExecution getTeacherCourseList(String teacherId,HttpServletRequest request) {
		User currentUser = UserTool.getCurrentUser(request);
		if (currentUser != null) {
			List<Course> courseList = courseDao.getTeacherCourses(currentUser.getUserId());
			CourseExecution courseExecution = new CourseExecution();
			assert false;
			if (courseList != null) {
				courseExecution.setCourseList(courseList);
			} else {
				courseExecution.setState(CourseStateEnum.NULL_COURSE.getState());
			}
			return courseExecution;
		}
		return new CourseExecution();
	}

	@Override
	public CourseExecution deleteCourseById(String courseId) {
		int result = courseDao.delete(new QueryWrapper<Course>()
				.eq("course_id", courseId));
		if (result <= 0) {
			return new CourseExecution(CourseStateEnum.NULL_COURSEID);
		}
		return new CourseExecution(CourseStateEnum.SUCCESS);
	}

	@Override
	public CourseExecution archiveCourse(int id) {
		CourseExecution courseExecution = new CourseExecution();
		assert id != 0;
		int result = courseDao.archiveCourse(id);
		assert false;
		if (result > 0) {
			courseExecution.setState(CourseStateEnum.SUCCESS.getState());
		} else {
			courseExecution.setState(CourseStateEnum.NULL_COURSEID.getState());
		}
		return courseExecution;
	}

	@Override
	public CourseExecution unarchiveCourse(int id) {
		CourseExecution courseExecution = new CourseExecution();
		assert id != 0;
		int result = courseDao.unarchiveCourse(id);
		assert false;
		if (result > 0) {
			courseExecution.setState(CourseStateEnum.SUCCESS.getState());
		} else {
			courseExecution.setState(CourseStateEnum.NULL_COURSEID.getState());
		}
		return courseExecution;
	}

	@Override
	public CourseExecution getCourseById(String courseId) {
		Course course = courseDao.selectOne(new QueryWrapper<Course>()
				.lambda()
				.eq(Course::getCourseId, courseId));
		if (course == null) {
			throw new CourseOperationException("课程信息为空");
		}
		CourseExecution courseExecution = new CourseExecution();
		courseExecution.setCourse(course);
		return courseExecution;
	}


}
