package com.sparkcassandra.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
//import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapRowTo;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

public class RDDCassandaDemo extends PersistUtil{
	
	public static void main(String args[]) {
		
		List<Employee> list = new ArrayList<>();
		list.add(new Employee("Manish", "Tiwari", 30, "Manthan", "ETL", "20180516"));
		list.add(new Employee("Manish", "Tiwari", 30, "Manthan", "ETL", "20180518"));
		list.add(new Employee("Brijesh", "Gupta", 28, "Jpmc", "Java", "20170518"));
		list.add(new Employee("Shubhada", "Nayak", 25, "Exotel", "Java", "20190114"));
		list.add(new Employee("Suvankar", "Giri", 30, "Cisco BSFT", "Full Stack", "20160428"));
		
		SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("PERSIST TO DB");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        
        SparkSession session = new SparkSession(sparkContext.sc());
        
        SQLContext sqlContext = session.sqlContext();
        
        

        JavaRDD<Employee> personRdd = sparkContext.parallelize(list);
        
        javaFunctions(personRdd).writerBuilder("persons", "employee", mapToRow(Employee.class)).saveToCassandra();
        
        Dataset<Employee> epmDS = sqlContext.createDataset(personRdd.rdd(), Encoders.bean(Employee.class));
        epmDS.show(false);
        
        
        Map<String , String> persistOptions = new HashMap<>();
        configurePersistOptions(sqlContext , "persons" , "employee" , persistOptions);
        
        persist(sqlContext , epmDS , persistOptions);
        	
	}

}
