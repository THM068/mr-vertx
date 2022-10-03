package com.blog.learning.beans;

import jakarta.inject.Singleton;

@Singleton
public class PersonService {

  public String getName() {
    return "My name is Vertx";
  }
}
