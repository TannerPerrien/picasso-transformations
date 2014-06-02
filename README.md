Picasso Transformations
=======================

A transformation library providing a variety of image transformations for [Picasso](https://github.com/square/picasso).

![](picasso-transformations-sample.png)

What's Included
---------------

* A variety of filters from [JH Labs Image Filters](http://www.jhlabs.com/ip/filters/index.html)
 * Color Adjustments
 * Blurring / Sharpening
 * Distortion / Warping
 * Effects
 * Edge Detection
* Other

Download / Install
------------------

This jar is not yet available on Maven Central. You must clone the repository and build the project.

    git clone https://github.com/TannerPerrien/picasso-transformations
    cd picasso-transformations
    mvn install

Two options:

- Copy `picasso-transformations-x.x.x.jar` from the target directory of the `picasso-transformations` module into your project.
- Add the dependency to your project.

*Requires local artifact installation, as described above with `mvn install`*

    <dependency>
      <groupId>com.picassotransformations</groupId>
      <artifactId>picasso-transformations</artifactId>
      <version>1.0.0</version>
    </dependency>

Sample App
----------

Install the Sample app to see the filters in action. Long press an image to see a Toast describing the filter.

### Requirements to Build Sample

1. A defined ANDROID_HOME environment variable
2. Android Support Repository (SDK Manager)
3. Maven 3.0.4+

### Clone / Build / Install

    git clone https://github.com/TannerPerrien/picasso-transformations
    cd picasso-transformations
    mvn package
    adb install picasso-transformations-sample/target/picasso-transformations-sample-1.0.0.apk

License
-------

    Copyright (C) 2014 Tanner Perrien

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
