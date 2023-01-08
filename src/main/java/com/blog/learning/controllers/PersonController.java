package com.blog.learning.controllers;

import com.blog.learning.beans.PersonService;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Singleton;
import netscape.javascript.JSObject;

@Singleton
public class PersonController implements ServiceEndpoint {

  private PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }
  @Override
  public Router router(Vertx vertx) {
    Router router = Router.router(vertx);

    router.get(path("/name")).handler(this::getName);
    return router;
  }

  private void getName(RoutingContext routingContext) {
    String name = this.personService.getName();

    final HttpServerResponse response = routingContext.response();
    final JsonObject result = new JsonObject().put("result", name);

    response.putHeader("Accept", "application/json");
    response
      .setStatusCode(200)
      .end(result.encode());
  }

  private String path(String action) {
    return  "/person" + action;
  }
}
