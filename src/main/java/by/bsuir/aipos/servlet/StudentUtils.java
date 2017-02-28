package by.bsuir.aipos.servlet;

import by.bsuir.aipos.model.Student;
import by.bsuir.aipos.model.StudentGroup;
import by.bsuir.aipos.service.StudentGroupService;
import by.bsuir.aipos.service.StudentGroupServiceImpl;
import by.bsuir.aipos.service.StudentService;
import by.bsuir.aipos.service.StudentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentUtils {

    private static StudentService studentService = new StudentServiceImpl();
    private static StudentGroupService studentGroupService = new StudentGroupServiceImpl();
    private HttpServletRequest request;

    public StudentUtils(HttpServletRequest request){
        this.request = request;
    }

    public Student saveStudent(){
        Student student = new Student();
        student.setFirstName(request.getParameter("firstName"));
        student.setLastName(request.getParameter("lastName"));
        student.setMiddleName(request.getParameter("middleName"));
        student.setHomeAddress(request.getParameter("homeAddress"));
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = format.parse(request.getParameter("dateOfBirth"));
            student.setDateOfBirth(birthDate);
        } catch (ParseException e) {
            StudentLogger.getLogger().trace(e);
            StudentLogger.getLogger().error("ParseException dateOfBirth in saveStudent()");
        }
        StudentGroup studentGroup = studentGroupService.get(request.getParameter("group"));
        student.setStudentGroup(studentGroup);
        if (validStudent(student)){
            StudentLogger.getLogger().info("Save student");
            studentService.save(student);
        } else {
            StudentLogger.getLogger().warn("Get not valid student data");
        }
        return student;
    }

    private boolean validStudent(Student student) {
        return !(
                student.getFirstName().isEmpty() ||
                student.getLastName().isEmpty() ||
                student.getHomeAddress().isEmpty() ||
                student.getDateOfBirth() == null ||
                student.getStudentGroup() == null
        );
    }

    public void deleteStudent(){
        int id = Integer.parseInt(request.getParameter("userId"));
        if (studentService.get(id) != null){
            studentService.delete(id);
            StudentLogger.getLogger().info("Delete student id : " + id);
        } else {
            StudentLogger.getLogger().warn("Get not valid student id for delete, id : " + id);
        }
    }

    public List<Student> getStudents(){
        StudentLogger.getLogger().info("Get all student");
        return studentService.getAll();
    }

    public List<StudentGroup> getStudentGroups(){
        StudentLogger.getLogger().info("Get all student group");
        return studentGroupService.getAll();
    }
}
