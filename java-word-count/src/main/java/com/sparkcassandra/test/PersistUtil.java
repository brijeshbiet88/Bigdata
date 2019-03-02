package com.sparkcassandra.test;

import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.storage.StorageLevel;

public class PersistUtil {
	
	protected static final String SPARK_CASSANDRA_FORMAT = "org.apache.spark.sql.cassandra";

	protected static void configurePersistOptions(SQLContext sqlContext, String keyspace, String table, Map<String, String> persistOptions) {
		persistOptions.put("KEYSPACE", keyspace);
		persistOptions.put("TABLE", table);
		persistOptions.put("spar.cassandra.output.ttl", "600");
		persistOptions.put("confirm.truncate", "true");
		
	}
	
	protected static <T> void persist(SQLContext sqlContext, Dataset<T> ds, Map<String, String> persistOptions) {
		persistToDB(sqlContext ,ds , SaveMode.Overwrite , persistOptions);
		
	}

	protected static<T> void persistToDB(SQLContext sqlContext, Dataset<T> ds, SaveMode saveMode,	Map<String, String> persistOptions) {
		Dataset<Row> df = 
		ds.toDF().persist(StorageLevel.MEMORY_AND_DISK());
		df.write().format(SPARK_CASSANDRA_FORMAT).mode(saveMode).options(persistOptions).save();
	}

}
