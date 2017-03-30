package com.example.demo.customerstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.demo.customerstream.api.CustomerStreamService
import com.example.demo.customer.api.CustomerService
import com.softwaremill.macwire._

class CustomerStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new CustomerStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new CustomerStreamApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[CustomerStreamService]
  )
}

abstract class CustomerStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[CustomerStreamService].to(wire[CustomerStreamServiceImpl])
  )

  // Bind the CustomerService client
  lazy val customerService = serviceClient.implement[CustomerService]
}
