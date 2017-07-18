# RestFul web service on WordEmbeddings functions

A little RestFul web service built by Dropwizard with a Non-Sql database (**Cassandra**) to provide **wordEmbeddings** functions.

Four functions are included, which are: 

* Query wordEmbeddings
* Similarity of two words
* Nearest words
* Word analogy





[Here](http://bionlp-www.utu.fi/wv_demo/) is a website style demo from [Turku BioNLP Group](http://bionlp.utu.fi/)

To simplify running test, the jar files are provided in folder *jar_file*. Only the folder *jar_file* is needed to run this demo.


 
## Two step to Run Demo

### Prerequisites
* Download the folder *jar_file* 
* Install Cassandra (3.0.8)
* Download [sample 67M](http://www.zuccon.net/adcs2015_ntlm/w2v_embeddings/vectors_ap8889_skipgram_s200_w20_neg20_hs0_sam1e-4_iter5.txt.tar.gz) and place into a folder (e.g. a folder named *wordEmbeddingsData*)
* Install Java(1.8.0_101) if necessary 

### Running 
* Open Cassandra server : `./cassandra -f`

* Load data into Cassandra : `java -jar ReadData.jar ./wordEmbeddingsData`

* To running the restful service: 
`java -jar ./WordEmbeddingService.jar server ProjectConfiguation.yml`

### Usage test


* query wordEmbeddings
	* URL : `http://localhost:8080/Get_Vec?fileName=vectors_ap8889_skipgram_s200_w20_neg20_hs0&queryWord=apple`
	* Result : 
	![1](https://user-images.githubusercontent.com/16515626/28260285-aa59c4f2-6b1d-11e7-8450-6a45f32c95a0.png)
* words similarity
	* URL : `http://localhost:8080/Cal_MosrSimilarWords?fileName=vectors_ap8889_skipgram_s200_w20_neg20_hs0&queryWord=apple`
	* Result :![2](https://user-images.githubusercontent.com/16515626/28260384-29edb4c6-6b1e-11e7-982a-cf983c7df45e.png)
* most similar words
	* URL : `http://localhost:8080/Cal_Similarity?fileName=vectors_ap8889_skipgram_s200_w20_neg20_hs0&queryWord1=cat&queryWord2=dog
`
	* Result : ![3](https://user-images.githubusercontent.com/16515626/28260577-07172a8a-6b1f-11e7-9c5e-bd0bc03ebb4e.png)

* words analogy
	* URL : `http://localhost:8080/Cal_analogyWords?fileName=vectors_ap8889_skipgram_s200_w20_neg20_hs0&startWord=man&endWord=woman&queryWord=king
`	 
	* Result : ![4](https://user-images.githubusercontent.com/16515626/28260579-0a3b9764-6b1f-11e7-88e8-a40c39b9cec4.png)

## Built With
* Database : Cassandra (3.0.8)
* DB driver : dropwizard-cassandra (4.0.0)
* Web Framework : dropwizard (1.0.0)
* Maven (4.0.0)
* Java (1.8.0_101)
* [sample 67M](http://www.zuccon.net/adcs2015_ntlm/w2v_embeddings/vectors_ap8889_skipgram_s200_w20_neg20_hs0_sam1e-4_iter5.txt.tar.gz) from [Dr. Zuccon](http://www.zuccon.net/ntlm.html)





 

