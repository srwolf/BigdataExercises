package com.utad.cassandra.basic;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.ColumnListMutation;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.query.RowQuery;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.util.RangeBuilder;
import com.utad.cassandra.util.Utils;

public class Pagination1 {
	public static void main(String args[]) throws ConnectionException {

int pageSize = 10000;
		
		// crear mutation
		
		for (int i = 1; i <= 1000000; i++) {
			
			// escribir en el mutation
			
			if (i % pageSize == 0) {
				// si hemos completado una página, ejecutar el mutation
				
				// y volver a crear un mutation de nuevo! si no, la siguiente vez
				// volverá a escribir los datos anteriores
			}
		}

		// ejecutar el mutation una última vez para asegurar la escritura de
		// la última página

		System.out.println("finished writing");

		// leer los datos

		System.out.println("finished reading paginated values");
	}
}
