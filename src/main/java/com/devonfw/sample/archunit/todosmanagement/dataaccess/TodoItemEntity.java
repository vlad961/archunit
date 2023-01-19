package com.devonfw.sample.archunit.todosmanagement.dataaccess;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.devonfw.sample.archunit.general.dataaccess.ApplicationPersistenceEntity;
import com.devonfw.sample.archunit.todosmanagement.common.TodoItem;

/**
 * {@link TodoItem} implementation as {@link ApplicationPersistenceEntity}.
 */
@Entity
@Table(name = "todos")
public class TodoItemEntity extends ApplicationPersistenceEntity implements TodoItem {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    
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
}
