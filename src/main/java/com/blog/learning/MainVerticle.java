package com.blog.learning;

import com.blog.learning.beans.PersonService;
import com.blog.learning.controllers.ServiceEndpoint;
import io.micronaut.context.BeanContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    BeanContext beanContext = BeanContext.run();

    Router mainRouter = beanContext.streamOfType(ServiceEndpoint.class)
        .collect(() -> Router.router(vertx),
          (r,s) -> {
            r.route()
              .handler(LoggerHandler.create())
              .handler(BodyHandler.create())
              .handler(StaticHandler.create());
//              .failureHandler(this::handleFailure);
            r.route("/api/*").subRouter(s.router(vertx));
          }, (r1, r2) -> {});

    vertx.createHttpServer().requestHandler(mainRouter).listen(7777, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 7777");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
