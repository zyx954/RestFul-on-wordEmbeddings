# RestFul web service on WordEmbeddings functions

This project use **dropwizard** to build the RestFul web service. The precalculated **wordEmbeddings** will be stroed into a Non-Sql database (**Cassandra**). 

There are four functions are implemented, which are: 

* query wordEmbeddings
* words similarity
* most similar words
* words analogy

To simplify runing test, the jar files are provided in folder *jar_file*. Only the folder *jar_file* is needed to run this demo.


 
## Two step to Run Demo
###Prerequisites
* Download the folder *jar_file* 
* Install Cassandra (3.0.8)
* Download [sample 67M](http://www.zuccon.net/adcs2015_ntlm/w2v_embeddings/vectors_ap8889_skipgram_s200_w20_neg20_hs0_sam1e-4_iter5.txt.tar.gz) and please into a folder (e.g. a folder named *wordEmbeddingsData*)
* Install Java(1.8.0_101) if necessary 

###Runing 
* Open Canssandra server : `./cassandra -f`

* Load data into cassandra : `java -jar ReadData.jar ./wordEmbeddingsData`

* To runing the restful service: 
`java -jar ./WordEmbeddingService.jar server ProjectConfiguation.yml`

### Usage test


* query wordEmbeddings
	* URL : `http://localhost:8080/Get_Vec?fileName=vectors_ap8889_skipgram_s200_w20_neg20_hs0&queryWord=apple`
	* result : 
	![Alt text](images/1.png?raw=true "Optional Title")
* words similarity
* most similar words
* words analogy
[![solarized dualmode](https://github.com/altercation/solarized/raw/master/img/solarized-yinyang.png)](#features)
```
Give an example
```

## Built With
* Database : Cassandra (3.0.8)
* DB driver : dropwizard-cassandra (4.0.0)
* Web Framework : dropwizard (1.0.0)
* Maven (4.0.0)
* Java (1.8.0_101)
* [sample 67M](http://www.zuccon.net/adcs2015_ntlm/w2v_embeddings/vectors_ap8889_skipgram_s200_w20_neg20_hs0_sam1e-4_iter5.txt.tar.gz) from [Dr. Zuccon](http://www.zuccon.net/ntlm.html)





 
