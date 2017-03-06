package by.bsuir.aipos.servlet;

import by.bsuir.aipos.model.Student;
import by.bsuir.aipos.model.StudentGroup;
import by.bsuir.aipos.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest({StudentUtils.class, StudentServiceImpl.class, StudentGroupServiceImpl.class})
public class StudentUtilsTest {

    private StudentServiceImpl studentService;
    private StudentGroupServiceImpl studentGroupService;
    private HttpServletRequest request;
    private StudentUtils studentUtils;
    private Student student;
    private StudentGroup studentGroup;

    @Before
    public void init() throws Exception {
        String dateString = "1996-12-05";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateString);
        studentGroup = new StudentGroup("421702");
        studentGroup.setId(1);
        student = new Student(
                "Andrey",
                "Bobkov",
                "Valerievich",
                date,
                "Bobrujsk",
                studentGroup);
        student.setId(1);
        PowerMockito.mockStatic(StudentServiceImpl.class);
        studentService = mock(StudentServiceImpl.class);
        when(studentService.get(anyInt())).thenReturn(student);
        when(studentService.getAll())
                .thenReturn(new ArrayList<Student>(){{ add(student); }});
        PowerMockito.mockStatic(StudentGroupServiceImpl.class);
        studentGroupService = mock(StudentGroupServiceImpl.class);
        when(studentGroupService.get(anyString())).thenReturn(studentGroup);
        when(studentGroupService.getAll())
                .thenReturn(new ArrayList<StudentGroup>(){{ add(studentGroup); }});
        request = PowerMockito.mock(HttpServletRequest.class);
        when(request.getParameter("firstName")).thenReturn("Andrey");
        when(request.getParameter("lastName")).thenReturn("Bobkov");
        when(request.getParameter("middleName")).thenReturn("Valerievich");
        when(request.getParameter("homeAddress")).thenReturn("Bobrujsk");
        when(request.getParameter("dateOfBirth")).thenReturn("1996-12-05");
        when(request.getParameter("group")).thenReturn("421702");
        PowerMockito.whenNew(StudentServiceImpl.class).withNoArguments()
                .thenReturn(studentService);
        PowerMockito.whenNew(StudentGroupServiceImpl.class).withNoArguments()
                .thenReturn(studentGroupService);
        studentUtils = new StudentUtils(request);
    }

    @Test
    public void saveStudent() throws Exception {
        when(request.getParameter("id")).thenReturn("");
        Student student = studentUtils.saveStudent();
        String dateString = "1996-12-05";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateString);
        assert (student.getFirstName().equals("Andrey") &&
                student.getLastName().equals("Bobkov") &&
                student.getMiddleName().equals("Valerievich") &&
                student.getDateOfBirth().equals(date) &&
                student.getHomeAddress().equals("Bobrujsk") &&
                student.getStudentGroup().getName().equals("421702")
        );
    }

    @Test
    public void getStudent() throws Exception {
        when(request.getParameter("userId")).thenReturn("1");
        Student student = studentUtils.getStudent();
        assert (student.getFirstName().equals("Andrey") &&
                student.getLastName().equals("Bobkov") &&
                student.getMiddleName().equals("Valerievich") &&
                student.getHomeAddress().equals("Bobrujsk") &&
                student.getStudentGroup().getName().equals("421702")
        );
    }

    @Test
    public void deleteStudent() throws Exception {
        when(request.getParameter("userId")).thenReturn("1");
        studentService.delete(1);
        verify(studentService).delete(1);
    }

    @Test
    public void getStudents() throws Exception {
        List<Student> students = studentUtils.getStudents();
        assert (students.size() == 1);
    }

    @Test
    public void getStudentGroups() throws Exception {
        List<StudentGroup> studentsGroup = studentUtils.getStudentGroups();
        assert (studentsGroup.size() == 1);
    }

}