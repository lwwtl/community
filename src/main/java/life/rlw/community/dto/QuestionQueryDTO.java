package life.rlw.community.dto;

public class QuestionQueryDTO {
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "QuestionQueryDTO{" +
                "page=" + page +
                ", size=" + size +
                ", search='" + search + '\'' +
                '}';
    }

    private Integer page;
    private Integer size;
    private String search;
}
