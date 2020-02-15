# Create RestApi protected by Azure Authentication

1. Create manually a simple Azure AD (or B2C, both are similar) (or reuse exisgin one) with service principal used later on for terraforming.
   1. The principal should have 'contributor' role on the Azure AD/B2C so **do net save its credentials in the repo**. It is used for initial terraforming only.
2. Use terraform scripts to create two user groups and two users
3. Run edge-service
4. Use protected REST method to obtain details about the user
5. Use protected GraphQL method to obtain details about the user

Demo:
1. mvn spring-boot:run
2. try http://localhost:8080/api/cars to see JSON list of cars



## Used articles
- https://github.com/Microsoft/azure-spring-boot/blob/master/azure-spring-boot-starters/azure-active-directory-spring-boot-starter/README.md
- https://azure.microsoft.com/en-us/blog/use-azure-active-directory-with-spring-security-5-0-for-oauth-2-0/