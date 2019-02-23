package com.sparksql.demo;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataFrameDemo {

	public static void main(String[] args) {
		SparkSession session = SparkSession.builder().master("local").appName("jsonreader").getOrCreate();
		Dataset<Row> list = session.read().option("multiLine", true).json("/home/brijesh/programming/personal/java-word-count/java-word-count/data.json");
		list.show(false);
		
		System.out.println("-------------City-------------");
		
		list.select("city").show(false);
		

	}

}
