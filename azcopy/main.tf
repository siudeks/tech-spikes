terraform {
  backend "local" {
    workspace_dir = "terraform.tfstate.d"
  }
}

provider "azurerm" {
  features {}
}

resource "random_string" "storage-name" {
  length  = 4
  upper   = false
  number  = false
  lower   = true
  special = false
}

resource "azurerm_resource_group" "default" {
  name     = "siudeksdemo"
  location = "westeurope"
}

resource "azurerm_storage_account" "default" {
  name                     = "${azurerm_resource_group.default.name}storage${random_string.storage-name.result}"
  resource_group_name      = azurerm_resource_group.default.name
  location                 = "westeurope"
  account_tier             = "Standard"
  account_replication_type = "GRS"

  tags = {
    environment = "demo"
  }
}


data "azuread_client_config" "current" {}

resource "azuread_application" "example" {
  display_name = "demo-application"
  owners       = [data.azuread_client_config.current.object_id]
}

# Represents our service principal designed to write / read / list data from azure storage
resource "azuread_service_principal" "example" {
  application_id               = azuread_application.example.application_id
  app_role_assignment_required = false
  owners                       = [data.azuread_client_config.current.object_id]
}

resource "azuread_service_principal_password" "secret" {
  service_principal_id = azuread_service_principal.example.id
}


resource "azurerm_storage_container" "example" {
  name                  = "content"
  storage_account_name  = azurerm_storage_account.default.name
  container_access_type = "private"
}

resource "azurerm_role_assignment" "data_contributor_role" {
  scope                = azurerm_storage_container.example.resource_manager_id
  role_definition_name = "Storage Blob Data Contributor"
  principal_id         = azuread_service_principal.example.id
}


output "tenantId" {
  value = data.azuread_client_config.current.tenant_id
}

output "applicationId" {
  value = azuread_application.example.application_id
}

output "storageName" {
  value = azurerm_storage_account.default.name
}

output "containerName" {
  value = azurerm_storage_container.example.name
}

output "servicePrincipalPassword" {
  value = nonsensitive(azuread_service_principal_password.secret.value)
}

