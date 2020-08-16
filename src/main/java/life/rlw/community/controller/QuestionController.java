package life.rlw.community.controller;

import life.rlw.community.dto.CommentDTO;
import life.rlw.community.dto.QuestionDTO;
import life.rlw.community.enums.CommentTypeEnum;
import life.rlw.community.service.CommentService;
import life.rlw.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id,
                           Model model
                           ){
        QuestionDTO questionDTO = questionService.getById(id);
        //阅读数

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
