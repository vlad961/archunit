package com.devonfw.sample.archunit.todosmanagement.logic;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.sample.archunit.todosmanagement.common.TodoEto;
import com.devonfw.sample.archunit.todosmanagement.dataaccess.TodoItemEntity;
import com.devonfw.sample.archunit.todosmanagement.dataaccess.TodoItemRepository;

/**
 * Use-Case to find {@link TaskItemEntity task-items}.
 */
@ApplicationScoped
@Named
@Transactional
public class UcFindTodoItem {
    @Inject
    TodoItemRepository todoItemRepository;

    @Inject
    TodoItemMapper taskItemMapper;
  
    /**
     * @param itemId the {@link TodoItemEntity#getId() primary key} of the {@link TodoItemEntity} to find.
     * @return the {@link TodoEto} with the given {@link TodoEto#getId() primary key} or {@code null} if not
     *         found.
     */
    // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_TASK_ITEM)
    public TodoEto findById(Long itemId) {
  
      Optional<TodoItemEntity> item = this.todoItemRepository.findById(itemId);
      return item.map(entity -> this.taskItemMapper.toEto(entity)).orElse(null);
    }
}
