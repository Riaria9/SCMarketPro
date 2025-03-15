# Set up
- Create the database in MySQL using the markplace(2).sql script
- Install the Spring Boot Extension Pack on VSCODE (https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack)
- Install the Extension Pack for Java on VSCODE (https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- Find the application.properties file in the backend files and change the username and pasword if it differs from yours
-   ```
    spring.datasource.url=jdbc:mysql://localhost/marketplace
    spring.datasource.username=root
    spring.datasource.password=root
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    
    aws.accessKeyId=AKIAWX6U3TP2YSVQW7X7
    aws.secretKey=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

    ```

# Starting the server
- Find the DemoApplication.java file and click run
- The server should start on port 8080
  
