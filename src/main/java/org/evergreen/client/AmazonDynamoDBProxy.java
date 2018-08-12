package org.evergreen.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AmazonDynamoDBProxy {
    private DynamoDB dynamoDB;
    private Map<String, Table> tableMap = new LinkedHashMap<>();

    public AmazonDynamoDBProxy(final Regions regions) {
        dynamoDB = new DynamoDB(regions);
    }

    public Item retrieveItem(final String tableName, final PrimaryKey primaryKey) {
        final Table table = getTable(tableName);
        return table.getItem(primaryKey);
    }

    public void updateItemStringAttribute(final String tableName, final PrimaryKey primaryKey,
                                          final String stringAttributeName, final String stringAttributeValue) {
        final Table table = getTable(tableName);
        table.updateItem(primaryKey, new AttributeUpdate(stringAttributeName).put(stringAttributeValue));
    }

    public void updateItemStringListAttribute(final String tableName, final PrimaryKey primaryKey,
                                     final String stringListAttributeName, final List<String> stringListValue) {

        final Table table = getTable(tableName);
        table.updateItem(primaryKey, new AttributeUpdate(stringListAttributeName).put(stringListValue));
    }

    public Table getTable(final String tableName) {
        if (!tableMap.containsKey(tableName)) {
            tableMap.put(tableName, dynamoDB.getTable(tableName));
        }
        return tableMap.get(tableName);
    }
}
