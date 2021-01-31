# FeedOn 

<img src="src/main/webapp/assets/Banner.png">

FeedOn is a web application serves as a dual platform for F&Bs and charities to connect with one another. It aims to minimize food wastage by redistributing excess food from F&Bs to charities. 

Watch the live demo for the application <a href="https://www.youtube.com/watch?v=xlqRs-MqeXA&feature=youtu.be">here</a>, and view our team proposal <a href="https://github.com/anushadatta/FeedOn/blob/master/src/main/webapp/assets/FeedOn.pptx">here.</a> 

### MOTIVATION 

As we know, our world is battling high food wastage, widespread hunger and extensive food insecurity. These are all problems that directly affect the basic quality of life. Moreover, higher food wastage also means that we would need to build more waste disposal facilities, which leads to an unnecessary increase energy consumption. 

However, by distributing these food leftovers to people who are unable to afford food on their own, we can alleviate their standard of living. So the value of this product is the possibility of solving all the mentioned problems, without even increasing the food output. 

### KEY USER GROUPS

* Food & Beverage organisations
* Food Charities 

### FEATURES

<ol>
    <li>User Authentication</li>
    <li>Main Page </li>
    <ol>
        <li>Donation Matches</li>
        <li>Charities</li>
        <li>Restaurants </li>
    </ol>
    <li>Donation Form/Inbox </li>
</ol>

### TEAM 

<b>Project Advisor:</b> Rashi Karanpuria, Google 

* Anusha Datta
* Anh Huynh
* Shengjing Zhang
* Yew Onn Khaw

# Dev Guide
This is a generated App Engine Standard Java application from the appengine-standard-archetype archetype.

See the [Google App Engine standard environment documentation][ae-docs] for more
detailed instructions.

[ae-docs]: https://cloud.google.com/appengine/docs/java/

- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Maven](https://maven.apache.org/download.cgi) (at least 3.5)
- [Google Cloud SDK](https://cloud.google.com/sdk/) (aka gcloud)

## Setup

    gcloud init
    gcloud auth application-default login

## Maven

### Running locally

    mvn appengine:devserver

### Deploying

    mvn appengine:update

## Testing

    mvn verify

As you add / modify the source code (`src/main/java/...`) it's very useful to add
[unit testing](https://cloud.google.com/appengine/docs/java/tools/localunittesting)
to (`src/main/test/...`). The following resources are quite useful:

- [Junit4](http://junit.org/junit4/)
- [Mockito](http://mockito.org/)
- [Truth](http://google.github.io/truth/)

## Updating to latest Artifacts

An easy way to keep your projects up to date is to use the maven [Versions plugin][versions-plugin].

    mvn versions:display-plugin-updates
    mvn versions:display-dependency-updates
    mvn versions:use-latest-versions

Note - Be careful when changing `javax.servlet` as App Engine Standard uses 3.1 for Java 8, and 2.5
for Java 7.

Our usual process is to test, update the versions, then test again before committing back.

[plugin]: http://www.mojohaus.org/versions-maven-plugin/
