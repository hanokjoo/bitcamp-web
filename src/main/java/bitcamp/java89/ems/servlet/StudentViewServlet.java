package bitcamp.java89.ems.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import bitcamp.java89.ems.dao.impl.StudentMysqlDao;
import bitcamp.java89.ems.vo.Student;

// 톰캣 서버가 실행할 수 있는 클래스는 반드시 Servlet 규격에 맞추어 제작해야 한다.
// 그러나 Servlet 인터페이스의 메서드가 많아서 구현하기 번거롭다.
// 그래서 AbstractServlet이라는 추상 클래스를 만들어서 이 클래스를 상속받아 
// 간접적으로 Servlet 인터페이스를 구현하는 방식을 취한다.
// 이 클래스를 상속받게 되면 오직 service() 메서드만 만들면 되기 때문에 코드가 편리하다.
@WebServlet("/student/view")
public class StudentViewServlet extends AbstractServlet{
  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    try {
      StudentMysqlDao studentDao = StudentMysqlDao.getInstance();
      response.setContentType("text/plain;charset=UTF-8");
      PrintWriter out = response.getWriter();

      Student student = studentDao.getOne(request.getParameter("userId"));
      if (student == null) {
        out.println("해당 아이디의 학생이 없습니다.");
        return;
      }      
      out.printf("아이디: %s\n", student.getUserId());
      out.printf("암호: (***)\n");
      out.printf("이름: %s\n", student.getName());
      out.printf("전화: %s\n", student.getTel());
      out.printf("이메일: %s\n", student.getEmail());
      out.printf("재직중: %s\n", (student.isWorking()) ? "Yes" : "No");
      out.printf("태어난 해: %d\n", student.getBirthYear());
      out.printf("학교: %s\n", student.getSchool());

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
