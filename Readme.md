
Gemeinsames jar erzeugen mit 

./activator
> assembly

Aufruf:

java -jar myFirstAkka-assembly-1.1.jar

oder einzeln:
java -jar myFirstAkka-assembly-1.1.jar remote
java -jar myFirstAkka-assembly-1.1.jar local
 
Überschreiben der Konfiguration (z.B. Ändern der IP-Adresse):

$ cat config/common.conf 
akka {

  actor {
    provider = "akka.remote.RemoteActorRefProvider"
  }

  remote {
    netty.tcp {
      hostname = "192.168.110.71"
    }
  }
}

java -cp config:myFirstAkka-assembly-1.1.jar myakka.MyAkkaApp remote