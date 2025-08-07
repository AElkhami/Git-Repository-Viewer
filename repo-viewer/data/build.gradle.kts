plugins {
    alias(libs.plugins.repoviewer.android.library)
    alias(libs.plugins.repoviewer.jvm.ktor)
    alias(libs.plugins.repoviewer.jvm.junit5)
}

android {
    namespace = "com.elkhami.repoviewer.data"
}

dependencies {
    // Koin
    implementation(libs.bundles.koin)
    //Paging
    implementation(libs.bundles.paging)
    //Room
    implementation(libs.bundles.room)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)
}