package com.charlie.newsapp;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;



@ApplicationScope
@Component(modules = {AndroidInjectionModule.class,
        ApplicationModule.class,
        ActivityProvider.class})
public interface ApplicationComponent {

    void inject(NewsAppApplication newsappApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(android.app.Application application);

        ApplicationComponent build();
    }


}
