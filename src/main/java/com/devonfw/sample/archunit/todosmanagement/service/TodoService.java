package com.devonfw.sample.archunit.todosmanagement.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.devonfw.sample.archunit.todosmanagement.common.TodoEto;
import com.devonfw.sample.archunit.todosmanagement.logic.UcDeleteTodoItem;
import com.devonfw.sample.archunit.todosmanagement.logic.UcFindTodoItem;

/**
 * Rest service for todo component with {@link com.devonfw.sample.archunit.todosmanagement.common.TodoItem}.
 */
@Path("/todo")
@ApplicationScoped
public class TodoService {
  @Inject
  UcFindTodoItem ucFindTodoItem;

  @Inject
  UcDeleteTodoItem ucDeleteTodoItem;
  /**
  * @param id the {@link TaskItemEto#getId() primary key} of the {@link TaskItemEto} to find.
  * @return the {@link TaskItemEto} for the given {@code id}.
  */
  @GET
  @Path("/todo/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public TodoEto findTaskItem(@PathParam("id") Long id) {
    TodoEto item = this.ucFindTodoItem.findById(id);
    if (item == null) {
      throw new NotFoundException("TaskItem with id " + id + " does not exist.");
    }
        return item;
    }

  /**
  * @param id the {@link TodoEto#getId() primary key} of the {@link TodoItemEto} to delete.
  */
  @DELETE
  @Path("/todo/{id}")
  public void deleteTaskItem(@PathParam("id") Long id) {

    this.ucDeleteTodoItem.delete(id);
  }

  @Transactional
  public void createTodo(TodoEto todo) {
  
  }    
}
