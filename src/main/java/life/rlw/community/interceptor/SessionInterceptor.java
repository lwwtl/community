package life.rlw.community.interceptor;

import life.rlw.community.mapper.UserMapper;
import life.rlw.community.model.User;
import life.rlw.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired(required = false)
    private UserMapper userMapper;

    //请求地址时统一通过cookie拿到token，通过token查出user对象放入session
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Cookie[] cookies = request.getCookies();
            if(cookies!=null &&cookies.length!=0)
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        UserExample userExample = new UserExample();
                        userExample.createCriteria()
                                .andTokenEqualTo(token);
                        List<User> users=userMapper.selectByExample(userExample);
                        if (users.size() != 0) {
                            request.getSession().setAttribute("user", users.get(0));

                        }
                        break;
                    }
                }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
