terraform {
  backend "local" {
    workspace_dir = "terraform.tfstate.d"
  }
}

provider "azurerm" {
  features { }
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
  resource_group_name      = "${azurerm_resource_group.default.name}"
  location                 = "westeurope"
  account_tier             = "Standard"
  account_replication_type = "GRS"

  tags = {
    environment = "demo"
  }
}

output "storageName" {
    value = azurerm_storage_account.default.name
}
