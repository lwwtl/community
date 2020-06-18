package life.rlw.community.service;

import life.rlw.community.dto.PageDTO;
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

    public PageDTO list(Integer page, Integer size) {

        PageDTO pageDTO = new PageDTO();
        //通过questionMapper拿到totalCount(总条数)的值
        Integer totalPage;

        Integer totalCount=questionMapper.count();
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size + 1;
        }
        //将这些参数传给dto进行分页处理
        //页数异常处理
        if(page<1){page=1;}
        if(page>totalPage){page=totalPage;}
        pageDTO.setPagination(totalPage,page);

        //size*(page-1)
        Integer offset = size*(page-1);
        List<Question>questions=questionMapper.list(offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            //通过question.creator找到user
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        return pageDTO;
    }

    public PageDTO list(Integer uesrid, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        //通过questionMapper拿到totalCount(总条数)的值
        Integer totalPage;

        Integer totalCount=questionMapper.countByUserId(uesrid);
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size + 1;
        }
        //将这些参数传给dto进行分页处理
        //页数异常处理
        if(page<1){page=1;}
        if(page>totalPage){page=totalPage;}
        pageDTO.setPagination(totalPage,page);

        //size*(page-1)
        Integer offset = size*(page-1);
        List<Question>questions=questionMapper.listByUserId(uesrid,offset,size);
        List<QuestionDTO>questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //通过question.creator找到user
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
