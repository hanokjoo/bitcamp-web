package bitcamp.java89.ems.controller;

import java.io.PrintStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bitcamp.java89.ems.dao.ContactDao;
import bitcamp.java89.ems.server.annotation.RequestMapping;
import bitcamp.java89.ems.server.annotation.RequestParam;
import bitcamp.java89.ems.vo.Contact;

@Component
public class ContactController {
  // 의존 객체 DAO를 저장하기 위한 변수
  // => 직접 클래스 이름을 명시하기보다 인터페이스 변수를 선언하는 것이 향후 확장성에 좋다.
  //    왜? 이 변수에 다양한 객체를 넣을 수 있기 때문이다.
  @Autowired ContactDao contactDao;
  
  @RequestMapping(value="contact/add")
  public void add(
      @RequestParam("name") String name,
      @RequestParam("position") String position,
      @RequestParam("tel") String tel,
      @RequestParam("email") String email,
      PrintStream out)
      throws Exception {
    if (contactDao.existEmail(name)) {
      out.println("같은 이메일이 존재합니다. 등록을 취소합니다.");
      return;
    }
    
    Contact contact = new Contact();
    contact.setName(name);
    contact.setPosition(position);
    contact.setTel(tel);
    contact.setEmail(email);
    
    contactDao.insert(contact);
    out.println("등록하였습니다.");
  }
  
  @RequestMapping(value="contact/delete")
  public void delete(@RequestParam("email") String email, PrintStream out) throws Exception {
    // 주입받은 contactDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    if (!contactDao.existEmail(email)) {
      out.println("해당 데이터가 없습니다.");
      return;
    }
    contactDao.delete(email);
    out.println("해당 데이터 삭제 완료하였습니다.");
  }
  
  @RequestMapping(value="contact/list")
  public void list(PrintStream out) throws Exception {
    // 주입받은 contactDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    ArrayList<Contact> list = contactDao.getList();
    for (Contact contact : list) {
      out.printf("%s,%s,%s,%s\n",
        contact.getName(),
        contact.getPosition(),
        contact.getTel(),
        contact.getEmail());
    }
  }
  
  @RequestMapping(value="contact/update")
  public void update(
      @RequestParam("name") String name,
      @RequestParam("position") String position,
      @RequestParam("tel") String tel,
      @RequestParam("email") String email, 
      PrintStream out) 
      throws Exception {
    // 주입받은 contactDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    if (!contactDao.existEmail(email)) {
      out.println("이메일을 찾지 못했습니다.");
      return;
    }
    Contact contact = new Contact();
    contact.setEmail(email);
    contact.setName(name);
    contact.setPosition(position);
    contact.setTel(tel);
    
    
    contactDao.update(contact);
    out.println("변경 하였습니다.");
  }
  
  @RequestMapping(value="contact/view")
  public void view(@RequestParam("name") String name, PrintStream out) throws Exception {
    // 주입받은 contactDao를 사용할 것이기 때문에 더이상 이 메서드에서 ContactDao 객체를 준비하지 않는다.
    // => 단 이 메서드가 호출되기 전에 반드시 ContactDao가 주입되어 있어야 한다.
    ArrayList<Contact> list = contactDao.getListByName(name);
    for (Contact contact : list) {
      out.println("--------------------------");
      out.printf("이름: %s\n", contact.getName());
      out.printf("직위: %s\n", contact.getPosition());
      out.printf("전화: %s\n", contact.getTel());
      out.printf("이메일: %s\n", contact.getEmail());
    } 
  }
}
