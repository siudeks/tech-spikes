# Simple terraform script to create resources required by the spike
# Values, required to be used in values in application properties, are displayed as script output values.

resource "random_id" "resource_group_suffix" { byte_length = 8 }
resource "azurerm_resource_group" "example" {
  name     = "example-${random_id.resource_group_suffix.dec}"
  location = "West Europe"
}

resource "random_id" "servicebus_namespace_suffix" { byte_length = 8 }
resource "azurerm_servicebus_namespace" "example" {
  name                = "my-servicebus-namespace-${random_id.servicebus_namespace_suffix.dec}"
  location            = azurerm_resource_group.example.location
  resource_group_name = azurerm_resource_group.example.name
  sku                 = "Standard"
}

resource "azurerm_servicebus_topic" "example" {
  name                = "servicebus_topic"
  resource_group_name = azurerm_resource_group.example.name
  namespace_name      = azurerm_servicebus_namespace.example.name

  # ??? enable_partitioning = true
}

resource "azurerm_servicebus_subscription" "example" {
  name                = "my_servicebus_subscription"
  resource_group_name = azurerm_resource_group.example.name
  namespace_name      = azurerm_servicebus_namespace.example.name
  topic_name          = azurerm_servicebus_topic.example.name
  max_delivery_count  = 1
}

output resource-group {
  value = azurerm_resource_group.example.name
}
output location {
  value = azurerm_resource_group.example.location
}
output servicebus-namespace {
  value = azurerm_servicebus_namespace.example.name
}

