# Move files to File Share


Required:
- terraform
- az tools
- [azcopy](https://docs.microsoft.com/en-us/azure/storage/common/storage-use-azcopy-v10#download-azcopy)
    ```bash
    wget https://aka.ms/downloadazcopy-v10-linux -O azcopy.tar.gz
    tar -xvzf community_images.tar.gz
    mv azcopy_linux_a*/azcopy .
    ./azcopy
    ```

Note: we use *eval echo* in bash to remove from variables first and last quote because output from terraform contains quotes, but parameters of azcopy do not accept quotes
E.g. value "6b4f6921-321f-4a4f-aadd-e8fc504f2500" produced by Terraform output has to be converted to 6b4f6921-321f-4a4f-aadd-e8fc504f2500  
more: https://stackoverflow.com/questions/9733338/shell-script-remove-first-and-last-quote-from-a-variable#

- **terraform init** to install providers
- **PATH=$PATH:.** to simplify invocation of local azcopy *(azcopy instead of ./azcopy)*
- **az account list --query '[].{SubscriptionId:id, Name:name, IsDefault:isDefault}' --output table** to see subscriptions
- **az account set --subscription 'Visual Studio Professional'** to set default subscription. *Replace 'Visual Studio Professional' with your choice*
- **terraform apply** to create resources, and confirm **yes** when asking about configrmation
- Optional **azcopy env** to understand meaning of env variables
- ***
  ```bash
  export AZCOPY_SPA_CLIENT_SECRET=$(eval echo $(terraform output servicePrincipalPassword))
  export AZCOPY_SPA_APPLICATION_ID=$(eval echo $(terraform output applicationId))
  export AZCOPY_TENANT_ID=$(eval echo $(terraform output tenantId))
  export AZCOPY_AUTO_LOGIN_TYPE=SPN
  _STORAGE=$(eval echo $(terraform output storageName))
  _CONTAINER=$(eval echo $(terraform output containerName))
  ```
  to prepare secret variable for *azcopy* and temp variables to use them later on
- **fallocate -l 10M big_file.bin** to create local big file (100 MB)
- **azcopy copy big_file.bin https://$_STORAGE.blob.core.windows.net/$_CONTAINER** copy file to remote storage. Logged thanks to https://github.com/Azure/azure-storage-azcopy/wiki/Improved-login-support-for-AzCopy-commands-(with-in-memory-secret-store)
- **azcopy list https://$_STORAGE.blob.core.windows.net/$_CONTAINER** check if the file is created in remote azure filesystem
- **terraform destroy** finally after test to cleanup, and confirm **yes** when asking about configrmation

## Useful articles
- https://docs.microsoft.com/en-us/azure/storage/common/storage-ref-azcopy-copy