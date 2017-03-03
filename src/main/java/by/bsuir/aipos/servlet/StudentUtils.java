package by.bsuir.aipos.servlet;

import by.bsuir.aipos.model.Student;
import by.bsuir.aipos.model.StudentGroup;
import by.bsuir.aipos.service.StudentGroupService;
import by.bsuir.aipos.service.StudentGroupServiceImpl;
import by.bsuir.aipos.service.StudentService;
import by.bsuir.aipos.service.StudentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentUtils {
    /**
     * Service for handling with student groups
     */
    private static StudentService studentService = new StudentServiceImpl();
    /**
     * Service for handling with students
     */
    private static StudentGroupService studentGroupService = new StudentGroupServiceImpl();
    private HttpServletRequest request;

    public StudentUtils(HttpServletRequest request){
        this.request = request;
    }

    /**
     * Save or update student
     *
     * @return saved student
     */
    public Student saveStudent(){
        final String ISO = "iso-8859-1";
        final String UTF8 = "UTF-8";
        Student student = new Student();
        if (!request.getParameter("id").isEmpty()) {
            student.setId(Integer.parseInt(request.getParameter("id")));
        }
        try {
            student.setFirstName
                    (new String(request.getParameter("firstName").getBytes(ISO), UTF8));
            student.setLastName
                    (new String(request.getParameter("lastName").getBytes(ISO), UTF8));
            student.setMiddleName
                    (new String(request.getParameter("middleName").getBytes(ISO), UTF8));
            student.setHomeAddress
                    (new String(request.getParameter("homeAddress").getBytes(ISO), UTF8));
        } catch (UnsupportedEncodingException e) {
            StudentLogger.getLogger().trace(e);
            StudentLogger.getLogger().error("UnsupportedEncodingException in saveStudent()");
        }
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

    /**
     * Check property of student
     *
     * @param student
     * @return true if student have all needed property
     */
    private boolean validStudent(Student student) {
        return !(
                student.getFirstName().isEmpty() ||
                student.getLastName().isEmpty() ||
                student.getHomeAddress().isEmpty() ||
                student.getDateOfBirth() == null ||
                student.getStudentGroup() == null
        );
    }

    /**
     * Get student
     *
     * @return student
     */
    public Student getStudent() {
        int id = Integer.parseInt(request.getParameter("userId"));
        Student student = new Student();
        if (studentService.get(id) != null){
            student = studentService.get(id);
            StudentLogger.getLogger().info("Get student id : " + id);
        } else {
            StudentLogger.getLogger().warn("Get not valid student id for get, id : " + id);
        }
        return student;
    }

    /**
     * Delete student
     */
    public void deleteStudent(){
        int id = Integer.parseInt(request.getParameter("userId"));
        if (studentService.get(id) != null){
            studentService.delete(id);
            StudentLogger.getLogger().info("Delete student id : " + id);
        } else {
            StudentLogger.getLogger().warn("Get not valid student id for delete, id : " + id);
        }
    }

    /**
     * Get all student
     *
     * @return list of student
     */
    public List<Student> getStudents(){
        StudentLogger.getLogger().info("Get all student");
        return studentService.getAll();
    }

    /**
     * Get all student groups
     *
     * @return list of student groups
     */
    public List<StudentGroup> getStudentGroups(){
        StudentLogger.getLogger().info("Get all student group");
        return studentGroupService.getAll();
    }
}
