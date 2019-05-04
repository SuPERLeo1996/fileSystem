package com.server.file.DTO;

/**
 * @Auther: Leo
 * @Date: 2019/5/4
 * @Description:
 */
public class TypeDTO {
    private Integer id ;
    private String text;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
