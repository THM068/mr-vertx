package com.blog.learning.controllers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public interface ServiceEndpoint {

  Router router(Vertx vertx);
}
