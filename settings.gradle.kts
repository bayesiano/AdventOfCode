pluginManagement {
    repositories {
        maven {
            url = uri("http://ibrhsvn.ibmutua.inet:8081/artifactory/repo")
            isAllowInsecureProtocol = true
        }
        mavenCentral()
    }
}

rootProject.name = "AdventOfCode"
include("AoC-2021")

