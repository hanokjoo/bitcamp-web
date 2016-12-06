/*목적: javax.servlet.Servlet 인터페이스의 4개 메서드를 미리 구현하여 서브 클래스에게 상속해주는 용도로 사용한다. 
 *      서브 클래스는 Servlet 인터페이스를 직접 구현하는 것 보다, 이 클래스를 상속받게 되면
 *      오직 service() 메서드만 구현하면 되기 때문에 편리하다. 
 */
package bitcamp.java89.ems.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public abstract class AbstractServlet implements Servlet {
  ServletConfig config;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    this.config = config;
  }

  @Override
  public ServletConfig getServletConfig() {
    return this.config;
  }

  @Override
  public String getServletInfo() {
    return this.getClass().getName();
  }

  @Override
  public void destroy() {}
}
