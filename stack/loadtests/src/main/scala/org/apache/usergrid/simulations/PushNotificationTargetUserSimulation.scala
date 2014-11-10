/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.usergrid.simulations

import com.ning.http.client.AsyncHttpClient
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.apache.usergrid.scenarios._
import org.apache.usergrid.settings.Settings
import scala.concurrent.duration._
import org.apache.usergrid.helpers.Setup

class PushNotificationTargetUserSimulation extends Simulation {


  before{
    Setup.setupOrg()
    Setup.setupApplication()
    Setup.setupNotifier()
    Setup.setupUsers()
  }
  setUp(
    NotificationScenarios.createScenario
      .inject(constantUsersPerSec(Settings.maxPossibleUsers) during (Settings.duration)) // wait for 15 seconds so create org can finish, need to figure out coordination
      .throttle(reachRps(Settings.throttle) in ( Settings.rampTime.seconds))
      .protocols( Settings.httpConf.acceptHeader("application/json"))
  )

}
