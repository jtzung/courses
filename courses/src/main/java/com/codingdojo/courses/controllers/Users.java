package com.codingdojo.courses.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.courses.models.Course;
import com.codingdojo.courses.models.User;
import com.codingdojo.courses.models.UserCourse;
import com.codingdojo.courses.services.CourseService;
import com.codingdojo.courses.services.UserCourseService;
import com.codingdojo.courses.services.UserService;
import com.codingdojo.courses.validator.UserValidator;

// imports removed for brevity
@Controller
public class Users {
	private final UserValidator userValidator;
    private final UserService userService;
    private final CourseService courseService;
    private final UserCourseService userCourseService;

    
    public Users(UserService userService, UserValidator userValidator, CourseService courseService,UserCourseService usercourseService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.courseService = courseService;
        this.userCourseService = usercourseService;
    }
    
    @RequestMapping("/registration")
    public String registerForm(@ModelAttribute("user") User user) {
        return "registrationPage.jsp";
    }
    @RequestMapping("/login")
    public String login() {
        return "loginPage.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	userValidator.validate(user, result);
        // if result has errors, return the registration page (don't worry about validations just now)
    	if(result.hasErrors()) {
    		return "registrationPage.jsp";
    	}
    	userService.registerUser(user);
    	session.setAttribute("userId",user.getId());
    	return "redirect:/home";
        // else, save the user in the database, save the user id in session, and redirect them to the /home route
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        // if the user is authenticated, save their user id in session
    	if(userService.authenticateUser(email, password)) {
    		User user = userService.findByEmail(email);
    		session.setAttribute("userId", user.getId());
    		return "redirect:/home";
    	}
    	model.addAttribute("error", "Invalid login credentials");
    	return "loginPage.jsp";
        // else, add error messages and return the login page
    }
    
    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
    	if(session.getAttribute("userId")==null) {
    		return "redirect:/registration";
    	}
    	Long id = (Long) session.getAttribute("userId");
    	User loggedInUser = userService.findUserById(id);
    	model.addAttribute("user", loggedInUser);
    	
    	List<Course> allCourses = courseService.getAllCourses();
    	model.addAttribute("allCourses", allCourses);
    	return "homePage.jsp";
        // get user from session, save them in the model and return the home page
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/login";
    }
    
    
    @RequestMapping("/add/{courseId}")
    public String add(@PathVariable("courseId") Long courseId, HttpSession session) {
    	//Getting our course and our user via user's session id, and course id through path variable
    	Long userId = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userId);
    	Course course = courseService.getCourseById(courseId);
    	
    	//OPTION ONE: Adding user to course's student list
    	//Can't add extra info to middle table easily
	//    	course.getStudents().add(user);
	//    	courseService.saveCourse(course);
    	
    	//OPTION TWO: Adding course to user's current courses
	//    	user.getCourses().add(course);
	//    	userService.saveUser(user);
    	
    	//OPTION THREE: Creating and saving an instance of middle table
    	UserCourse uc = new UserCourse();
    	uc.setCourse(course);
    	uc.setUser(user);
    	Date date = new Date();
    	uc.setCreatedAt(date);
    	userCourseService.saveUC(uc);
    	
    	return "redirect:/home";
    	
    }
    
    
//    @RequestMapping("/edit/{id}")
//    public String edit(@ModelAttribute("course") Course course) {
//    	return "edit.jsp";
//    }
    
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
    	Course course = courseService.getCourseById(id);
    	model.addAttribute("course", course);
    	return "edit.jsp";
    }
    
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("course") Course course, BindingResult result) {
    	if(result.hasErrors()) {
    		return "edit.jsp";
    	}
    	courseService.saveCourse(course);
    	return "redirect:/home";
    }
    
    
}