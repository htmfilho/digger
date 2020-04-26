# Digger

[![CircleCI](https://circleci.com/gh/htmfilho/digger/tree/master.svg?style=svg)](https://circleci.com/gh/htmfilho/digger/tree/master)
[![Maintainability](https://api.codeclimate.com/v1/badges/d7883e061b0c699fde4f/maintainability)](https://codeclimate.com/github/htmfilho/digger/maintainability) [![Join the chat at https://gitter.im/database-digger/community](https://badges.gitter.im/database-digger/community.svg)](https://gitter.im/database-digger/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Use Digger to document your relational databases. It is a web application that works like a supporting tool for DBAs and Developers, making sure data is well understood before developing software to maintain it. Everybody in the organization can contribute to the knowledge base and make the documentation of large databases in record time possible.

![Screenshot](digger-screenshot.png)

## Running Digger

1. Download the latest release from the [release page][releases].
2. Save the digger-<version>.jar in the folder you want to install it.
3. Run it with the command: `$ java -jar digger-<version>.jar`
4. Open the application by visiting the address https://localhost:8080
 
## Using Digger

Digger is a user friendly tool and you probably can get along by simply using it. But in case you need more detailed information please read Digger's [user guide]. It also provides contextual guidance when you click on the help button on the top right of the user interface.

## Contributing to the Project

Follow these instructions if you want to contribute to Digger.

### Assumptions

We assume your development environment is configured with:
 
 - **Java 8+**: you can perform the commands `java` and `javac` in your terminal
 - **Maven 3**: you can perform the command `mvn` in your terminal
 - **Git**: you can perform the command `git` in your terminal

### Local Environment Setup

We favor the use of the command line to setup the local environment, so we do not depend on any other tool for this basic step. Open the Windows/Linux terminal and start by cloning the repository in your local machine:

    $ cd [your-java-projects-folder]
    $ git clone https://github.com/htmfilho/digger.git

It creates the folder `digger` that contains the entire source code of the application. Execute the following Maven command to build, test, and run the application:

    $ cd digger
    $ mvn spring-boot:run

Visit the local address http://localhost:8080/ to use the application. To stop it, type `Ctrl+C` on the terminal. 

### Development Experience

Your changes to Java files take effect as soon as you save them. Spring DevTools makes sure they are compiled and deployed automatically, so you don't have to do it yourself.

### Deployment

Create a deployment package using Maven:

    $ mvn clean package

It creates a Java standalone application package in the folder `target`.

If the default port `8080` is already in use, set the environment variable `SERVER_PORT` to `8081`.

Run the package to check if everything works:

    $ cd [your-java-projects-folder]/digger
    $ java -jar target/digger-<version>-SNAPSHOT.jar

### Documentation 

There are two types of documentation: the [user guide] and the [project documentation]. To generate the user guide:

    $ asciidoc docs/index.adoc
    
To generate the project documentation:

    $ mvn site:stage -DstagingDirectory=${basedir}/docs/project

### Technologies in Use

 - [Spring MVC]
 - [Spring Security]
 - [Thymeleaf]

[user guide]: https://www.hildeberto.com/digger/
[project documentation]: https://www.hildeberto.com/digger/project/
[releases]: https://github.com/htmfilho/digger/releases
[Spring MVC]: https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html
[Spring Security]: https://spring.io/projects/spring-security
[Thymeleaf]: http://www.thymeleaf.org