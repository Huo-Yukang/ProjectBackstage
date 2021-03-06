package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//验证过滤器
@WebFilter(filterName = "Filter 20",urlPatterns = {"/*"})
public class Filter20_LoginSession implements Filter {
    public void destroy() {
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 20 - LoginSessionFilter begin!");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();
        if (path.contains("/login.ctl") ||
                path.contains("/AdministratorLogin.ctl") ||
                path.contains("/logout.ctl") ||
                path.contains("/index.html") ||
                path.contains("Reception")) {//let login and logout go
            chain.doFilter(req, resp);
            System.out.println("Filter 20 - LoginSessionFilter ends!");
        }else  if (session != null && session.getAttribute("currentUser") != null){
            chain.doFilter(req,resp);
            System.out.println("Filter 20 - LoginSessionFilter ends!");
        }else {
            response.getWriter().println("您没有登录，请登录");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
