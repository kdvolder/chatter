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

The `chatter-ping-bot` is a 'headless' application. It is a bot which continually sends
numbered 'ping' messages to the chat channel at regular intervals.

##chatter-command-bot:

The `chatter-command-bot` is a 'headless' application. It is a bot which listens for 
incoming messages of the form `!<command-name> <argument>`. When a command is recognized
it is parsed and passed to a command handler. The handler typically reads the arguments
and then sends some messages on the channel to answer the user's request.

Commands and handlers are configured via `application.yml`.

##chatter-banner-service:

This is a rest service that creates ascii banners. It is used by the handler for the `!banner` command
in the command-bot.

###chatter-log:

The `chatter-log` is a 'headless' application. It connects to the 'chat' channel 
and logs all received messages at level 'INFO' to its slf4j logger.

###chatter-web-ui:

The `chatter-web-ui` provides a simple web-ui to a user. In the 'backend' it 
also connects to the 'chat' channel and relays messages from/to this channel 
via websockets to/from the web ui.

###chatter-eureka:

This is a `eureka-server` application. Its needed for the chatter-bot to be able
to discover where the chatter-banner-service(s) is/are.

RabbitMQ
--------

The different apps communicate with eachother via spring-cloud-stream-rabbitmq. RabbitMQ must be installed.
An easy way to do this locally is using docker via this command:

    docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 -p 61613:61613 kdvolder/rabbitmq-stomp

Once this is running, you can access the management-web-ui on port 15672. Spring-boot default autoconfiuration will 
also pick up this installation and use if automatically from localhost. If you are on a Mac / Windows you may need 
to use boot2docker VM. This may add some extra complications. For example, the rabbitmq will not be accessible on localopst directly. So, you will have to  find out the container's ip address and define `spring.rabbitmq.host` property to point to it (e.g you can add this to the `application.yml` files of the apps, or set a env var SPRING_RABBITMQ_HOST.

Running from STS:
-----------------

Import the app as 'exsiting maven projects'. Select all 6 app in the Boot Dash View. Click the 'Start' button.

Note: When you run the apps locally, each app must be assigned different local
port.  The apps are already set up so they satisfy this requirement. The 'web-ui' app just runs on the default port (8080).
The `chatter-eureka` runs on the default port for eureka, `8761`. The other apps have `server.port=0` in their application.yml files. This instructs spring-boot to pick a port dynamically.

### Note: Why do we need to pick port for the headless apps? 

This is because spring-cloud-stream implicitly turns the app into a webby app. Some useful info is being served on actuator endpoints. For example, try accessing `/health` and see what it returns.

Deploying to CF from STS:
-------------------------

Everything is setup so you should be able to simply drag/drop to CF and things should work. There are however two things you
have to do first.

1) create a rabbit-mq service on your space and call if `my-rabbit`. The `manifest.yml` included with each app refers to this
service if it needs access to rabbitmq.

2) Search and replace the name `eureka-kdv` which I have chosen as the hostname for the eureka server. You will have choose
something else.

Once you have done that, drag/drop all the apps on a CF instance and it should all start up correctly.
 
