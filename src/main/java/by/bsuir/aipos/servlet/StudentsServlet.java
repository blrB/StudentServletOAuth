package by.bsuir.aipos.servlet;

import by.bsuir.aipos.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/students"})
public class StudentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students = new StudentUtils(request).getStudents();
        request.setAttribute("students", students);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }

}