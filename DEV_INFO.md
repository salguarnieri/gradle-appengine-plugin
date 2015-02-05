![Google Cloud Platform Logo](https://cloud.google.com/_static/images/gcp-logo.png)
# Developer Info

## Contributing

Want to contribute? Great! First, read this page (including the small print at the end).

#### Before you contribute
Before we can use your code, you must sign the
[Google Individual Contributor License Agreement](https://developers.google.com/open-source/cla/individual?csw=1)
(CLA), which you can do online. The CLA is necessary mainly because you own the
copyright to your changes, even after your contribution becomes part of our
codebase, so we need your permission to use and distribute your code. We also
need to be sure of various other things—for instance that you'll tell us if you
know that your code infringes on other people's patents. You don't have to sign
the CLA until after you've submitted your code for review and a member has
approved it, but you must do it before we can put your code into our codebase.
Before you start working on a larger contribution, you should get in touch with
us first through the issue tracker with your idea so that we can help out and
possibly guide you. Coordinating up front makes it much easier to avoid
frustration later on.

#### Code reviews
All submissions, including submissions by project members, require review. We
use Github pull requests for this purpose.

#### The small print
Contributions made by corporations are covered by a different agreement than
the one above, the Software Grant and Corporate Contributor License Agreement.


## Building

The gradle-appengine-plugin **requires** a jdk7 or higher to build.

After cloning the repository from github, you can build the plugin using the
version of gradle packaged with the plugin (`./gradlew` or `gradle.bat`), you may
optionally build a snapshot version which will add the suffix -SNAPSHOT to the
jar.

```
./gradlew build [snapshot]
```

#### Using a development build

You can install a copy of your custom built plugin to the local maven repository
on your system (`$HOME/.m2`) and reference in your App Engine project via a 
modified build file.

- Install your snapshot (will build it if necessary)
```
./gradlew install snapshot
```

- Modify your build file to use your local snapshot
```gradle
buildscript {
    repositories {
        mavenLocal()
        mavenCentral() // or jcenter() for remote dependencies
    }
    dependencies {
        classpath "com.google.appengine:gradle-appengine-plugin:1.9.17-SNAPSHOT"
    }
}
```
##### Getting rid of that pesky bootstrap class warning

If you have not configured the location of your jdk6 for tooling classes, you will see
```
warning: [options] bootstrap class path not set in conjunction with -source 1.6
1 warning
```
because the tooling jars and classes are built to be compatible with java6

To get rid of this warning, add a gradle.properties file in the project root with the contents
```
systemProp.jdk6.home=/absolute/path/to/my/java6-jdk
```
