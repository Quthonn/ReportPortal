package model;

import java.util.List;

public class BodyResponseModel {
    // ""content.name", dashboardName";
    String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Dashboard> content;

    public List<Dashboard> getContent() {
        return content;
    }

    public void setContent(List<Dashboard> content) {
        this.content = content;
    }
}
