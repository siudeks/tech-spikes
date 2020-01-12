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

resource "random_id" "example-acr" {
  byte_length = 8
}
resource "azurerm_container_registry" "example" {
  name                     = "acr${random_id.example-acr.hex}"
  resource_group_name      = azurerm_resource_group.example.name
  location                 = azurerm_resource_group.example.location
  sku                      = "Basic"
  admin_enabled            = false
}

resource "azurerm_app_service_plan" "example" {
  name                = "example-appserviceplan"
  location            = azurerm_resource_group.example.location
  resource_group_name = azurerm_resource_group.example.name

  kind                = "linux"
  reserved            = true

  sku {
    tier = "Free"
    size = "F1"
  }
}

resource "random_id" "example-appservice" {
  byte_length = 4
}
resource "azurerm_app_service" "example" {
  name                = "my-fundction-${random_id.example-appservice.hex}"
  location            = azurerm_resource_group.example.location
  resource_group_name = azurerm_resource_group.example.name
  app_service_plan_id = azurerm_app_service_plan.example.id
  
  # site_config {
  #    always_on        = true
  #    linux_fx_version = "DOCKER|${azurerm_container_registry.example.login_server}/testdocker-alpine:v1"
  # }

  # app_settings = {
  #   WEBSITES_ENABLE_APP_SERVICE_STORAGE = "false"
  #   DOCKER_REGISTRY_SERVER_URL          = "https://${azurerm_container_registry.example.login_server}"
  #   DOCKER_REGISTRY_SERVER_USERNAME     = azurerm_container_registry.example.admin_username
  #   DOCKER_REGISTRY_SERVER_PASSWORD     = azurerm_container_registry.example.admin_password
  # }
}

resource "random_id" "function-app" {
  byte_length = 4
}
resource "azurerm_function_app" "example" {
  name                      = "test-azure-functions-${random_id.function-app.hex}"
  location                  = azurerm_resource_group.example.location
  resource_group_name       = azurerm_resource_group.example.name
  app_service_plan_id       = azurerm_app_service_plan.example.id
  storage_connection_string = azurerm_storage_account.example.primary_connection_string
}
