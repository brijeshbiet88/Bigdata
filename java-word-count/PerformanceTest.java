package com.test.sparkdemo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.util.SizeEstimator;

import com.dataframe.demo.Person;

public class PerformanceTest {
	

	
    private static void wordCount(String path) throws Exception {

    	
    	SparkSession session = SparkSession.builder().config(new SparkConf().registerKryoClasses(new Class[] {Person.class})).master("local").appName("jsonreader").getOrCreate();
    	session.conf().set("spark.kryo.registrator", "com.test.sparkdemo.MyRegistrator");
    	session.conf().set("spark.driver.maxResultSize", "2g");
    	
    	Dataset<Person> personDataSet = session.read().json(path).as(Encoders.bean(Person.class)); 
		Dataset<Person> personDataSet2 = session.read().json(path).as(Encoders.bean(Person.class)); 
		
		personDataSet = personDataSet.unionAll(personDataSet2).unionAll(personDataSet2).unionAll(personDataSet2).unionAll(personDataSet2).unionAll(personDataSet2).unionAll(personDataSet2);
		personDataSet = personDataSet.unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet);
		personDataSet = personDataSet.unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet);
		personDataSet = personDataSet.unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet);
		//personDataSet = personDataSet.unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet).unionAll(personDataSet);
		personDataSet = personDataSet.unionAll(personDataSet);
		
		long startime = System.currentTimeMillis();
    	System.out.println("Start time "+startime);
		JavaRDD<Person> personsRDD = personDataSet.toJavaRDD();
		
		System.out.println("Size Count : "+personsRDD.count());
		
		personsRDD.persist(StorageLevel.MEMORY_ONLY_SER());
		personsRDD.map(
			person -> {
			    Person p = person;
			    p.setAge(person.getAge()+2);
			    p.setCity(person.getCity().toUpperCase());
			    p.setHobby(person.getHobby().toUpperCase());
			    p.setName(person.getName().toUpperCase());
				return p;
			}	
		);
		
		personsRDD.persist(StorageLevel.MEMORY_ONLY_SER());
		
		personsRDD.map(
				person -> {
				    Person p = person;
				    p.setAge(person.getAge()+2);
				    p.setCity(person.getCity().toLowerCase());
				    p.setHobby(person.getHobby().toUpperCase());
				    p.setName(person.getName().toUpperCase());
					return p;
				}	
			);
		
		personsRDD = personsRDD.filter(o -> o != null);
		personsRDD.persist(StorageLevel.MEMORY_ONLY_SER());
		
		personDataSet.show(false);
		
		Dataset<Person> personDF = session.createDataset(personsRDD.rdd() ,  Encoders.bean(Person.class));
		personDF.show(false);
		System.out.println("Size Count : "+personsRDD.count());
		long sizeinKB = SizeEstimator.estimate(personsRDD)/1024;
		System.out.println("Size Space : "+sizeinKB);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Total Time : "+(endTime-startime));
    }

    public static void main(String[] args) throws Exception{

        if (args.length != 0) {
            System.out.println("Use File Provided");
            wordCount(args[0]);
            
        }else {
        	String file = "/home/brijesh/Desktop/data.json";
        	wordCount(file);
        }
    }

}

