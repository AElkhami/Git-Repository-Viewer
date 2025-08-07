plugins {
    alias(libs.plugins.repoviewer.android.library)
    alias(libs.plugins.repoviewer.android.room)
}

android {
    namespace = "com.elkhami.core.database"

}
dependencies {
    //Koin
    implementation(libs.bundles.koin)

    //Paging
    implementation(libs.bundles.paging)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(projects.core.domain)
}