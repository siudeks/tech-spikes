How to send an email in secure way

run:
mvn spring-boot:run -Dspring-boot.run.arguments='--mailFrom=EMAIL_FROM --mailTo=EMAIL_TO --password="PASSWORD"'

where
EMAIL_FROM - sender
EMAIL_TO - receiver
PASSWORD - password of the sender
