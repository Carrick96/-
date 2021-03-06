package cn.cqut.final_edu_ketangpai.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @CLASSNAME:CourseIndexController
 * @description:
 * @author: Nonameguy
 * @create: 2020-05-14 22:45
 */
@Controller
public class CourseIndexController {
	@GetMapping("/studentcourse")
	private String getStudentCourse() {
		return "/studentcourse";
	}
	@GetMapping("/teachercourse")
	private String teacherCourse(){
		return "/teachercourse";
	}
	@GetMapping("/teachercoursemember")
	private String teacherCourseMember() {
		return "/member";
	}
	@GetMapping("/muke")
	private String muKe() { return "/muke";	}
	@GetMapping("/myjingpin")
	private String myJingPin() { return "/myjingpin"; }
	@GetMapping("/papermanage")
	private String paperManage() { return "/papermanage"; }
	@GetMapping("/shuangxuanhui")
	private String shuangXuanHui() { return "/shuangxuanhui"; }


}
