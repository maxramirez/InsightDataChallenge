Insight Data Engineering - Coding Challenge
===========================================================

For this coding challenge, you will develop tools that could help analyze the community of Twitter users.  For simplicity, the features we will build are primitive, but you could easily build more complicated features on top of these.   

## Challenge Summary

This challenge was to implement two features:

1. Clean and extract the text from the raw JSON tweets that come from the Twitter Streaming API, and track the number of tweets that contain unicode.
2. Calculate the average degree of a vertex in a Twitter hashtag graph for the last 60 seconds, and update this each time a new tweet appears.

## Details of Solution

The solution implements an extensible approach for tweet stream processing, this process is separated in several steps:

- A twitterProcessor object must be configured. The configuration includes
	- Selected variables to be parsed
	- Filters that filter the text field of the twitter stream
	- Features that make up the twitter processing system.
- The twitter stream is parsed from the json stream, using a Json library (json.org), and then the selected fields are extracted.
- Each twitter object text field is filtered by all twitterProcessor filters.
- Each twitter object is finally passed to all Feature objects, they implement components like writing the twitter string to disk, or building the hashtag Graph.

## First Feature

The first feature is implemented by

 - Attaching a textFilter, that filters unicode character and count them, using regex.
 - Attaching a textFilter, that filters special line spaces.
 - Attaching a twitterWritter, that writes to disk the output of the tweet stream in the desired format.

## Second Feature
The second feature continually updates a graph that contains relationships between hashtags. Its implemented by:

 - Attaching a twitter AverageDegreeCalculator, that holds a 60 second tweet stream window, the corresponding graph, and generates an AverageDegree stream.
 - Implementing a AverageDegreeWriter that saves the AverageDegree stream into disk.

The graph is build by keeping reference to all nodes, and all edges when a tweet is added in the tweet stream window, all corresponding edges are incremented, creating nodes if necessary.
When a tweet is removed from the tweet window, all corresponding edges are decreased, deleting edges and nodes if necessary.

The average degree is calculated with the following expression:

 AvgDegree=totalEdges*2/totalNodes.

Instructions
--

To run this challenge Just run the run.sh file.
It also can be run with parameters using java

java -jar Feature1.jar input.txt output.txt
java -jar Feature2.jar input.txt output.txt

About  compiling
--
It can also be run as an Idea project and is build on java 8.
