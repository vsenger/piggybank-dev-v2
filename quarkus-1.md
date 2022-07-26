
# Cooking Java with Alexa — Ep 1

Creating a modern Java App with Quarkus, Java 17, MySQL, Docker and cool tools.

![](https://cdn-images-1.medium.com/max/2000/1*IuD9ukicYc3tfn-vdRaPmQ.png)

Welcome to **Cooking Java with Alexa**, a series of blog posts where you will learn how to create from scratch a Modern Java Application using **Quarkus** and running on your own machine (which we know always works!), as a container or as a serverless on AWS cloud. Since this post has some commands that you take some minutes to execute, Alexa and I will teach you how to make a **Sunomono Salad** in a very short video so you can make the best usage of your time!

![Delicious Japanese Salad](https://cdn-images-1.medium.com/max/2000/1*rQ8Q3_pfPBWONZDgyIWe3g.jpeg)*Delicious Japanese Salad*

In the past I have developed numerous **Java Enterprise** applications with J2EE and Java EE 5 with application servers such as WebSphere, JBoss, Tomcat and even iPlanet :); and let’s agree: it was a complex task to prepare IDE, application server, local database and others; but now we have a new generation of Java that has changed everything and now it's much much more easy. If you are an old school Java developer, or if you are learning Java now, I’m sure you’re in the right place because we’re going to show you a set of amazing technologies and tools for you to develop professional Java applications for small, medium or huge companies like [amazon.com](https://amazon.com), which by the way is one of the biggest users of Java of the world!

Java Enterprise kept for many years the application called PetStore, that was a reference implementation of the technology, which inspired me a lot to start this project to develop a reference implementation of Cloud Java Modern App, but I believe that, like me, a management application of Petshops is not very useful in my day-to-day. So thinking about some solution that I really need and want to use to improve my life, I realized that something for personal financial management would be excellent: who doesn’t want to control their finances in a more pleasant way, eventually save some money at the end of the day and also learn modern Java?

Let's start it!

### Functional Requirements

We have the following functional requirements for our solution:

1. Manage bank and credit card accounts uniformly, being able to generate expense reports by category with aggregated values of your bank account and credit card information. Calculate account balance individually and grouped.

1. Manage fixed and sporadic expenses / incoming in an intuitive way.

1. Generate expense reports by monthly category.

1. Plan fixed and variable costs based on your history.

### Non-functional Requirements

1. *Reusability*: keep the same Java business components for different architecture and deployment models such as on your own machine, virtual servers, containers and serverless services.

1. *Security*: we are going to use openid to authentication and authorization process.

1. *Scalability*: running on containers or serverless it's easy but in future posts we will share a study cost around scalability.

1. *Sustainability*: we are all looking forward to do more consuming less resources and emitting less carbon in the world, including application.

1. *Infrastructure as code*: being a **modern app** we are not use console to create manually our cloud infrastructure and **AWS Cloud Development Kit** is the way to code how we want to create our infrastructure, in Java sure!

### Tools

We chose open-source, modern and practical tools like Quarkus and SDKman and classic ones like MySQL for our development journey, here’s the complete list of what we’re going to use today:

1. *Github & Gitpod*

1. *AWS Cloud & AWS Cli*

1. *Java 17 — Amazon Corretto*

1. *sdkman*

1. *quarkus*

1. *maven*

1. *RESTEasy*

1. *Docker*

1. *MySQL*

1. *Hibernate*

And as we know that some commands will take some time to run, we will teach you how to cook something new with each blog post and today it will be a simple, easy, tasty and nutritious Japanese cucumber salad recipe: **Sunomono**. We will provide the recipe ingredient list and also a video with Alexa showing how to execute this recipe soon!

## Setup

Here’s a step-by-step guide to prepare your environment to cook java together with Alexa and with me, welcome again to Cooking Java with Alexa.

### Github and Gitpod

We are going to use github to host our code and make it easy to transport our project from our development machine to any other one, including Gitpod.

* Open [github.com](https://github.com) page

* Login or create an account

* Create a new repository *PiggyBank, *choose Java for .gitignore and I like to use MIT license for my open-source projects.

[Gitpod.io](https://gitpod.io) is a very nice online IDE that you can access using any computer with a browser and technically it's a complete virtual machine on the cloud to develop, run and test your Java application. It's a great experience and you have free 50 hours per month free and all you need is your github account to login and a workspace based on your github repository.

* Open [gitpod.io](https://gitpod.io) and create a new workspace

![](https://cdn-images-1.medium.com/max/4328/1*IFsKk6rcaRlaVaXGpzwobA.png)

* Click New Workspace and choose you **PiggyBank** Repository:

![](https://cdn-images-1.medium.com/max/2000/1*NDjX6PBU728APIT5d1ZUXQ.png)

Now you have your virtual Web IDE ready to start developing your Java project, including a terminal where you can type Linux commands to create, develop and run your Java App:

![](https://cdn-images-1.medium.com/max/5048/1*OAabgSxUSrQDAF0hVhPImg.png)

Now let's install some tools using the terminal.

![](https://cdn-images-1.medium.com/max/2000/1*PF_g01aVlDeUv4_WLvEnhA.png)

[SDKMan](https://sdkman.io/) is love in a shape of software. Basically it can install JDK, tools and Java frameworks in a very simple and smooth way. If you never used it before, just follow the instructions bellow and check why it's about love:

* Download and install [SDKMan](https://sdkman.io/)

* **For Macos / Linux**: curl -s “https://get.sdkman.io" | bash

* **To list JDK available:** sdk list java

* **To install Amazon Corretto 17: **sdk install java 17.0.3.6.1-amzn

![](https://cdn-images-1.medium.com/max/3644/1*lMwKQ21WtUV1RMTnqs3ZQQ.png)

Type Y to make Amazon Corretto 17 your default SDK.

* **To install Quarkus: **sdk install quarkus

![](https://cdn-images-1.medium.com/max/3628/1*lGXou1Ykc3NBPCAygjBZzw.png)

* **To install Maven:** sdk install maven

Got it? You don't need to download and setup everything manually! SDKMan can install hundreds of Java tools by typing a simple command, including jMeter for example.

Remember that we are doing on gitpod but you can do the same thing in your computer.

### Quarkus

Quarkus is "*A Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards.*". Quarkus represents very well the new generation of Java and we will explore the features gradually. If you were a Java EE developer you can think about Quarkus as the replacement of the *Application Servers *but if you are new to Java just think about Quarkus as a framework that will make your developer life simple to write Web Apps, REST API's and manage as containers.

Let's start by creating a Quarkus application typing the following commands:

* quarkus create app piggybank:piggybank-api:0.0.1 — java=17 — package-name=piggybank.api

![](https://cdn-images-1.medium.com/max/3620/1*UTRqiSigSZfLjsVW_x9Fig.png)

Now we have a *piggybank-api* directory with a Maven / Quarkus project ready to build and run, let's do it:

* cd piggybank-api

* mvn quarkus:dev

The first steps have been taken and while **Maven** downloads everything it needs to get your project running with **Quarkus**, let’s learn with Alexa how to make the **Japanese Sunomono Salad**.

### Cooking with Alexa Sunomono Salad

<center><iframe width="560" height="315" src="https://www.youtube.com/embed/QhViA4GokNU" frameborder="0" allowfullscreen></iframe></center>

### Running your Quarkus App with Gitpod

Now Maven downloaded all the artifacts we need and already started a Web server listening to the port 8080 and we need to make it public on our Gitpod enviroment.

* Open doors: just click on "Make Public" here:

![](https://cdn-images-1.medium.com/max/2000/1*kanVmFcETf55c3sbcp9dUw.png)

* Click Open Browser and you will see the index.html:

![](https://cdn-images-1.medium.com/max/3660/1*xlkKeqhGi8KFnKumK4kppA.png)

* You can click the button "VISIT THE DEV UI" to see which Quarkus plugins the project is using:

![](https://cdn-images-1.medium.com/max/3196/1*hlnL3wQ9HKH5xGcXO2iX6g.png)

* It also created a Java REST class called GreetingResource.java

![](https://cdn-images-1.medium.com/max/3320/1*LCk5_qjlZeFc4c1RlR8K2A.png)

* Add to the end of your url /hello to access

![](https://cdn-images-1.medium.com/max/3284/1*hROcFvuA3QjeGDPCH1nNpg.png)

* Make any change to the returned message, save it and refresh the browser. You can see the changes without needing to redeploy or restart Quarkus.

### Commit and Push the Project to Github

Now let's commit the project into the github:

* git add *

* git commit -m "initial project"

* git push

![](https://cdn-images-1.medium.com/max/2588/1*nlcn94LHLM0S7vcVz2aF9Q.png)

### Web Static Resources

You can also access, add and modify static files at:

* /src/main/resources/META-INF/resources

### Adding some JSON spicy

Now let's add JSON support and create a method into GreetingResource.java to return a JSON instead of a simple string. To manipulate JSON objects we are going to use resteasy-jackson library by typing the following command:

* quarkus ext add resteasy-jackson

It will just modify your **pom.xml **adding a new dependency to this library that you can also do it manually if you preffer:

    <dependency>

      <groupId>io.quarkus</groupId>

      <artifactId>quarkus-resteasy-jackson</artifactId>

    </dependency>

Now let's create a new method json() to return a JSON object:

    package piggybank.api;

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.MediaType;
    import java.util.Map;

    [@Path](http://twitter.com/Path)("/hello")
    public class GreetingResource {
        [@GET](http://twitter.com/GET)
        [@Path](http://twitter.com/Path)("json")
        [@Produces](http://twitter.com/Produces)(MediaType.APPLICATION_JSON)
        public Map<String, String> json() {
            return Map.of("message", "hello json");
        }

    [@GET](http://twitter.com/GET)
        [@Produces](http://twitter.com/Produces)(MediaType.TEXT_PLAIN)
        public String hello() {
            return "Hello from RESTEasy Reactive Cool!";
        }
    }

Open the browser and add at the end of URL /hello/json to access it:

![](https://cdn-images-1.medium.com/max/3324/1*M_72PKzYF7PfxRSxYiVmsg.png)

### Adding MySQL Database to the project

Let's do this last step of this post to start connecting to MySQL Database and we need to add more dependencies to have a connection pool and JDBC driver:

* **Connection Pool:** quarkus ext add agroal

* **Driver JDBC:** quarkus ext add jdbc-mysql

We are going to create a Java class to have a basic connection / test with MySQL:

    package piggybank.api;

    import javax.inject.Inject;
    import javax.sql.DataSource;
    import javax.ws.rs.GET;
    import javax.ws.rs.Path;
    import javax.ws.rs.WebApplicationException;
    import java.sql.SQLException;

    [@Path](http://twitter.com/Path)("/ds")
    public class DatasourceResource {
        [@Inject](http://twitter.com/Inject)
        DataSource datasource;

    [@GET](http://twitter.com/GET)
        public String getDS() {
            try (var con = datasource.getConnection();
                 var stmt = con.createStatement();
                 var rs = stmt.executeQuery("SELECT 1+1")) {
                rs.next();
                return rs.getString(1);
            } catch (SQLException e) {
                throw new WebApplicationException(e);
            }
        }
    }

Run Quarkus:

* mvn quarkus:dev

Quarkus will magically initialize a docker container with MySQL for development and you don't need to care about it at this moment.

Open the default URL and add **/ds **at the end:

![](https://cdn-images-1.medium.com/max/3276/1*uer4pDj90CWpLhrHYBfwkg.png)

### Conclusion

Now we have our environment ready to go and you may want to commit the project at github to use develop in any computer beyond gitpod. **Quarkus** has a lot of good resources that we will explore in the next blog posts that we are planning:

* Creating the entities classes and database tables

* Packing as Lambda and Container for ECS

* Adding Security.

See you soon!
