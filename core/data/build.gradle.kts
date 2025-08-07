plugins {
    alias(libs.plugins.repoviewer.android.library)
    alias(libs.plugins.repoviewer.jvm.ktor)
}

android {
    namespace = "com.elkhami.core.data"
}

dependencies {
    //Koin
    implementation(libs.bundles.koin)
    //Timber
    implementation(libs.timber)

    implementation(projects.core.domain)
}