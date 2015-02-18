#ShinobiCharts in ListViews (Java)#
This simple app demonstrates how to efficiently include our charts in a standard ListView and to tie the panning gestures from one chart to all the charts. There's a series of [blog posts](http://www.shinobicontrols.com/blog/posts/2015/01/21/charts-in-listviews-part-1-displaying-charts) accompanying this project.

<p align="center">
  <img src="screenshot.png?raw=true" alt="Screenshot" />
</p>

##Building the project##
In order to build this project you'll need a copy of ShinobiCharts for Android. If you don't have it yet, you can download a free trial from the [ShinobiCharts for Android website](http://www.shinobicontrols.com/android/shinobicharts).

###Eclipse###
If you haven't already set up ShinobiCharts as an Android library project in your Eclipse workspace, there are instructions in our [Quick Start Guide](http://www.shinobicontrols.com/android/shinobicharts/quickstartguide/import-the-library/). Once the library is in place, and you've cloned or downloaded this repo, click File > New > Other… > "Android Project from Existing Code", then point it at the app directory of your download.

With this demo app now in your Project Explorer you can link it up to ShinobiCharts for Android which will enable it to be built. To do this, open up the project's Properties dialog and in the *Android* section add the ShinobiCharts for Android library project that's in your workspace.

If you're using the trial version you'll need to add your trial license key: open up ChartArrayAdapter.java and replace the placeholder text in the `shinobiChart.setLicenseKey("<license_key_here>");` call with your trial license key.

You should now be able to build the project and run it.

###Android Studio###
Once you've cloned or downloaded this repo, click File > Import Project, then point is at the root directory of your download. When it asks whether you want to use the Gradle wrapper select "Cancel" and then choose the path of your local Gradle distribution.

With the demo app now in your Project Explorer you can link it up to ShinobiCharts for Android which will enable it to be built. To do this follow the instructions in [this blog post](http://www.shinobicontrols.com/blog/posts/2014/12/11/using-shinobicharts-with-android-studio-10) which describes how to import the library and link it to your project.

If you're using the trial version you'll need to add your trial license key: open up ChartArrayAdapter.java and replace the placeholder text in the `shinobiChart.setLicenseKey("<license_key_here>");` call with your trial license key.

You should now be able to build the project and run it.

##Contributing##

We'd love to see your contributions to this project - please go ahead and fork it and send us a pull request when you're done! Or if you have a new project you think we should include here, email info@shinobicontrols.com to tell us about it.

##License##

The [Apache License, Version 2.0](license.txt) applies to everything in this repository, and will apply to any user contributions.
