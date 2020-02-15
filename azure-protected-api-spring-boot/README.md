# Create RestApi protected by Azure Authentication

1. Create manually a simple Azure AD with service principal used for automation. Use principal in application configuration
2. Use terraform scripts to create two user groups and two users
3. Run edge-service
4. Use protected REST method to obtain details about the user
5. Use protected GraphQL method to obtain details about the user