# wordEmbeddings

The requirement to use web service   
     Requirement:
        1.	This project use jdk 1.8.0 
        2.	This project use Apache Cassandra: 3.0.1. 
        3.	Eclipse EE 4.6.0

    Usage of the Web Service
        1.	When first time to initialize the project, you need to use maven update this project to import relevant dependencies.
            •	The major dependency is :
            •	Dropwizard :１.0.0
            •	Dropwizard-cassandra :４.0.0
        2.	start DB server before run this project
            •	The way to start DB server is go to Cassandra bin file and run commend line under bin file. Then run "./cassandra -f" . After the server will run in 127.0.0.1  port
            •	The way to stop DB server is to price ctrl+c within the commend line.
        3.	Before run the web service. Configure the Arguments as "server ProjectConfiguation.yml" .
        4.	Then right click WordEmbeddingApplication.java and run this as application






The requirement to add more word embeddings

    The word embeddings file will can be automatically added into Cassandra DB. This function is in ReadFiletoDB.java

    Usage:

    1.	Before in the ReadFiletoDB.java file , change the path of folder variable into the location which contains the word embedding file. In this folder should not contain other files or folders. The format of file name should looks as follow.

     NB. 
        1.	This reading file function will trim the last 18 characters of the file name. For example, the file name will change from “vectors_ap8889_cbow_s100_w5_neg20_hs0_sam1e-4_iter5.txt” to “vectors_ap8889_cbow_s100_w5_neg20_hs0”
        2.	If the table already exist in DB this function will ignore loading the file to DB
    2.	To run this function which is adding more word embeddings is by running this file.  This function will be executed. Each word embedding file will cost around 6 mins depends on the size of the file.
 




 
The requirement of add new operations

    To add new operations means add one more resources within the resources package in the project.
    Usage:
        1.	The simple way is copy one of resources file in resource package and then manipulate this resource file as needed

        2.	Then register this resources to server. 
        •	The regestion is within the run function in the WordEmbeddingApplication.java file. 
        •	The way to register this resources as follow: 

