package zio

import java.util.concurrent.ExecutorService

import zio.internal.PlatformLive

trait NioExecutorService extends Serializable {
  val nioService: NioExecutorService.Service
}

object NioExecutorService extends Serializable {

  trait Service extends Serializable {
    val executorService: ExecutorService
  }

  trait Live extends NioExecutorService {
    override val nioService = new Service {
      override val executorService: ExecutorService = PlatformLive.DefaultExecutorService
    }
  }

  object Live extends Live

}
