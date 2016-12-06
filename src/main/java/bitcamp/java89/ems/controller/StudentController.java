package bitcamp.java89.ems.controller;

import java.io.PrintStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bitcamp.java89.ems.dao.StudentDao;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.annotation.RequestParam;
import bitcamp.java89.ems.vo.Student;

@Component
public class StudentController {
  @Autowired StudentDao studentDao;
  
  @RequestMapping(value="student/add")
  public void add(
      @RequestParam("userId") String userId,
      @RequestParam("password") String password,
      @RequestParam("name") String name,
      @RequestParam("tel") String tel,
      @RequestParam("email") String email,
      @RequestParam("working") boolean working,
      @RequestParam("birthYear") int birthYear,
      @RequestParam("school") String school,
      PrintStream out)
      throws Exception {
    // 주입받은 StudentDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 StudentDao가 주입되어 있어야 한다.
    if (studentDao.existUserId(userId)) {
      out.println("같은 아이디가 존재합니다. 등록을 취소합니다.");
      return;
    }
    
    Student student = new Student();
    student.setUserId(userId);
    student.setPassword(password);
    student.setName(name);
    student.setTel(tel);
    student.setEmail(email);
    student.setWorking(working);
    student.setBirthYear(birthYear);
    student.setSchool(school);

    studentDao.insert(student);
    out.println("등록하였습니다.");
  }
  
  @RequestMapping(value="student/delete")
  public void delete(@RequestMapping("userId") String userId, PrintStream out) throws Exception {
    // 주입받은 StudentDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 StudentDao가 주입되어 있어야 한다.
    if (!studentDao.existUserId(userId)) {
      out.println("해당 아이디의 학생이 없습니다.");
      return;
    }
    
    studentDao.delete(userId);
    out.println("해당 데이터 삭제 완료하였습니다.");
  }
  
  @RequestMapping(value="student/list")
  public void list(PrintStream out) throws Exception {
    // 주입받은 StudentDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 StudentDao가 주입되어 있어야 한다.
    ArrayList<Student> list = studentDao.getList();
    for (Student student : list) {
      out.printf("%s,%s,%s,%s,%s,%s,%d,%s\n",
        student.getUserId(),
        student.getPassword(),
        student.getName(),
        student.getTel(),
        student.getEmail(),
        ((student.isWorking())?"yes":"no"),
        student.getBirthYear(),
        student.getSchool());
    }
  }
  
  @RequestMapping(value="student.update")
  public void update(
      @RequestParam("userId") String userId,
      @RequestParam("password") String password,
      @RequestParam("name") String name,
      @RequestParam("tel") String tel,
      @RequestParam("email") String email,
      @RequestParam("working") boolean working,
      @RequestParam("birthYear") int birthYear,
      @RequestParam("school") String school, 
      PrintStream out) 
      throws Exception {
    // 주입받은 StudentDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 StudentDao가 주입되어 있어야 한다.
    if (!studentDao.existUserId(userId)) {
      out.println("입력하신 아이디를 찾지 못했습니다.");
      return;
    }
    
    Student student = new Student();
    student.setUserId(userId);
    student.setPassword(password);
    student.setName(name);
    student.setTel(tel);
    student.setEmail(email);
    student.setWorking(working);
    student.setBirthYear(birthYear);
    student.setSchool(school);
    
    studentDao.update(student);
    out.println("학생 정보를 변경하였습니다.");
  }
  
  @RequestMapping(value="student/view")
  public void view(@RequestParam("userId") String userId, PrintStream out) throws Exception {
    // 주입받은 StudentDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 StudentDao가 주입되어 있어야 한다.
    Student student = studentDao.getOne(userId);
//      Student student = studentDao.getListByUserId((paramMap.get("userId"));
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
  }
}
