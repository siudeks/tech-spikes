Required:
- terraform
- az tools

- **terraform init** to install providers
- **az account list --query '[].{SubscriptionId:id, Name:name}' --output table** to see subscriptions
- **az account set --subscription 'Visual Studio Professional'** to set default subscription. Replace 'Visual Studio Professional' with proper name of subscription
- **terraform apply** to create resources, and confirm **yes** when asking about configrmation
- **fallocate -l 10M big_file.bin** to create local big file
- **terraform destroy** finally after test to cleanup, and confirm **yes** when asking about configrmation