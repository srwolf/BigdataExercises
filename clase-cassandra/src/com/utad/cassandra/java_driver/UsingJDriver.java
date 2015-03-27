package com.utad.cassandra.java_driver;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Batch;
import com.utad.cassandra.basic.User;

public class UsingJDriver {

	public static void main(String args[]) {

		Cluster cluster;
		String node = "127.0.0.1";

		cluster = Cluster.builder().addContactPoint(node).build();

		Session session;
		session = cluster.connect();

		try {
			// ejecutamos una query directamente, en este caso, para crear el keyspace
			session.execute("CREATE KEYSPACE utad_cql WITH replication "
					+ "= {'class':'SimpleStrategy', 'replication_factor':3};");

			session.execute("CREATE TABLE utad_cql.users (" + "id int,"
					+ "email text," + "PRIMARY KEY (id));");
		} catch (Exception e) {
			System.out.println("ya existen keyspace y table");
		}

		// podemos usar prepared statements
		PreparedStatement ps1 = session
				.prepare("Insert into utad_cql.users(id, email) values (?, ?)");

		// y por supuesto, escrituras en batch
		BatchStatement batch = new BatchStatement();

		for (int i = 0; i < 1000; i++) {
			batch.add(ps1.bind(i, "user" + i + "@void.com"));
		}

		session.execute(batch);

		ResultSet results = session.execute("SELECT * FROM utad_cql.users ");

		for (Row row : results) {
			System.out.println("id : " + row.getInt("id"));
			System.out.println("email : " + row.getString("email"));
		}
		System.out.println();

		cluster.close();
	}

}
