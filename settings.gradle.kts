include(":flow_shared_prefs")
rootProject.name = "COVID Pulse"
rootProject.buildFileName = "build.gradle.kts"
include(":app")
include(":libraries:common")
include(":libraries:firebase_analytics")
include(":libraries:firebase_auth")
include(":libraries:google_maps_places")
include(":libraries:ledger")
include(":libraries:live_shared_prefs")
include(":data_sources:network:covid_stats")
include(":data_sources:databases:main_database")
include(":data_sources:repositories:covid_stats_repo")
include(":features:summary")