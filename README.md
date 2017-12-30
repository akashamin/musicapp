# musicapp

Created this app as a HackerEarth challenge for creating a Music App for OLA 
https://www.hackerearth.com/challenge/hiring/ola-android-developer-hiring-challenge/

The music player is based on the Exo Player from google.
https://developer.android.com/guide/topics/media/exoplayer.html

The basic functionality of this app is :
  1. Fetch music list from an API (Using retrofit library).
  2. Play music from the link provided in the API (Exo player library).
  3. User can download the music (Using Download Manager).
  4. User can search for music.
  
The SDKs used in this app are :
  1. RETROFIT - For fetching data from an API
  ```android
  compile 'com.squareup.retrofit2:retrofit:2.3.0'
  compile 'com.squareup.retrofit2:converter-gson:2.3.0'
  compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
  compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
  ```
  2. EXOPLAYER - For the Music player and the controls
  ```android
  compile 'com.google.android.exoplayer:exoplayer:2.6.0'
  ```
  3. BUTTERKNIFE - For field binding
  ```android
  compile 'com.jakewharton:butterknife:8.8.1'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
  ```
  4. UNIVERSAL IMAGE LOADER - For loading images from a cdn or a server
  ```android
  compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.1'
  ```

Screen Shots

![Alt text](/../master/screenshots/screen1.jpg?raw=true "Screen 1")
![Alt text](/../master/screenshots/screen2.jpg?raw=true "Screen 2")
