plugins {
    `kotlin-dsl`
}

group = "com.elkhami.repoviewer.buildlogic"

dependencies{
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "repoviewer.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose"){
            id = "repoviewer.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary"){
            id = "repoviewer.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "repoviewer.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUi"){
            id = "repoviewer.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("jvmLibrary"){
            id = "repoviewer.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmKtor"){
            id = "repoviewer.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
        register("jvmJunit5") {
            id = "repoviewer.jvm.junit5"
            implementationClass = "JvmJUnit5ConventionPlugin"
        }
        register("androidJunit5") {
            id = "repoviewer.android.junit5"
            implementationClass = "AndroidJUnit5ConventionPlugin"
        }
        register("androidRoom") {
            id = "repoviewer.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}