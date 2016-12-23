package bitcamp.java89.ems.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems.dao.impl.StudentMysqlDao;
import bitcamp.java89.ems.vo.Student;

// 톰캣 서버가 실행할 수 있는 클래스는 반드시 Servlet 규격에 맞추어 제작해야 한다.
// 그러나 Servlet 인터페이스의 메서드가 많아서 구현하기 번거롭다.
// 그래서 AbstractServlet이라는 추상 클래스를 만들어서 이 클래스를 상속받아 
// 간접적으로 Servlet 인터페이스를 구현하는 방식을 취한다.
// 이 클래스를 상속받게 되면 오직 service() 메서드만 만들면 되기 때문에 코드가 편리하다.
@WebServlet("/student/update")
public class StudentUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    
    Student student = new Student();
    student.setUserId(request.getParameter("userId"));
    student.setPassword(request.getParameter("password"));
    student.setName(request.getParameter("name"));
    student.setTel(request.getParameter("tel"));
    student.setEmail(request.getParameter("email"));
    student.setWorking(request.getParameter("working").equals("Y")? true : false);
    student.setBirthYear(Integer.parseInt(request.getParameter("byear")));
    student.setSchool(request.getParameter("schl"));

    response.setHeader("Refresh", "1;url=list");
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>학생 관리-변경</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>변경 결과</h1>");
    
    try {
      StudentMysqlDao studentDao = StudentMysqlDao.getInstance();
      
      if (!studentDao.existUserId(student.getUserId())) {
        throw new Exception("입력하신 아이디를 찾지 못했습니다.");
      }
      
      studentDao.update(student);
      out.println("<p>학생 정보를 변경하였습니다.</p>");
      
    } catch (Exception e) {
      out.printf("<p>%s</p>", e);
    }
    out.println("</body>");
    out.println("</html>");
  }
}
