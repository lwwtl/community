package life.rlw.community.service;

import life.rlw.community.dto.NotificationDTO;
import life.rlw.community.dto.PageDTO;
import life.rlw.community.dto.QuestionDTO;
import life.rlw.community.enums.NotificationEnum;
import life.rlw.community.mapper.NotificationMapper;
import life.rlw.community.mapper.UserMapper;
import life.rlw.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired(required = false)
    private NotificationMapper notificationMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public PageDTO list(Long userId, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();
        //通过questionMapper拿到totalCount(总条数)的值
        Integer totalPage;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount=(int)notificationMapper.countByExample(notificationExample);

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

        NotificationExample example=new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);

        List<Notification> notifications=notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        if(notifications.size()==0){
            return pageDTO;
        }
        List<NotificationDTO>notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setType(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }


        pageDTO.setData(notificationDTOS);
        return pageDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        return notificationMapper.countByExample(notificationExample);
    }
}
