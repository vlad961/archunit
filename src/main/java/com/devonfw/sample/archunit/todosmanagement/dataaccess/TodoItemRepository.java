package com.devonfw.sample.archunit.todosmanagement.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Interface for the {@link JpaRepository} giving database access to {@link TodoItemEntity}.
 */
public interface TodoItemRepository extends JpaRepository<TodoItemEntity, Long> {
      /**
   * @param name - the {@link TodoItemEntity#getName() name}.
   * @return the {@link List} with all matching {@link TodoItemEntity} hits.
   */
  @Query("SELECT item FROM TodoItemEntity item WHERE item.name = :name")
  List<TodoItemEntity> findByName(@Param("name") String name);
}
