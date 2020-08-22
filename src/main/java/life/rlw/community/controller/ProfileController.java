package life.rlw.community.controller;

import life.rlw.community.dto.PageDTO;
import life.rlw.community.model.User;
import life.rlw.community.service.NotificationService;
import life.rlw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired(required = false)
    private QuestionService questionService;

    @Autowired(required = false)
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){

        User user=(User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDTO pagination = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",pagination);
        }else if("replies".equals(action)){
            PageDTO pagination = notificationService.list(user.getId(),page,size);
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("pagination",pagination);
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("sectionName","最新回复");
        }


        return "profile";
    }
}
