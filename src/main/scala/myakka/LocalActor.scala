package myakka

import scala.concurrent.duration._
import akka.actor._

/**
 * Created by arne on 18.08.14.
 */
class LocalActor extends Actor {
  val path = "akka.tcp://MyAkkaSystem@192.168.110.71:2552/user/remoteActor"
//  val path = "akka.tcp://MyAkkaSystem@127.0.0.1:2552/user/remoteActor"

  sendIdentifyRequest()

  def sendIdentifyRequest(): Unit = {
    context.actorSelection(path) ! Identify(path)
    import context.dispatcher
    context.system.scheduler.scheduleOnce(3.seconds, self, ReceiveTimeout)
  }

  def receive = identifying

  def identifying: Actor.Receive = {
    case ActorIdentity(`path`, Some(actor)) =>
      context.watch(actor)
      context.become(active(actor))
    case ActorIdentity(`path`, None) => println(s"Remote actor not available: $path")
    case ReceiveTimeout => sendIdentifyRequest()
    case _ => println("Not ready yet...")
  }

  def active(actor: ActorRef): Actor.Receive = {
    case "Hello World!" =>
      actor ! "Hello"
    case "World!" =>
      println ("Antwort erhalten!")
    case Terminated(`actor`) =>
      println ("Actor wurde beendet.")
      sendIdentifyRequest()
      context.become(identifying)
    case ReceiveTimeout =>
      // ignore
  }
}
