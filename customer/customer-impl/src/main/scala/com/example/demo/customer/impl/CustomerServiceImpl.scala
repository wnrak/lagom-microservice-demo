package com.example.demo.customer.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.example.demo.customer.api.CustomerService

/**
  * Implementation of the CustomerService.
  */
class CustomerServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends CustomerService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the customer entity for the given ID.
    val ref = persistentEntityRegistry.refFor[CustomerEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id, None))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the customer entity for the given ID.
    val ref = persistentEntityRegistry.refFor[CustomerEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }
}
