package life.rlw.community.service;

import life.rlw.community.dto.PageDTO;
import life.rlw.community.dto.QuestionDTO;
import life.rlw.community.exception.CustomizeErrorCode;
import life.rlw.community.exception.CustomizeException;
import life.rlw.community.mapper.QuestionExtMapper;
import life.rlw.community.mapper.QuestionMapper;
import life.rlw.community.mapper.UserMapper;
import life.rlw.community.model.Question;
import life.rlw.community.model.QuestionExample;
import life.rlw.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionExtMapper questionExtMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {

        PageDTO pageDTO = new PageDTO();
        //通过questionMapper拿到totalCount(总条数)的值
        Integer totalPage;

        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
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

        List<Question>questions=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));

        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            //通过question.creator找到user
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        return pageDTO;
    }

    public PageDTO list(Long uesrid, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        //通过questionMapper拿到totalCount(总条数)的值
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(uesrid);
        Integer totalCount=(int)questionMapper.countByExample(questionExample);

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

        QuestionExample example=new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(uesrid);
        List<Question>questions=questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        List<QuestionDTO>questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //通过question.creator找到user
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        return pageDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null)
        {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUN);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else{
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example=new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated=questionMapper.updateByExampleSelective(updateQuestion,example);
            if (updated!=1)
            {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUN);
            }
        }
    }

    public void incView(Long id) {

        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

    }
}
