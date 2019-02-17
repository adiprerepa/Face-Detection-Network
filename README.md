Web Cam Face Detection Project

- Client: When motion is detected, images from webcam are sent to local server.
- Local server recieves image, then inserts raw images taken on client into a MySQL database with JDBC.
- Then, the server uses the Google Cloud Vision API which detects faces. 
- The images in which faces are detected are sent to another machine running a website, which posts the images and other info.

Database schema : 

One table with a Blob data type

To make the table and corresponding database in MySQL : 

CREATE DATABASE WebCamImages;
USE WebCamImages;
CREATE TABLE PIC( img BLOB );


READ THIS FOR MORE INFO : 
Documentation : https://docs.google.com/presentation/d/1kMARlNVwAXfZMxKK6mEsEL2aXA9WmWdGyEggzcdBu9w/edit?usp=sharing


- Aditya Prerepa 2/15/19 - Server Design, Face Detection, Front end Receiving, MySQL Schema
- Akshay Trivedi - Webcam Client
- Ishan Jain - Website Design
