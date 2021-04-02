package sk.styk.martin.apkanalyzer.dependencyinjection.activity.main

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import sk.styk.martin.apkanalyzer.dependencyinjection.activity.ActivityCommonModule
import sk.styk.martin.apkanalyzer.dependencyinjection.util.FragmentScope
import sk.styk.martin.apkanalyzer.dependencyinjection.viewmodel.ViewModelFactoriesModule
import sk.styk.martin.apkanalyzer.ui.about.AboutFragment
import sk.styk.martin.apkanalyzer.ui.applist.main.MainAppListFragment
import sk.styk.martin.apkanalyzer.ui.main.MainActivity
import sk.styk.martin.apkanalyzer.ui.permission.list.PermissionListFragment

@Module(includes = [
    ActivityCommonModule::class,
    ViewModelFactoriesModule::class,
])
abstract class MainActivityModule {

    @Binds
    abstract fun activity(activity: MainActivity): Activity

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun appListFragmentInjector(): MainAppListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun aboutFragmentInjector(): AboutFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun permissionListFragmentInjector(): PermissionListFragment

}