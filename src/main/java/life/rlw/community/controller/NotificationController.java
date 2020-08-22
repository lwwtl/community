package life.rlw.community.controller;

import life.rlw.community.dto.NotificationDTO;
import life.rlw.community.dto.PageDTO;
import life.rlw.community.enums.NotificationEnum;
import life.rlw.community.model.User;
import life.rlw.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;



@Controller
public class NotificationController {
    @Autowired(required = false)
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id")Long id){

        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);
        if(NotificationEnum.REPLY_COMMENT.getType()==notificationDTO.getType()||
        NotificationEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}
