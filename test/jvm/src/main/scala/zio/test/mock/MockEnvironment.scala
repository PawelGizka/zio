/*
 * Copyright 2017-2019 John A. De Goes and the ZIO Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zio.test.mock

import zio._
import zio.blocking.Blocking
import zio.scheduler.Scheduler

case class MockEnvironment(
  clock: MockClock.Mock,
  console: MockConsole.Mock,
  random: MockRandom.Mock,
  scheduler: MockClock.Mock,
  system: MockSystem.Mock,
  blocking: Blocking.Service[Any],
  nioService: NioExecutorService.Service
) extends Blocking
    with NioExecutorService
    with MockClock
    with MockConsole
    with MockRandom
    with Scheduler
    with MockSystem

object MockEnvironment {

  val Value: Managed[Nothing, MockEnvironment] =
    Managed.fromEffect {
      for {
        clock       <- MockClock.makeMock(MockClock.DefaultData)
        console     <- MockConsole.makeMock(MockConsole.DefaultData)
        random      <- MockRandom.makeMock(MockRandom.DefaultData)
        system      <- MockSystem.makeMock(MockSystem.DefaultData)
        blocking    = Blocking.Live.blocking
        nioExecutor = NioExecutorService.Live.nioService
      } yield new MockEnvironment(clock, console, random, clock, system, blocking, nioExecutor)
    }
}
