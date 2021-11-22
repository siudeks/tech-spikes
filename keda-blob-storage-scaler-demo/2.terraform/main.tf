terraform {
  backend "local" {
    workspace_dir = "terraform.tfstate"
  }
}

provider "azurerm" {
  features {}
}

resource "random_string" "rg-suffix" {
  length  = 4
  upper   = false
  number  = false
  lower   = true
  special = false
}

resource "azurerm_resource_group" "rg-default" {
  name     = "demo-${random_string.rg-suffix.result}"
  location = "westeurope"
}

resource "random_string" "storage-name" {
  length  = 8
  upper   = false
  number  = false
  lower   = true
  special = false
}

resource "azurerm_storage_account" "default" {
  name                     = "demostorage${random_string.storage-name.result}"
  resource_group_name      = azurerm_resource_group.rg-default.name
  location                 = "westeurope"
  account_tier             = "Standard"
  account_replication_type = "GRS"
}

resource "azurerm_storage_container" "container1" {
  name                  = "container1"
  storage_account_name  = azurerm_storage_account.default.name
  container_access_type = "private"
}

