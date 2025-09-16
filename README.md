E-Health wallet
REST API service for processing requests for reimbursement
of Medicines/Medical Procedures

1. Setup dev properties and run locally:
Setup in application.properties:
swagger.server.url=http://localhost:8080
and comment prod swagger.server.url
(temp solution, dev/prod profiles will be added soon together with update from basic auth to jwt)

Generate sources and build the JAR:
mvn clean compile package

1.1. Run locally:
mvn spring-boot:run

1.2. Build and run in docker container - go to app folder
docker build -t e-health-wallet-1.0.0 .
docker run -d -p 8080:8080 e-health-wallet-1.0.0

To stop docker container:
docker ps
docker stop <CONTAINER ID>

To browse pending reimbursements locally go to:
http://localhost:8080/
To browse swagger UI locally go to:
http://localhost:8080/swagger-ui/index.html


2. To see app deployed on railway browse:
https://e-health-wallet.up.railway.app
After login you will get view of pending reimbursement requests, you are able to approve/reject reimbursement requests
Link to browse swagger doc:
https://e-health-wallet.up.railway.app/swagger-ui/index.html

Find testing postman collection in /test/postman_test_collection folder

3. Frontend - run locally:
go to ./frontend/e-health-wallet
run ng serve
browse: http://localhost:4200/
after basic form login see review of reimbursement requests with description for approvement/rejection
or go to /add to add new reimbursement request or delete one
4. To see deployed angular part of app, go to: 
https://e-health-wallet.netlify.app/login