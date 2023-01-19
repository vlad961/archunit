package com.devonfw.sample.archunit.todosmanagement.common;

import com.devonfw.sample.archunit.general.common.ApplicationEntity;

public interface TodoItem extends ApplicationEntity{
    void setId(Long id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);
}
