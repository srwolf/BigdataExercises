package com.utad.cassandra.basic;

import java.util.ArrayList;
import java.util.List;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.query.RowQuery;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.util.RangeBuilder;
import com.utad.cassandra.util.Utils;

public class Reading7 {

public static void main(String args[]) throws ConnectionException {
		
		List<String> ids = new ArrayList<String>();
		ids.add("1");
		ids.add("3");
		ids.add("5");
		ids.add("7");
		ids.add("11");
	}
}
