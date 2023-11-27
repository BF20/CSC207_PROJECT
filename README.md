# CSC207_PROJECT: PROGRESS PAL!
CSC207 Project at the University of Toronto. Includes the code for the project and related assignment work. 

## Purpose
*Progress Pal!* is an application which allows users to track the completion of daily habits. Every user has an account. 
Users can join a group of users. Each group has a "score" which is calculated from the total number of hours logged. 
Users and groups can also see graphs of interesting statistics related to their progress. ChatGPT also provides words 
of "encouragement" based upon how much time is spend on completing habits. 

## Project Structure
The project is contained in "habit Builder Project". Inside this directory there are two folders: "data" which contains 
the user data for the app in the form of a .yaml file, and "src" which contains the app source code. 

## Design
The project makes use of three APIs. *SnakeYAML* is a module which allows for the reading and writing of .yaml files. 
This neat human-readable file type stores all the user data. *JavaGrapher* is a module which makes very nice graphs for 
displaying data. Both these APIs are local. The ChatGPT API is an online API using HTTPS, and it is where we source
ChatGPTs wonderful commentary. 


