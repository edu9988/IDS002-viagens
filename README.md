# IDS002-viagens

## Operations supported

| HTTP | URL | Payload | Description
| :--- | :--- | :--- | :---
| GET | /api/customers | - | Get all Customers
| GET | /api/customers/\<id\> | - | Get a single Customer
| POST | /api/customers | json | Create new Customer
| PUT | /api/customers/\<id\> | json | Update Customer
| PATCH | /api/customers/\<id\> | json | Update Customer's Status
| DELETE | /api/customers/\<id\> | - | Remove Customer
|  |  |  | 
| GET | /api/locations | - | Get all Locations
| GET | /api/locations/\<id\> | - | Get a single Location
| POST | /api/locations | json | Create new Location
| PUT | /api/locations/\<id\> | json | Update Location
| DELETE | /api/locations/\<id\> | - | Remove Location
|  |  |  | 
| GET | /api/travels | - | Get all Travels
| GET | /api/travels/\<id\> | - | Get a single Travel
| POST | /api/travels | json | Create new Travel
| PUT | /api/travels/\<id\> | json | Update Travel
| PATCH | /api/travels/\<id\> | json | Update Travel's Dates
| DELETE | /api/travels/\<id\> | - | Remove Travel

Where json payloads are of the form (ommiting `id` attribute that goes in responses)

`Customer`
```javascript
{
  "name"        : String,
  "lastname"    : String,
  "address"     : String,
  "city"        : String,
  "state"       : String,
  "country"     : String,
  "birthDate"   : LocalDate (java.time),
  "limitAmount" : BigDecimal (java.math),
  "status"      : Status (enum)
}
```

`Location`
```javascript
{
  "name"    : String,
  "nickname": String,
  "address" : String,
  "city"    : String,
  "state"   : String,
  "country" : String
}
```

`Travel`
```javascript
{
  "orderNumber"     : String,
  "amount"          : BigDecimal (java.math),
  "source"          : Location,
  "destination"     : Location,
  "startDateTime"   : LocalDateTime (java.time),
  "endDateTime"     : LocalDateTime (java.time),
  "type"            : TripType (enum),
  "customer"        : Customer
}
```
