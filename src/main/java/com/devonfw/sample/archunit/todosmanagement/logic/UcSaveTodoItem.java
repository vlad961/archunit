package com.devonfw.sample.archunit.todosmanagement.logic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.todosmanagement.common.TodoItemEto;
import com.devonfw.sample.archunit.todosmanagement.dataaccess.TodoItemEntity;
import com.devonfw.sample.archunit.todosmanagement.dataaccess.TodoItemRepository;

@ApplicationScoped
@Named
@Transactional
public class UcSaveTodoItem {
    
    @Inject
    TodoItemRepository todoItemRepository;

    @Inject
    TodoItemMapper todoItemMapper;

    /**
    * @param item the {@link TaskItemEto} to save.
    * @return the {@link TaskItemEntity#getId() primary key} of the saved {@link TaskItemEntity}.
    */
    // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_TASK_ITEM)
    public Long save(TodoItemEto item) {
        TodoItemEntity entity = this.todoItemMapper.toEntity(item);
        entity = this.todoItemRepository.save(entity);
        return entity.getId();
    }
}
