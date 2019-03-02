package com.cassandradb.demo;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalog.Column;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import com.sparkcassandra.test.PersistUtil;

import scala.reflect.api.TypeTags;

import static org.apache.spark.sql.functions.to_timestamp;
import static org.apache.spark.sql.functions.to_date;


public class DataframeConverter extends PersistUtil{
	
	public static void main(String args[]) throws ParseException {
		
		List<Student> list = new ArrayList<Student>();
		list.add(new Student("Kedar", "0101110", "CS", dateConverter("01-10-1999", 1)));
		list.add(new Student("Virat", "0101116","IT", dateConverter("24-02-2019", 1)));
		list.add(new Student("Rohit", "0101117", "CS", dateConverter("09-09-2001" , 1)));
		list.add(new Student("Aditya", "0101118", "CE", dateConverter("21-09-2018" , 1)));
		
		for (Student  s : list) {
			System.out.println(s.getDateOfBirth());
		}
		
		SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("PERSIST TO DB");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        
        SparkSession session = new SparkSession(sparkContext.sc());
        
        SQLContext sqlContext = session.sqlContext();
        
        /*List<StructField> fields = new ArrayList<StructField>();
       fields.add(new StructField("date" , DataTypes.IntegerType , true ,Metadata.empty()));
        fields.add(new StructField("hours" , DataTypes.IntegerType , true ,Metadata.empty()));
        fields.add(new StructField("minutes" , DataTypes.IntegerType , true ,Metadata.empty()));
        fields.add(new StructField("month" , DataTypes.IntegerType , true ,Metadata.empty()));
        fields.add(new StructField("seconds" , DataTypes.IntegerType , true ,Metadata.empty()));
        fields.add(new StructField("time" , DataTypes.DateType , true ,Metadata.empty()));
        fields.add(new StructField("year" , DataTypes.IntegerType , true ,Metadata.empty()));*/
        
        StructField s1 = new StructField("date", DataTypes.IntegerType, true, Metadata.empty());
        StructField s2 = new StructField("hours", DataTypes.IntegerType, true, Metadata.empty());
        StructField s3 = new StructField("minutes", DataTypes.IntegerType, true, Metadata.empty());
        StructField s4 = new StructField("month", DataTypes.IntegerType, true, Metadata.empty());
        StructField s5 = new StructField("seconds", DataTypes.IntegerType, true, Metadata.empty());
        StructField s6 = new StructField("time", DataTypes.DateType, true, Metadata.empty());
        StructField s7 = new StructField("year", DataTypes.IntegerType, true, Metadata.empty());
        
        StructField [] arr = {s1 , s2 ,s3 , s4 , s5 , s6 , s7};
        
        StructType type = new StructType(arr);
        /*
         * 
         * date: integer (nullable = true)
 |    |-- hours: integer (nullable = true)
 |    |-- minutes: integer (nullable = true)
 |    |-- month: integer (nullable = true)
 |    |-- seconds: integer (nullable = true)
 |    |-- time: long (nullable = true)
 |    |-- year: integer (nullable = true)
         * 
       
       
        StructField s1 = new StructField("firstname", DataTypes.StringType, true, Metadata.empty());
        StructField s2 = new StructField("branch", DataTypes.StringType, true, Metadata.empty());
        StructField s3 = new StructField("dateofbirth", DataTypes.TimestampType, true, Metadata.empty());
        StructField s4 = new StructField("registrationnumber", DataTypes.StringType, true, Metadata.empty());
        StructField [] arr = {s1 ,s2 ,s3 , s4};
        StructType type = new StructType(arr);*/
        

        JavaRDD<Student> students = sparkContext.parallelize(list);
        
        
        
       //javaFunctions(students).writerBuilder("persons", "students", mapToRow(Student.class)).saveToCassandra();
      
        Dataset<Student> studentRdd = sqlContext.createDataset(students.rdd(), Encoders.bean(Student.class));
        
         Dataset<Row> studentDF = studentRdd.toDF();
      
        
       // studentDF = studentDF.select("dateOfBirth.*").select("time");
        
        studentDF = studentDF.select("branch","firstName","registrationNumber","dateOfBirth.*");
        studentDF = studentDF.select("branch","firstName","registrationNumber","time");
        
        
        
        // StructField f = studentDF.select("dateOfBirth").schema().apply(0);
        // System.out.println("Field : "+f);
        
       //studentDF = studentDF.drop("dateOfBirth");
       studentDF = studentDF.withColumnRenamed("firstName", "firstname");
       studentDF = studentDF.withColumnRenamed("registrationNumber", "registrationnumber");
       studentDF = studentDF.withColumnRenamed("time", "dateofbirth");
       
       System.out.println("DF Timestamp");
       studentDF.show(false);
       
        Map<String , String> persistOptions = new HashMap<>();
        configurePersistOptions(sqlContext , "persons" , "students" , persistOptions);
        
        studentDF.write().format(SPARK_CASSANDRA_FORMAT).mode(SaveMode.Append).options(persistOptions).save();
        
        //persist(sqlContext , studentDs , persistOptions);
        //persist(sqlContext , df , persistOptions);
		
		
	}
	
	private static Date dateConverter(String startDate , int sql) throws ParseException {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = sdf1.parse(startDate);
		return date;
	}
	
private static java.sql.Date dateConverter(String startDate) throws ParseException {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = sdf1.parse(startDate);
		java.sql.Date d = new java.sql.Date(date.getTime());
		return d;
	}


}
