package life.rlw.community.controller;

import life.rlw.community.dto.PageDTO;
import life.rlw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {


    @Autowired(required = false)
    private QuestionService questionService;


    @GetMapping("/")
//    访问首页时，循环获取到cookie，拿到token的值去数据库中查找，如果有把user放到index.html的session中
    public String index(
                        Model model,
                        //获取页码和分页数
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search
                        ){

        //将参数传到service
        PageDTO pagination=questionService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        return "index";}
}
