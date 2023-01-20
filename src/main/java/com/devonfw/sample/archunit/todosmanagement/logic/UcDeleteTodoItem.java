package com.devonfw.sample.archunit.todosmanagement.logic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.sample.archunit.todosmanagement.common.TodoItemEto;
import com.devonfw.sample.archunit.todosmanagement.dataaccess.TodoItemRepository;

@ApplicationScoped
@Named
@Transactional
public class UcDeleteTodoItem {
    
    private static final Logger LOG = LoggerFactory.getLogger(UcDeleteTodoItem.class);

    @Inject
    TodoItemRepository todoItemRepository;

        /**
     * @param itemId the {@link TodoItemEntity#getId() primary key} of the {@link TodoItemEntity} to find.
     * @return the {@link TodoItemEto} with the given {@link TodoItemEto#getId() primary key} or {@code null} if not
     *         found.
     */
    // @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_TASK_ITEM)
    public void delete(Long itemId) {
        this.todoItemRepository.deleteById(itemId);
    }

    public void delete(TodoItemEto item) {
        Long id = item.getId();

        if(id == null) {
            LOG.info("TodoItem {} ist transient und kann nicht gelöscht werden", item.getName());
        }
        this.todoItemRepository.deleteById(item.getId());
    }

}
