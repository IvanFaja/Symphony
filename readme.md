# Symphony
This is a project that finds expression on web pages.

## How it works

The reads URLs from a text file and applies tree types of filters
 * Extract text values from elements found with ccs selector
 * Extract values of one attributes from all elements
 * Apply a regular expression for refining results
 * Write results in a file for each URL

By default, it uses predefined filters for finding hashtags in the web pages.
This behavior cloud be modified by overriding the default filters passing the following parameters:
```
  --attributes: one or more attributes to get their value
  --text: CSS selector to define the elements to extract the ext values
  --regexp: regular expression applied to the result of previous filters 
```
For example, the following command extracts all the URLs on links on every element and text on the body:
```
    java -jar Symphony.jar --path=/home/test/urls.txt --attributes=href --text=body --regexp=b(?:https?://)?(?:(?i:[a-z]+\.)+)[^\s,]+\b 
```

For optimising the speed of the process this project launch a thread per every core the cpu has

## How to build it

### Requirements
 * Java JDK
 * maven
Run the following command at the root of the project
```
  mvn clean package
```
