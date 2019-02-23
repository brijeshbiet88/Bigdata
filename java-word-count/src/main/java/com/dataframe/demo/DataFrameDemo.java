package com.dataframe.demo;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataFrameDemo {
	
	public static void main(String [] args) {
		SparkSession session = SparkSession.builder().master("local").appName("jsonreader").getOrCreate();
		String path = "/home/brijesh/programming/personal/java-word-count/java-word-count/data.json";
		Dataset<Person> personDataSet = session.read().json(path).as(Encoders.bean(Person.class)); 
		personDataSet.show(false);
		Dataset<Row> df = personDataSet.toDF();
		df.show();
		
	}

}
