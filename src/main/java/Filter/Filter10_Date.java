package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;

@WebFilter(filterName = "Filter 10",urlPatterns = {"/*"})
//打印请求资源的名字和时间的过滤器
public class Filter10_Date implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //打印过滤器开始
        System.out.println("Filter 1 - date begins");
        //将请求转换成http
        HttpServletRequest request = (HttpServletRequest)req;
        //获取请求路径
        String path = request.getRequestURI();
        //创建日期对象
        Calendar cal = Calendar.getInstance();
        //创建时间
        String time = cal.get(Calendar.YEAR) + " 年 " + (cal.get(Calendar.MONTH) + 1) + " 月 " + cal.get(Calendar.DATE) + "日" + cal.get(Calendar.HOUR_OF_DAY) + ": " + cal.get(Calendar.MINUTE);
        //打印时间
        System.out.println(path + " @ " + time);
        //放行
        chain.doFilter(req,resp);
        //打印过滤器结束
        System.out.println("Filter 1 - date ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
