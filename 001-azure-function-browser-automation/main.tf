# Configure the Azure Provider
provider "azurerm" {
  # whilst the `version` attribute is optional, we recommend pinning to a given version of the Provider
  version = "=1.38.0"
}


resource "random_id" "example-rg" {
  byte_length = 4
}
resource "azurerm_resource_group" "example" {
  name     = "spike-azure-function-${random_id.example-rg.hex}"
  location = "westeurope"
}


resource "random_id" "example-sa" {
  byte_length = 4
}
resource "azurerm_storage_account" "example" {
  name                     = "storageacc${random_id.example-sa.hex}"
  resource_group_name      = azurerm_resource_group.example.name
  location                 = azurerm_resource_group.example.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
}

resource "azurerm_app_service_plan" "example" {
  name                = "azure-functions-test-service-plan"
  location            = azurerm_resource_group.example.location
  resource_group_name = azurerm_resource_group.example.name
  
  kind                = "linux"

  site_config {
     always_on        = true
     linux_fx_version = "DOCKER|${data.azurerm_container_registry.containertest.login_server}/testdocker-alpine:v1"
  }

  app_settings {
    "WEBSITES_ENABLE_APP_SERVICE_STORAGE" = "false"
    "DOCKER_REGISTRY_SERVER_URL"          = "https://${data.azurerm_container_registry.containertest.login_server}"
    "DOCKER_REGISTRY_SERVER_USERNAME"     = "${data.azurerm_container_registry.containertest.admin_username}"
    "DOCKER_REGISTRY_SERVER_PASSWORD"     = "${data.azurerm_container_registry.containertest.admin_password}"
  }}

resource "azurerm_function_app" "example" {
  name                      = "test-azure-functions"
  location                  = azurerm_resource_group.example.location
  resource_group_name       = azurerm_resource_group.example.name
  app_service_plan_id       = azurerm_app_service_plan.example.id
  storage_connection_string = azurerm_storage_account.example.primary_connection_string
}
