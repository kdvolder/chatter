Chatter
=======

Yet another simple chat application.

Doing this as an exercise to try and put together a few things and learn how to
use them (together).

 - spring-boot 'microservices'
 - spring-cloud-stream
 - rabbitmq message broker service (installed locally via docker, or remotely as a CF service)
 - simple javascript single-page app for the UI.
 - websockets to pass messages from/to the web-ui client.
 - webjars to provide some of the js dependencies (e.g. jquery, sockjs)
  
Components:
-----------

This example consists of a few simple spring-boot apps or 'micro-services'. Each of the
apps can be run separately, but they are meant to be run together. The apps communicate
with eachother via spring-cloud-stream messages.

###chatter-bot:

The `chatter-bot` is a 'headless' application. It is a bot which continually sends
numbered 'ping' messages to the chat channel at regular intervals.

###chatter-log:

The `chatter-log` is a 'headless' application. It connects to the 'chat' channel 
and logs all received messages at level 'INFO' to its slf4j logger.

###chatter-web-ui:

The `chatter-web-ui` provides a simple web-ui to a user. In the 'backend' it 
also connects to the 'chat' channel and relays messages from/to this channel 
via websockets to/from the web ui.

RabbitMQ
--------

The different apps communicate with eachother via spring-cloud-stream-rabbitmq. RabbitMQ must be installed.
An easy way to do this locally is using docker via this command:

    docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 -p 61613:61613 kdvolder/rabbitmq-stomp

Once this is running, you can access the management-web-ui on port 15672. Spring-boot default autoconfiuration will also pick up this
installation and use if automatically from localhost. If you are on a Mac / Windows you may need to use boot2docker VM. This may add 
some extra complications. For example, the rabbitmq will not be accessible on localopst directly. So, you will have to 
find out the container's ip address and define `spring.rabbitmq.host` property to point to it (e.g you can add this to the
`application.yml` files of the apps, or set a env var SPRING_RABBITMQ_HOST.

Running from STS:
-----------------

Import the app as 'exsiting maven projects'. When you run the apps locally, each app must be assigned different local
ports.  In the boot-dash, select each app and click the pencil icon. Assign a port by overriding the `server.port` property.
We suggest setting the 'chatter-log' and 'chatter-bot' apps `server.port=0`. This will instruct spring-boot to pick
a port dynamically. Leave the 'chatter-web' as default, and it will run on port 8080.

When ports are selected properly, you can select all three apps in the dash and click the 'start' button. 


 
