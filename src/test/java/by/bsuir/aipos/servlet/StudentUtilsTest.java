package by.bsuir.aipos.servlet;

import by.bsuir.aipos.model.Student;
import by.bsuir.aipos.model.StudentGroup;
import by.bsuir.aipos.service.StudentGroupService;
import by.bsuir.aipos.service.StudentGroupServiceImpl;
import by.bsuir.aipos.service.StudentService;
import by.bsuir.aipos.service.StudentServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest({StudentUtils.class, StudentService.class, StudentGroupService.class})
public class StudentUtilsTest {

    private static StudentService studentService;
    private static StudentGroupService studentGroupService;
    private static HttpServletRequest request;
    private static StudentGroup studentGroupID;
    private static StudentUtils studentUtils;

    @BeforeClass
    public static void init() throws Exception {
        studentService = mock(StudentServiceImpl.class);
        studentGroupID = new StudentGroup("421702");
        studentGroupID.setId(1);
        studentGroupService = mock(StudentGroupServiceImpl.class);
        when(studentGroupService.get("421702")).thenReturn(studentGroupID);
        request = mock(HttpServletRequest.class);
        when(request.getParameter("firstName")).thenReturn("Andrey");
        when(request.getParameter("lastName")).thenReturn("Bobkov");
        when(request.getParameter("middleName")).thenReturn("Valerievich");
        when(request.getParameter("homeAddress")).thenReturn("Bobrujsk");
        when(request.getParameter("dateOfBirth")).thenReturn("1996-12-05");
        when(request.getParameter("group")).thenReturn("421702");
        PowerMockito.whenNew(StudentServiceImpl.class).withNoArguments()
                .thenReturn((StudentServiceImpl) studentService);
        PowerMockito.whenNew(StudentGroupServiceImpl.class).withNoArguments()
                .thenReturn((StudentGroupServiceImpl) studentGroupService);
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

}