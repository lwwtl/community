package life.rlw.community.controller;

import life.rlw.community.dto.PageDTO;
import life.rlw.community.dto.QuestionDTO;
import life.rlw.community.mapper.QuestionMapper;
import life.rlw.community.mapper.UserMapper;
import life.rlw.community.model.Question;
import life.rlw.community.model.User;
import life.rlw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;


    @GetMapping("/")
//    访问首页时，循环获取到cookie，拿到token的值去数据库中查找，如果有把user放到index.html的session中
    public String index(HttpServletRequest request,
                        Model model,
                        //获取页码和分页数
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
                        ){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null &&cookies.length!=0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        //将参数传到service
        PageDTO pagination=questionService.list(page,size);
        model.addAttribute("pagination",pagination);

        return "index";}
}
