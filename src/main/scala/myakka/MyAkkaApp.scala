package myakka

import scala.concurrent.duration._
import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory

/**
 * Created by arne on 18.08.14.
 */
object MyAkkaApp {

  def main(args: Array[String]): Unit = {
    if (args.isEmpty || args.head == "remote")
      startRemoteActor()
    if (args.isEmpty || args.head == "local")
      startLocalActor()
  }

  def startRemoteActor(): Unit = {
    val system = ActorSystem("MyAkkaSystem", ConfigFactory.load("remoteActor"))
    system.actorOf(Props[RemoteActor], "remoteActor")

    println("remote actor - waiting for messages")
  }

  def startLocalActor(): Unit = {
    val system = ActorSystem("MyAkkaSystem", ConfigFactory.load("localActor"))
    val actor = system.actorOf(Props[LocalActor], "localActor")

    println("started local actor")
    import system.dispatcher
    system.scheduler.schedule(1.second, 1.second) {
      actor ! "Hello World!"
    }
  }

}
