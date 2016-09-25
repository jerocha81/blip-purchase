===============================================
 How to test Blip Purchase Service application
===============================================

NOTE: The following instructions apply to a Windows environment. Please make sure you perform the necessary adaptations 
	  (especially directory paths) if you're about to run the application on a different operating system.

1) 	Download and install Mongo server. Create the default database folder 'C:\data\db' if it doesn't exist already.

2) 	Download and install Robomongo, Mongo Chef, or another visual Mongo database client.

3) 	Create a new database called 'blipstore' and a new collection named 'purchase' in it. 
	Fill the collection with test data by opening JavaScript file 'blip-purchase/conf/mongo/insert_data.js' and running it.
   
4) 	Compile and package the project using Maven (install it if necessary). Make sure you have connectivity to the internet for
	updating your Maven repository. 
	IMPORTANT: To guarantee the compatibility with some of the needed libraries, please point the environment variable JAVA_HOME 
	to a JDK 1.7 installation directory.

		> cd blip-purchase
		> mvn clean install
 
5) 	Start MongoDB daemon on a new terminal by executing the following command:
		
		> cd blip-purchase\run
		> mongo.bat
		
6)	Run the REST Blip Purchase Service application using the following command:
	
		> cd blip-purchase\src\blip-purchase-service\run
		> blip-purchase-service.bat
		
	The Dropwizard-embedded Jetty server should run immediately and will be listening for connections on port 9090.
	
7)	Download Postman add-on for Google Chrome and import the 'blip-purchase/doc/BlipPurchase.postman.collection' file.
	You can now perform both requested operations using the provided examples!
