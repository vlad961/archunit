package com.devonfw.sample.archunit.todosmanagement.common;

import com.devonfw.sample.archunit.general.common.AbstractEto;

/**
 * {@link TodoItem} implementation as {@link AbstractEto}.
 */
public class TodoEto extends AbstractEto implements TodoItem {
    private Long id;
    private String name;
    private String description;
    private Integer version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
