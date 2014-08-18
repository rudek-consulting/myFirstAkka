package myakka

import akka.actor.Actor

/**
 * Created by arne on 18.08.14.
 */
class RemoteActor extends Actor {
  def receive = {
    case "Hello" =>
      println ("received 'Hello'")
      sender() ! "World!"
  }
}
