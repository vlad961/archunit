package com.devonfw.sample.archunit.todosmanagement.logic;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.devonfw.sample.archunit.todosmanagement.common.TodoItemEto;
import com.devonfw.sample.archunit.todosmanagement.dataaccess.TodoItemEntity;

/**
 * {@link Mapper} for {@link com.devonfw.sample.archunit.task.common.TodoItem}.
 */
@Mapper(componentModel = "cdi")
public interface TodoItemMapper {
    /**
   * @param items the {@link List} of {@link TodoItemEntity items} to convert.
   * @return the {@link List} of converted {@link TodoItemEto}s.
   */
  default List<TodoItemEto> toEtos(List<TodoItemEntity> items) {

    List<TodoItemEto> etos = new ArrayList<>(items.size());
    for (TodoItemEntity item : items) {
      etos.add(toEto(item));
    }
    return etos;
  }

  /**
   * @param item the {@link TaskItemEntity} to map.
   * @return the mapped {@link TaskItemEto}.
   */
  TodoItemEto toEto(TodoItemEntity item);

  /**
   * @param item the {@link TaskItemEto} to map.
   * @return the mapped {@link TaskItemEntity}.
   */
  TodoItemEntity toEntity(TodoItemEto item);
    
}
