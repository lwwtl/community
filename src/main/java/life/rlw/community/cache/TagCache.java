package life.rlw.community.cache;

import life.rlw.community.dto.TagDTO;
import org.h2.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","java","node","python","c","c++"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","struts","vue","express","yii","koa"));
        tagDTOS.add(framework);
        return tagDTOS;
    }
    public static String filterInvalid(String tags){
        String[] split = StringUtils.arraySplit(tags, '#',true);
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> tagList.contains(t)).collect(Collectors.joining("#"));
        return invalid;
    }
}
