package com.devonfw.sample.archunit.todosmanagement.service;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.devonfw.sample.archunit.todosmanagement.common.TodoItemEto;
import com.devonfw.sample.archunit.todosmanagement.logic.UcDeleteTodoItem;
import com.devonfw.sample.archunit.todosmanagement.logic.UcFindTodoItem;
import com.devonfw.sample.archunit.todosmanagement.logic.UcSaveTodoItem;

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

  @Inject
  UcSaveTodoItem ucSaveTodoItem;
  /**
  * @param id the {@link TaskItemEto#getId() primary key} of the {@link TaskItemEto} to find.
  * @return the {@link TaskItemEto} for the given {@code id}.
  */
  @GET
  @Path("/todo/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public TodoItemEto findTaskItem(@PathParam("id") Long id) {
    TodoItemEto item = this.ucFindTodoItem.findById(id);
    if (item == null) {
      throw new NotFoundException("TaskItem with id " + id + " does not exist.");
    }
        return item;
    }

  /**
  * @param id the {@link TodoItemEto#getId() primary key} of the {@link TodoItemEto} to delete.
  */
  @DELETE
  @Path("/todo/{id}")
  public void deleteTaskItem(@PathParam("id") Long id) {

    this.ucDeleteTodoItem.delete(id);
  }

  /**
   * @param taskList the {@link TaskListEto} to save (insert or update).
   * @return response
   */
  @POST
  @Path("/todo")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response saveTask(@Valid TodoItemEto todo) {
    Long todoId = this.ucSaveTodoItem.save(todo);
    if (todo.getId() == null || todo.getId() != todoId) {
      return Response.created(URI.create("/todo/" + todoId)).build();
    }
    return Response.ok().build();
  } 
}
