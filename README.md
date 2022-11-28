## CMPUT301F22T04
Welcome to the nosh, a meal planning mobile application!

## Table of Contents
- [Introduction](#introduction)
- [Wiki Page](#wiki-page)
- [Issue](#issue)
- [License](#license)


## Team Members
| Name  | CCID | 
| ------------- | ------------- |
| Julian Gallego Franco  | gallegof | 
| Al Hisham Anik | alhisham | 
| Mohammad Fahad Naveed  | mnaveed1  | 
| Chengxuan Li | chengxua | 
| Manav Powar | mpowar | 
| Lok Him Isaac Cheng | lokhimis | 

## Introduction
To assist in meal planning, we created a mobile application that allows user to track their food storage, record their recipes, plan their meals for several days, and prepare shopping lists.


## Firebase Emulator 
---
The instalation of this emulator is required to run the app as of project part 3.
- To use emulator, you need Firebase CLI in your local machine.
  - If you're on Windows, use this https://firebase.tools/bin/win/instant/latest to download.
    - Remember to add it to your environmental path or place it in your project folder
    - Recommend to rename the file to `firebase` since the auto run script use this name
  - If you're on Mac or Linux, just run the `install-firebase-mac.sh` in terminal and it will automatically setup for you.
- To run emulator, just run `start-emulator.sh` (`start-emulator.ps1` for Windows) in terminal
  - It might prompt you transaction to login with Firebase account if you're first time using it.
    - Make sure to select the `nosh-45f06` project when it asks for which project in your account want to be used
---
### Database related Classes
---
- Don't directly call constructor for `UserAuth` and constructors for `DBController` and its future sub-classes.
  - Use `DBControllerFactory.getDBController(classname.class.getSimpleName())` to create the class you want
  - Use `AppInitializer.getInstance(this).initializeComponent(UserAuthInitializer.class);` to create an instance of `UserAuth` for users authentication.
    - It suppose to be call automatically and implicitly every time the app launch but I can't get it work right now.
---
### About `Initializer`
---
- A library for initializing necessary components (ex. Firebase, Facebook API, etc.) at application startup.
  - Read more in here https://developer.android.com/topic/libraries/app-startup
- What `Initializer` does is create a single object of a class for the whole life cycle of the apps.
  - Even if you manually create another instance, the variable will have the reference that point to the same object in memory. 

## Wiki Page
Link to our Wiki page: https://github.com/CMPUT301F22T04/nosh/wiki

## Issue
This repository is maintained actively, so if you face any issue please <a href="https://github.com/ankitwasankar/mftool-java/issues/new">raise an issue</a>.

<h4>Liked the work ?</h4>
Give the repository a star :-)

## License




