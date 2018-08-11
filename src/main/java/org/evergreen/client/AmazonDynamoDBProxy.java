package org.evergreen.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.*;

public class AmazonDynamoDBProxy {
    private DynamoDB dynamoDB;

    public AmazonDynamoDBProxy(final Regions regions) {
        dynamoDB = new DynamoDB(regions);
    }

    public Item retrieveItem(final String tableName, final PrimaryKey primaryKey) {
        final Table table = dynamoDB.getTable(tableName);
        return table.getItem(primaryKey);
    }

    public void updateItemStringAttribute(final String tableName, final PrimaryKey primaryKey,
                                          final String stringAttributeName, final String stringAttributeValue) {
        final Table table = dynamoDB.getTable(tableName);
        table.updateItem(primaryKey, new AttributeUpdate(stringAttributeName).put(stringAttributeValue));
    }
}
