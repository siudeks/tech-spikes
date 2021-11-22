provider "azurerm" {
  features {

  }
}

locals {
  subscriptionName = "Visual Studio Professional"
}

data "azurerm_resource_group" {
    name = 
}
