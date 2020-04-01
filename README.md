# DisneyEmojiBlitzCategoriesHelper

## UPDATE
Effective April 1, 2020, this application will no longer be updated. A website has been replaced for this. To see the website you can click [here](debsidekick.com)

## What is Disney Emoji Blitz?
Disney Emoji Blitz (DEB) is a Match-3 game which features different Emojis from many Disney franchises including Star Wars. You can download it for *free* on the [App Store](https://apps.apple.com/app/id1017551780) or [Google Play](https://play.google.com/store/apps/details?id=com.disney.emojimatch_goo&referrer=utm_source%3Dko_8c695e46b223008ad%26utm_medium%3D1%26utm_campaign%3Dkoemoji-blitz-google55a93de56c122358f70aa4b8c1%26utm_term%3D%26utm_content%3D%26)

## Purpose of the App
In the game, there are missions that have you earn prizes, level up and earn more points. Some of the missions require a certain emoji like a *red* emoji or a *hat-wearing* emoji. The game itself cannot tell you which emoji works for the requirement until after you have completed a match. This app basically solves this problem with the ability to choose up to 3 different categories (or up to 2 categories if using a box) so that you can kill two birds with one stone with completing multiple missions at once (there are at most 3 missions at a time).

## How to run this program
1. You must have **JDK 13.0.1** or higher in order to run this. Also make sure your **JAVA_HOME** enviroment variable is up to date.
2. This program uses Gradle as the build automator. If you don't have Gradle, you can download it for free [right here](https://gradle.org/)
3. Inside where you have placed the contents, use a terminal or command line prompt to go to that directory. You can then type in `gradle clean` and `gradle build`.
4. You can run the program from there by using `gradle run`. If you want a distribution copy, go to *build/distributions* and choose either the *.tar* or *.zip*
5. If using the distribution, make sure the *.csv* file is included inside the *bin* of the distribution folder, otherwise it will not produce results. Also, make sure you are using `cd <path-to-bin-folder>` in the terminal or command line prompt or you will get errors. You can then run the batch file or the unix executable located in the *bin* of the distribution by dragging the file into the terminal or command line prompt.

~~## What's next?
* There are some bugs that need to be handeled as this may not run on other operating systems (this does work on MacOS)~~
~~* As more emojis are added over time, getting the results might be longer. I am planning on switching over to a SQLite database when this happens.
* I might expand the program to only include emojis that you have. ~~

