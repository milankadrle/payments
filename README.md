# Payments

This is a sample payment tracker application which accepts payments (positive or negative) on the command line
and once per minute logs balance for each used currency. Payment is expected as currency and amount pair e.g. EUR 500.
Path to the file with initial payments may be provided as program argument. Such file should contain lines with payments
in the format described above.

 - Any three uppercase characters can be used as currency code.
 - No decimal numbers are allowed in input.
 - Exchange rate is hardcoded for a few currencies only.
 - Error message is logged when user enters invalid input.
 - Program exits when it is started with invalid file name parameter or file is in incorrect format.

## Build
Application is ready to be built using Maven. To build the application run
```
mvn clean install
```
in the root of the application, next to pom.xml. All application dependencies are downloaded and
packed into one FAT jar called **payments-0.0.1-SNAPSHOT.jar**.
This jar is located in the target subdirectory created during build process.

## Running
To run the application go to target subdirectory or copy **payments-0.0.1-SNAPSHOT.jar** anywhere
and run
```
java -jar payments-0.0.1-SNAPSHOT.jar [inputfile]
```
Inputfile parameter is optional.

Now you can add payments by typing currency and amount pairs.
Balances for each currency are listed once per minute.

To quit the application type "quit" and Enter.

## Technology choices
Spring Boot was selected to simply demonstrate Spring Framework usage. Application also uses
Apache Derby in-memory database to employ Spring Data and JPA. Some JUnit tests contains mocking with Mockito.
