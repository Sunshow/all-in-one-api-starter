rootProject.name = "all-in-one-api"
include(":api-entity")
include(":api-domain")
include(":api-provider")
include(":api-gateway")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include("api-scheduler")
