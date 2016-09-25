// create database 'blipstore' and collection 'purchase'
use blipstore
db.createCollection("purchase")

// fill collection with test data
db.getCollection("purchase").insert([{
	"id" : NumberLong(1000),
    "productType" : "jeans",
    "expires" : ISODate("2016-09-30T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(1),
        "description" : "straight blue jeans with low waist",
        "quantity" : 1,
        "value" : 69.99
    }
}, {
    "id" : NumberLong(1001),
    "productType" : "jacket",
    "expires" : ISODate("2016-09-19T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(2),
        "description" : "black leather jacket",
        "quantity" : 1,
        "value" : 239.99
    }
}, {
    "id" : NumberLong(1002),
    "productType" : "shirt",
    "expires" : ISODate("2016-10-04T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(3),
        "description" : "white/red checkered shirt",
        "quantity" : 1,
        "value" : 49.99
    }
}, {
    "id" : NumberLong(1003),
    "productType" : "shoes",
    "expires" : ISODate("2016-09-22T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(4),
        "description" : "black shoes",
        "quantity" : 1,
        "value" : 109.99
    }
}, {
    "id" : NumberLong(1004),
    "productType" : "snickers",
    "expires" : ISODate("2016-10-05T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(5),
        "description" : "white snickers with orange stripe",
        "quantity" : 2,
        "value" : 29.99
    }
}, {
    "id" : NumberLong(1005),
    "productType" : "shirt",
    "expires" : ISODate("2016-09-20T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(6),
        "description" : "solid light blue shirt",
        "quantity" : 1,
        "value" : 49.99
    }
}, {
    "id" : NumberLong(1006),
    "productType" : "jeans",
    "expires" : ISODate("2016-10-01T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(7),
        "description" : "light yellow jeans",
        "quantity" : 1,
        "value" : 79.99
    }
}, {
    "id" : NumberLong(1007),
    "productType" : "t-shirt",
    "expires" : ISODate("2016-10-02T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(8),
        "description" : "red t-shirt with Rolling Stones symbol",
        "quantity" : 3,
        "value" : 39.99
    }
}, {
    "id" : NumberLong(1008),
    "productType" : "sweater",
    "expires" : ISODate("2016-09-21T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(9),
        "description" : "solid grey sweater",
        "quantity" : 2,
        "value" : 59.99
    }
}, {
    "id" : NumberLong(1009),
    "productType" : "shoes",
    "expires" : ISODate("2016-10-05T23:59:59.000Z"),
    "purchaseDetails" : {
        "id" : NumberLong(10),
        "description" : "bright red high heel shoes",
        "quantity" : 1,
        "value" : 99.99
    }
}]);