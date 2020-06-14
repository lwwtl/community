package life.rlw.community.service;

import life.rlw.community.dto.QuestionDTO;
import life.rlw.community.mapper.QuestionMapper;
import life.rlw.community.mapper.UserMapper;
import life.rlw.community.model.Question;
import life.rlw.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
//service起到一个组装的作用，当一个请求需要question和user时，需要一个中间层，这个中间层就是service
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question>questions=questionMapper.list();
        List<QuestionDTO>questionDTOList=new ArrayList<>();
        for (Question question : questions) {
            //通过question.creator找到user
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
