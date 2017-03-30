package com.example.demo.customerstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.demo.customerstream.api.CustomerStreamService
import com.example.demo.customer.api.CustomerService

import scala.concurrent.Future

/**
  * Implementation of the CustomerStreamService.
  */
class CustomerStreamServiceImpl(customerService: CustomerService) extends CustomerStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(customerService.hello(_).invoke()))
  }
}
