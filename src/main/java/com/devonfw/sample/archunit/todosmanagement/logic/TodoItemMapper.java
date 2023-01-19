package com.devonfw.sample.archunit.todosmanagement.logic;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.devonfw.sample.archunit.todosmanagement.common.TodoEto;
import com.devonfw.sample.archunit.todosmanagement.dataaccess.TodoItemEntity;

/**
 * {@link Mapper} for {@link com.devonfw.sample.archunit.task.common.TodoItem}.
 */
@Mapper(componentModel = "cdi")
public interface TodoItemMapper {
    /**
   * @param items the {@link List} of {@link TodoItemEntity items} to convert.
   * @return the {@link List} of converted {@link TodoEto}s.
   */
  default List<TodoEto> toEtos(List<TodoItemEntity> items) {

    List<TodoEto> etos = new ArrayList<>(items.size());
    for (TodoItemEntity item : items) {
      etos.add(toEto(item));
    }
    return etos;
  }

  /**
   * @param item the {@link TaskItemEntity} to map.
   * @return the mapped {@link TaskItemEto}.
   */
  TodoEto toEto(TodoItemEntity item);

  /**
   * @param item the {@link TaskItemEto} to map.
   * @return the mapped {@link TaskItemEntity}.
   */
  TodoItemEntity toEntity(TodoEto item);
    
}
