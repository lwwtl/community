package life.rlw.community.dto;

import java.util.List;

public class TagDTO {
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "categoryName='" + categoryName + '\'' +
                ", tags=" + tags +
                '}';
    }

    private String categoryName;
    private List<String>tags;

}
