plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "gamja-market"
include("libs:common-domain")
include("libs:common-dto")
include("libs:common-utils")
include("apps:market-api")
include("apps:market-batch")
include("apps:market-admin")